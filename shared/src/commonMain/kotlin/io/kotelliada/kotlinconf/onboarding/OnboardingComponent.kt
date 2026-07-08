package io.kotelliada.kotlinconf.onboarding

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.privacynotice.PrivacyNoticeComponent

interface OnboardingComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class PrivacyNotice(val component: PrivacyNoticeComponent) : Child()
        class Notifications() : Child()
    }
}
