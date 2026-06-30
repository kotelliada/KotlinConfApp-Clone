package io.kotelliada.kotlinconf.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.main.MainComponent
import io.kotelliada.kotlinconf.privacynotice.PrivacyNoticeComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Main(val component: MainComponent) : Child()
        class PrivacyNotice(val component: PrivacyNoticeComponent) : Child()
    }
}
