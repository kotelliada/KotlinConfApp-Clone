package io.kotelliada.kotlinconf.privacynotice

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.privacynotice.main.PreviewPrivacyNoticeMainComponent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PreviewPrivacyNoticeComponent(previewUiState: UiState) : PrivacyNoticeComponent {

    override val childStack: Value<ChildStack<*, PrivacyNoticeComponent.Child>> =
        MutableValue(
            ChildStack(
                configuration = Unit,
                instance = PrivacyNoticeComponent.Child.Main(PreviewPrivacyNoticeMainComponent())
            )
        )

    override val uiState: StateFlow<UiState> = MutableStateFlow(previewUiState)

    override fun onRejectNoticeClicked() {
    }

    override fun onAcceptNoticeClicked() {
    }

    override fun onReloadDocumentClicked() {
    }

    override fun closeLoadingDialogClicked() {
    }
}
