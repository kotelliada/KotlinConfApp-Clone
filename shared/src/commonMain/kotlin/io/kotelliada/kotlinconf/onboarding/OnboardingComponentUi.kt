package io.kotelliada.kotlinconf.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.arkivanov.decompose.extensions.compose.stack.Children
import io.kotelliada.kotlinconf.privacynotice.PrivacyNoticeComponentUi
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper

@Composable
fun OnboardingComponentUi(
    component: OnboardingComponent,
    modifier: Modifier = Modifier
) {
    Children(
        stack = component.childStack,
        modifier = modifier
    ) {
        when (val child = it.instance) {
            is OnboardingComponent.Child.PrivacyNotice -> PrivacyNoticeComponentUi(component = child.component)
            is OnboardingComponent.Child.Notifications -> throw NotImplementedError()
        }
    }
}

@PreviewLightDark
@Composable
private fun OnboardingComponentUiPreview() {
    PreviewHelper(paddingEnabled = false) {
        OnboardingComponentUi(component = PreviewOnboardingComponent())
    }
}
