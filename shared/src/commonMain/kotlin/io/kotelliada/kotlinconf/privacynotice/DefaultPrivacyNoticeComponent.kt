package io.kotelliada.kotlinconf.privacynotice

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.getOrCreate
import io.kotelliada.kotlinconf.ConferenceService
import io.kotelliada.kotlinconf.privacynotice.details.DefaultPrivacyNoticeDetailsComponent
import io.kotelliada.kotlinconf.privacynotice.details.PrivacyNoticeDetailsComponent
import io.kotelliada.kotlinconf.privacynotice.main.DefaultPrivacyNoticeMainComponent
import io.kotelliada.kotlinconf.privacynotice.main.PrivacyNoticeMainComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

class DefaultPrivacyNoticeComponent(
    componentContext: ComponentContext,
    private val conferenceService: ConferenceService,
    private val mainDispatcher: MainCoroutineDispatcher,
    private val defaultDispatcher: CoroutineDispatcher,
    private val globalLogger: Logger,
    private val onRejectNotice: () -> Unit,
    private val onAcceptNotice: () -> Unit,
    private val onAppTermsOfUse: () -> Unit,
    private val confirmationRequired: Boolean,
) : ComponentContext by componentContext, PrivacyNoticeComponent {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, PrivacyNoticeComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Main,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private val logger = globalLogger.withTag("PrivacyNoticeComponent")

    private val viewModel = instanceKeeper.getOrCreate {
        PrivacyNoticeViewModel(
            conferenceService = conferenceService,
            mainDispatcher = mainDispatcher,
            logger = logger
        )
    }

    override val uiState: StateFlow<UiState>
        get() {
            return viewModel.uiState
        }

    override fun onRejectNoticeClicked() {
    }

    override fun onAcceptNoticeClicked() {
    }

    override fun onAppTermsOfUseClicked() {
    }

    override fun onRetryClicked() {
        logger.d { "onRetryClicked" }
        viewModel.onRetryClicked()
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
