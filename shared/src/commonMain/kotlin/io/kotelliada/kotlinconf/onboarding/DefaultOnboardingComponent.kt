package io.kotelliada.kotlinconf.onboarding

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.privacynotice.DefaultPrivacyNoticeComponent
import io.kotelliada.kotlinconf.privacynotice.PrivacyNoticeComponent
import kotlinx.serialization.Serializable

class DefaultOnboardingComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, OnboardingComponent {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, OnboardingComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.PrivacyNotice,
            handleBackButton = true,
            childFactory = ::createChild
        )

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): OnboardingComponent.Child {
        return when (config) {
            is Config.PrivacyNotice -> OnboardingComponent.Child.PrivacyNotice(
                createPrivacyNoticeComponent(componentContext)
            )

            is Config.Notifications -> OnboardingComponent.Child.Notifications()
        }
    }

    private fun createPrivacyNoticeComponent(
        componentContext: ComponentContext
    ): PrivacyNoticeComponent {
        return DefaultPrivacyNoticeComponent(
            componentContext = componentContext,
            onRejectNotice = {},
            onAcceptNotice = {},
            onAppTermsOfUse = {},
            confirmationRequired = false
        )
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object PrivacyNotice : Config()

        @Serializable
        data object Notifications : Config()
    }
}
