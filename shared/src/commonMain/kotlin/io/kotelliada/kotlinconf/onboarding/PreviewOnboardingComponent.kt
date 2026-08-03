package io.kotelliada.kotlinconf.onboarding

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.privacynotice.PreviewPrivacyNoticeComponent
import io.kotelliada.kotlinconf.privacynotice.UiState

class PreviewOnboardingComponent : OnboardingComponent {

    override val childStack: Value<ChildStack<*, OnboardingComponent.Child>> =
        MutableValue(
            ChildStack(
                configuration = Unit,
                instance = OnboardingComponent.Child.PrivacyNotice(PreviewPrivacyNoticeComponent(
                    UiState.loadingState()))
            )
        )
}
