package io.kotelliada.kotlinconf.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.di.AppGraph
import io.kotelliada.kotlinconf.home.DefaultHomeComponent
import io.kotelliada.kotlinconf.home.HomeComponent
import io.kotelliada.kotlinconf.onboarding.DefaultOnboardingComponent
import io.kotelliada.kotlinconf.onboarding.OnboardingComponent
import io.kotelliada.kotlinconf.utils.ComponentLifecycleLogger
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val appGraph: AppGraph,
) : RootComponent, ComponentContext by componentContext {

    private val logger = appGraph.globalLogger.withTag("RootComponent")

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Home,
            handleBackButton = true,
            childFactory = ::createChild
        )

    init {
        val componentLifecycleLogger =
            ComponentLifecycleLogger("RootComponent") { message ->
                logger.d { message }
            }

        lifecycle.subscribe(componentLifecycleLogger)

        appGraph.appCoroutineScope.launch {
            appGraph.appInitializer.isAppInitializedFlow
                .first { it }

            logger.d { "App has been initialized" }
        }
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            is Config.Home -> RootComponent.Child.Home(createHomeComponent(componentContext))
            is Config.Onboarding -> RootComponent.Child.Onboarding(
                createOnboardingComponent(componentContext)
            )
        }
    }

    private fun createHomeComponent(componentContext: ComponentContext): HomeComponent {
        return DefaultHomeComponent(
            componentContext = componentContext,
            appGraph = appGraph,
            launchOnboarding = {
                logger.d { "Requested to launch onboarding from HomeComponent" }
                navigation.replaceAll(Config.Onboarding)
            }
        )
    }

    private fun createOnboardingComponent(
        componentContext: ComponentContext
    ): OnboardingComponent {
        return DefaultOnboardingComponent(componentContext)
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Home : Config()

        @Serializable
        data object Onboarding : Config()
    }
}
