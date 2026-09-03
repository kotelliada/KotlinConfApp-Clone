package io.kotelliada.kotlinconf.onboarding

import co.touchlab.kermit.Logger
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import io.kotelliada.kotlinconf.ConferenceService
import io.kotelliada.kotlinconf.privacynotice.DefaultPrivacyNoticeComponent
import io.kotelliada.kotlinconf.privacynotice.PrivacyNoticeComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainCoroutineDispatcher
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class DefaultOnboardingComponent(
    componentContext: ComponentContext,
    private val onOnboardingCompleted: () -> Unit,
    private val conferenceService: ConferenceService,
    private val mainDispatcher: MainCoroutineDispatcher,
    private val defaultDispatcher: CoroutineDispatcher,
    private val globalLogger: Logger
) : ComponentContext by componentContext, OnboardingComponent {

    private val retainedLogic = instanceKeeper.getOrCreate { RetainedLogic(mainDispatcher) }

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
            is PrivacyNotice -> OnboardingComponent.Child.PrivacyNotice(
                createPrivacyNoticeComponent(componentContext)
            )

            is Notifications -> OnboardingComponent.Child.Notifications()
        }
    }

    private fun createPrivacyNoticeComponent(
        componentContext: ComponentContext
    ): PrivacyNoticeComponent {
        val onboardingCompletion: () -> Unit = {
            retainedLogic.coroutineScope.launch {
                conferenceService.completeOnboarding()
                onOnboardingCompleted()
            }
        }

        return DefaultPrivacyNoticeComponent(
            componentContext = componentContext,
            onAppTermsOfUse = {
                // TODO: Implement
            },
            conferenceService = conferenceService,
            mainDispatcher = mainDispatcher,
            globalLogger = globalLogger,
            defaultDispatcher = defaultDispatcher,
            onPrivacyAccepted = onboardingCompletion,
            onPrivacyRejected = onboardingCompletion
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

private class RetainedLogic(
    mainDispatcher: MainCoroutineDispatcher
) : InstanceKeeper.Instance {

    val coroutineScope = CoroutineScope(mainDispatcher.immediate + SupervisorJob())

    override fun onDestroy() {
        coroutineScope.cancel()
        super.onDestroy()
    }
}
