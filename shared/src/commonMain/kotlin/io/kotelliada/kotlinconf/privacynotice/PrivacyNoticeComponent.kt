package io.kotelliada.kotlinconf.privacynotice

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.privacynotice.details.PrivacyNoticeDetailsComponent
import io.kotelliada.kotlinconf.privacynotice.main.PrivacyNoticeMainComponent

interface PrivacyNoticeComponent {
    val childStack: Value<ChildStack<*, Child>>

    fun onRejectNoticeClicked()

    fun onAcceptNoticeClicked()

    fun onAppTermsOfUseClicked()

    sealed class Child {
        class Main(val component: PrivacyNoticeMainComponent) : Child()
        class Details(val component: PrivacyNoticeDetailsComponent) : Child()
    }
}
