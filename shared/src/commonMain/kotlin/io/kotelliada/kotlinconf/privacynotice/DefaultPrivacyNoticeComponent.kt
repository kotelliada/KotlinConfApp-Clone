package io.kotelliada.kotlinconf.privacynotice

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.privacynotice.details.DefaultPrivacyNoticeDetailsComponent
import io.kotelliada.kotlinconf.privacynotice.details.PrivacyNoticeDetailsComponent
import io.kotelliada.kotlinconf.privacynotice.main.DefaultPrivacyNoticeMainComponent
import io.kotelliada.kotlinconf.privacynotice.main.PrivacyNoticeMainComponent
import kotlinx.serialization.Serializable

class DefaultPrivacyNoticeComponent(
    componentContext: ComponentContext,
    private val onRejectNotice: () -> Unit,
    private val onAcceptNotice: () -> Unit,
    private val onAppTermsOfUse: () -> Unit,
    private val confirmationRequired: Boolean,
) : ComponentContext by componentContext, PrivacyNoticeComponent {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, PrivacyNoticeComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Main,
            handleBackButton = true,
            childFactory = ::createChild
        )

    override fun onRejectNoticeClicked() {
    }

    override fun onAcceptNoticeClicked() {
    }

    override fun onAppTermsOfUseClicked() {
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): PrivacyNoticeComponent.Child {
        return when (config) {
            is Config.Main -> PrivacyNoticeComponent.Child.Main(createPrivacyNoticeMainComponent(componentContext))
            is Config.Details -> PrivacyNoticeComponent.Child.Details(createPrivacyNoticeDetailsComponent(componentContext))
        }
    }

    private fun createPrivacyNoticeMainComponent(
        componentContext: ComponentContext
    ): PrivacyNoticeMainComponent {
        return DefaultPrivacyNoticeMainComponent(componentContext)
    }

    private fun createPrivacyNoticeDetailsComponent(
        componentContext: ComponentContext
    ): PrivacyNoticeDetailsComponent {
        return DefaultPrivacyNoticeDetailsComponent(componentContext)
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Main : Config()

        @Serializable
        data object Details : Config()
    }
}
