package io.kotelliada.kotlinconf.privacynotice

import io.kotelliada.kotlinconf.ConferenceService
import io.kotelliada.kotlinconf.utils.Result
import io.kotelliada.mvi.Middleware
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

@OptIn(ExperimentalCoroutinesApi::class)
class LoadPrivacyNoticeMiddleware(
    private val conferenceService: ConferenceService,
) : Middleware<PrivacyNoticeComponent.Action, UiState, PrivacyNoticeComponent.Effect> {

    override fun bind(
        actions: Flow<PrivacyNoticeComponent.Action>,
        state: Flow<UiState>,
        emitEffect: (PrivacyNoticeComponent.Effect) -> Unit
    ): Flow<PrivacyNoticeComponent.Action> {
        return actions
            .filter { action ->
                action is PrivacyNoticeComponent.InternalAction.LoadDocument ||
                        action is PrivacyNoticeComponent.UiAction.RetryToLoadDocument
            }
            .flatMapLatest {
                flow {
                    emit(PrivacyNoticeComponent.InternalAction.LoadDocumentStarted())

                    val assetResult = conferenceService.getAsset("documents/app-privacy-notice.md")

                    if (assetResult is Result.Success) {
                        emit(PrivacyNoticeComponent.InternalAction.LoadDocumentSuccess(assetResult))
                    } else {
                        val result = assetResult as Result.Failure
                        emit(PrivacyNoticeComponent.InternalAction.LoadDocumentFailed(result))
                    }
                }
            }
    }
}
