package io.kotelliada.kotlinconf.privacynotice

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.arkivanov.essenty.lifecycle.Lifecycle
import io.kotelliada.kotlinconf.ConferenceService
import io.kotelliada.kotlinconf.privacynotice.details.DefaultPrivacyNoticeDetailsComponent
import io.kotelliada.kotlinconf.privacynotice.details.PrivacyNoticeDetailsComponent
import io.kotelliada.kotlinconf.privacynotice.main.DefaultPrivacyNoticeMainComponent
import io.kotelliada.kotlinconf.privacynotice.main.PrivacyNoticeMainComponent
import io.kotelliada.mvi.Reducer
import io.kotelliada.mvi.Store
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class DefaultPrivacyNoticeComponent(
    componentContext: ComponentContext,
    private val conferenceService: ConferenceService,
    private val mainDispatcher: MainCoroutineDispatcher,
    private val defaultDispatcher: CoroutineDispatcher,
    private val globalLogger: Logger,
    private val onAppTermsOfUse: () -> Unit,
    private val onPrivacyAccepted: () -> Unit,
    private val onPrivacyRejected: () -> Unit
) : ComponentContext by componentContext, PrivacyNoticeComponent {

    private val navigation = StackNavigation<Config>()

    private val logger = globalLogger.withTag("PrivacyNoticeComponent")

    private val retainedLogic = instanceKeeper.getOrCreate {
        RetainedLogic(mainDispatcher, logger, conferenceService)
    }

    override val childStack: Value<ChildStack<*, PrivacyNoticeComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Main,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override val uiState: StateFlow<UiState> = retainedLogic.store.state

    init {
        lifecycle.subscribe(object: Lifecycle.Callbacks {
            private var effectsJob: Job? = null

            override fun onStart() {
                super.onStart()
                effectsJob = retainedLogic.coroutineScope.launch {
                    retainedLogic.store.effects
                        .collect { effect ->
                            when (effect) {
                                is PrivacyNoticeComponent.Effect.PrivacyAccepted -> onPrivacyAccepted()
                                is PrivacyNoticeComponent.Effect.PrivacyRejected -> onPrivacyRejected()
                            }
                        }
                }
            }

            override fun onStop() {
                super.onStop()
                effectsJob?.cancel()
                effectsJob = null
            }
        })
    }

    override fun onRejectNoticeClicked() {
        val action = PrivacyNoticeComponent.UiAction.RejectPrivacyNotice()
        retainedLogic.store.acceptAction(action)
    }

    override fun onAcceptNoticeClicked() {
        val action = PrivacyNoticeComponent.UiAction.AcceptPrivacyNotice()
        retainedLogic.store.acceptAction(action)
    }

    override fun onReloadDocumentClicked() {
        val action = PrivacyNoticeComponent.UiAction.RetryToLoadDocument()
        retainedLogic.store.acceptAction(action)
    }

    override fun closeLoadingDialogClicked() {
        val action = PrivacyNoticeComponent.UiAction.CloseLoadingDialog()
        retainedLogic.store.acceptAction(action)
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): PrivacyNoticeComponent.Child {
        return when (config) {
            is Config.Main -> PrivacyNoticeComponent.Child.Main(createPrivacyNoticeMainComponent(componentContext))
            is Config.Details -> PrivacyNoticeComponent.Child.Details(createPrivacyNoticeDetailsComponent(componentContext))
        }
    }

    private fun createPrivacyNoticeMainComponent(
        componentContext: ComponentContext
    ): PrivacyNoticeMainComponent {
        return DefaultPrivacyNoticeMainComponent(
            componentContext = componentContext,
            onReadPrivacyNotice = { navigation.pushNew(Config.Details) }
        )
    }

    private fun createPrivacyNoticeDetailsComponent(
        componentContext: ComponentContext
    ): PrivacyNoticeDetailsComponent {
        return DefaultPrivacyNoticeDetailsComponent(
            componentContext = componentContext,
            conferenceService = conferenceService,
            mainDispatcher = mainDispatcher,
            defaultDispatcher = defaultDispatcher,
            globalLogger = globalLogger,
            onBackButton = { navigation.pop() }
        )
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Main : Config()

        @Serializable
        data object Details : Config()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private class RetainedLogic(
    mainDispatcher: MainCoroutineDispatcher,
    logger: Logger,
    conferenceService: ConferenceService,
) : InstanceKeeper.Instance {

    val coroutineScope = CoroutineScope(mainDispatcher.immediate + SupervisorJob())

    val store = Store(
        reducer = PrivacyNoticeReducer(),
        middlewares = listOf(
            LoadPrivacyNoticeMiddleware(conferenceService),
            AcceptPrivacyNoticeMiddleware(conferenceService),
            RejectPrivacyNoticeMiddleware()
        ),
        coroutineScope = coroutineScope,
        mainDispatcher = mainDispatcher,
        initialState = UiState()
    )

    init {
        store.acceptAction(PrivacyNoticeComponent.InternalAction.LoadDocument())
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}

class PrivacyNoticeReducer : Reducer<UiState, PrivacyNoticeComponent.Action> {

    override fun reduce(
        state: UiState,
        action: PrivacyNoticeComponent.Action
    ): UiState {
        return when (action) {
            is PrivacyNoticeComponent.InternalAction.LoadDocumentStarted -> state.copy(
                isDocumentLoading = true,
                isDocumentLoadingError = false,
                isDocumentReady = false,
                isAcceptingPrivacyAvailable = false,
                isRejectingPrivacyAvailable = false
            )

            is PrivacyNoticeComponent.InternalAction.LoadDocumentFailed -> state.copy(
                isDocumentLoading = false,
                isDocumentLoadingError = true,
                isDocumentReady = false,
                isAcceptingPrivacyAvailable = false,
                isRejectingPrivacyAvailable = false
            )

            is PrivacyNoticeComponent.InternalAction.LoadDocumentSuccess -> state.copy(
                isDocumentLoading = false,
                isDocumentLoadingError = false,
                isDocumentReady = true,
                isAcceptingPrivacyAvailable = true,
                isRejectingPrivacyAvailable = true
            )

            is PrivacyNoticeComponent.UiAction.CloseLoadingDialog -> state.copy(
                isAcceptingPrivacyError = false,
                isAcceptingPrivacyAvailable = true,
                isAcceptingPrivacy = false,
                isRejectingPrivacyError = false,
                isRejectingPrivacyAvailable = true,
                isRejectingPrivacy = false
            )

            is PrivacyNoticeComponent.InternalAction.AcceptingPrivacyStarted -> state.copy(
                isAcceptingPrivacyError = false,
                isAcceptingPrivacyAvailable = false,
                isAcceptingPrivacy = true,
            )

            is PrivacyNoticeComponent.InternalAction.RejectingPrivacyStarted -> state.copy(
                isRejectingPrivacy = true,
                isRejectingPrivacyAvailable = false,
                isRejectingPrivacyError = false
            )

            is PrivacyNoticeComponent.InternalAction.AcceptingPrivacyFailed -> state.copy(
                isAcceptingPrivacyError = true,
                isAcceptingPrivacyAvailable = true,
                isAcceptingPrivacy = false,
            )

            is PrivacyNoticeComponent.InternalAction.LoadDocument,
            is PrivacyNoticeComponent.UiAction.RetryToLoadDocument,
            is PrivacyNoticeComponent.UiAction.AcceptPrivacyNotice,
            is PrivacyNoticeComponent.UiAction.RejectPrivacyNotice, -> state
        }
    }
}
