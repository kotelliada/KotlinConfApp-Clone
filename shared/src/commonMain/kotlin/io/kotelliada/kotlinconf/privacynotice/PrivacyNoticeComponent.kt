package io.kotelliada.kotlinconf.privacynotice

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.privacynotice.details.PrivacyNoticeDetailsComponent
import io.kotelliada.kotlinconf.privacynotice.main.PrivacyNoticeMainComponent
import kotlinx.coroutines.flow.StateFlow

interface PrivacyNoticeComponent {
    val childStack: Value<ChildStack<*, Child>>

    val uiState: StateFlow<UiState>

    fun onRejectNoticeClicked()

    fun onAcceptNoticeClicked()

    fun onRetryClicked()

    sealed class Child {
        class Main(val component: PrivacyNoticeMainComponent) : Child()
        class Details(val component: PrivacyNoticeDetailsComponent) : Child()
    }
}
