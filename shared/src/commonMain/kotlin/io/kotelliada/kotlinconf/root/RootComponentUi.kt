package io.kotelliada.kotlinconf.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import io.kotelliada.kotlinconf.home.HomeComponentUi
import io.kotelliada.kotlinconf.onboarding.OnboardingComponentUi

@Composable
fun RootComponentUi(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    Children(
        stack = component.childStack,
        modifier = modifier
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.Home -> HomeComponentUi(component = child.component)
            is RootComponent.Child.Onboarding -> OnboardingComponentUi(component = child.component)
        }
    }
}
