package io.kotelliada.kotlinconf.privacynotice

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.privacynotice.details.PrivacyNoticeDetailsComponent
import io.kotelliada.kotlinconf.privacynotice.main.PrivacyNoticeMainComponent
import io.kotelliada.kotlinconf.utils.Result
import kotlinx.coroutines.flow.StateFlow

interface PrivacyNoticeComponent {
    val childStack: Value<ChildStack<*, Child>>

    val uiState: StateFlow<UiState>

    fun onRejectNoticeClicked()

    fun onAcceptNoticeClicked()

    fun onReloadDocumentClicked()

    fun closeLoadingDialogClicked()

    sealed class Child {
        class Main(val component: PrivacyNoticeMainComponent) : Child()
        class Details(val component: PrivacyNoticeDetailsComponent) : Child()
    }

    sealed interface Action

    sealed interface UiAction : Action {
        class RetryToLoadDocument : UiAction
        class AcceptPrivacyNotice : UiAction
        class RejectPrivacyNotice : UiAction
        class CloseLoadingDialog : UiAction
    }

    sealed interface InternalAction : Action {
        class LoadDocument : InternalAction
        class LoadDocumentStarted : InternalAction
        data class LoadDocumentSuccess(
            val privacyNotice: Result.Success<String>
        ) : InternalAction

        data class LoadDocumentFailed(
            val privacyNotice: Result.Failure<String>
        ) : InternalAction

        class AcceptingPrivacyStarted : InternalAction
        class AcceptingPrivacyFailed : InternalAction

        class RejectingPrivacyStarted : InternalAction
    }

    sealed interface Effect {
        class PrivacyAccepted : Effect
        class PrivacyRejected : Effect
    }
}
