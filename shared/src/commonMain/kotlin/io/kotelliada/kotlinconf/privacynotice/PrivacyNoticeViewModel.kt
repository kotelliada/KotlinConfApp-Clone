package io.kotelliada.kotlinconf.privacynotice

import co.touchlab.kermit.Logger
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import io.kotelliada.kotlinconf.ConferenceService
import io.kotelliada.kotlinconf.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PrivacyNoticeViewModel(
    conferenceService: ConferenceService,
    mainDispatcher: MainCoroutineDispatcher,
    private val logger: Logger,
    private val confirmationRequired: Boolean
) : InstanceKeeper.Instance {

    private val coroutineScope = CoroutineScope(mainDispatcher.immediate + SupervisorJob())

    // TODO: Add throttle to prevent multiple clicks
    private val retryClicksFlow = MutableSharedFlow<Unit>()

    val uiState: StateFlow<UiState> = retryClicksFlow.onStart {
        logger.d { "PrivacyNoticeViewModel retryClicksFlow initial value" }
        emit(Unit)
    }.flatMapLatest {
        logger.d { "PrivacyNoticeViewModel flatMapLatest" }

        flow {
            emit(UiState.loadingState())

            val assetResult = conferenceService.getAsset("documents/app-privacy-notice.md")
            val newUiState = assetResponseToUiState(assetResult)
            emit(newUiState)
            logger.d { "PrivacyNoticeViewModel emit content state" }
        }
    }.stateIn(
        scope = coroutineScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState.loadingState()
    )

    fun onRetryClicked() {
        logger.d { "PrivacyNoticeViewModel.onRetryClicked" }
        coroutineScope.launch {
            retryClicksFlow.emit(Unit)
        }
    }

    fun onRejectNoticeClicked() {

    }

    fun onAcceptNoticeClicked() {

    }

    override fun onDestroy() {
        logger.d { "PrivacyNoticeViewModel.onDestroy" }
        super.onDestroy()
        coroutineScope.cancel()
    }

    private fun assetResponseToUiState(result: Result<String>): UiState {
        return when (result) {
            is Result.Success -> UiState.contentState()
            is Result.Failure -> UiState.errorState()
        }
    }
}
