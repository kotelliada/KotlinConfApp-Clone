package io.kotelliada.kotlinconf.privacynotice

import io.kotelliada.mvi.Middleware
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

@OptIn(ExperimentalCoroutinesApi::class)
class RejectPrivacyNoticeMiddleware :
    Middleware<PrivacyNoticeComponent.Action, UiState, PrivacyNoticeComponent.Effect> {

    override fun bind(
        actions: Flow<PrivacyNoticeComponent.Action>,
        state: Flow<UiState>,
        emitEffect: (PrivacyNoticeComponent.Effect) -> Unit
    ): Flow<PrivacyNoticeComponent.Action> {
        return actions
            .filterIsInstance<PrivacyNoticeComponent.UiAction.RejectPrivacyNotice>()
            .flatMapLatest {
                flow {
                    emit(PrivacyNoticeComponent.InternalAction.RejectingPrivacyStarted())

                    emitEffect(PrivacyNoticeComponent.Effect.PrivacyRejected())
                }
            }
    }
}
