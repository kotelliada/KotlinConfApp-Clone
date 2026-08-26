package io.kotelliada.kotlinconf.privacynotice.details

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.mikepenz.markdown.model.State
import com.mikepenz.markdown.model.parseMarkdownFlow
import io.kotelliada.kotlinconf.ConferenceService
import io.kotelliada.kotlinconf.utils.Result
import io.kotelliada.kotlinconf.utils.UnknownError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn

class DefaultPrivacyNoticeDetailsComponent(
    componentContext: ComponentContext,
    mainDispatcher: MainCoroutineDispatcher,
    defaultDispatcher: CoroutineDispatcher,
    globalLogger: Logger,
    private val conferenceService: ConferenceService,
    private val onBackButton: () -> Unit
) : ComponentContext by componentContext, PrivacyNoticeDetailsComponent {

    private val logger = globalLogger.withTag("PrivacyNoticeDetailsComponent")

    private val retainedLogic = instanceKeeper.getOrCreate {
        RetainedLogic(conferenceService, mainDispatcher, defaultDispatcher)
    }

    override val uiState: StateFlow<State> = retainedLogic.uiState

    override fun onBackButtonClicked() {
        onBackButton()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private class RetainedLogic(
    conferenceService: ConferenceService,
    mainDispatcher: MainCoroutineDispatcher,
    defaultDispatcher: CoroutineDispatcher
) : InstanceKeeper.Instance {

    val coroutineScope = CoroutineScope(mainDispatcher.immediate + SupervisorJob())

    val uiState: StateFlow<State> = flow {
        val asset = conferenceService.getAsset("documents/app-privacy-notice.md")
        emit(asset)
    }
        .flatMapLatest { result ->
            if (result is Result.Success) {
                parseMarkdownFlow(result.value)
            } else {
                flow { emit(State.Error(UnknownError())) }
            }
        }
        .flowOn(defaultDispatcher)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = State.Loading()
        )

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }
}
