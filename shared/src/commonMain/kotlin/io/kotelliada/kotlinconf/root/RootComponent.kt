package io.kotelliada.kotlinconf.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.home.HomeComponent
import io.kotelliada.kotlinconf.onboarding.OnboardingComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Child>>

    sealed class Child {
        class Home(val component: HomeComponent) : Child()
        class Onboarding(val component: OnboardingComponent) : Child()
    }
}
