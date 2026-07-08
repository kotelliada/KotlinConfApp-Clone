package io.kotelliada.kotlinconf.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.home.PreviewHomeComponent
import io.kotelliada.kotlinconf.onboarding.PreviewOnboardingComponent
import io.kotelliada.kotlinconf.root.RootComponent.Child

class PreviewRootComponent : RootComponent {

    override val childStack: Value<ChildStack<*, Child>> =
        MutableValue(
            ChildStack(
                configuration = Unit,
                instance = Child.Home(PreviewHomeComponent())
            )
        )
}
