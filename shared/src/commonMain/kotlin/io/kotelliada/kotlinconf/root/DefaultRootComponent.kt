package io.kotelliada.kotlinconf.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import io.kotelliada.kotlinconf.di.AppGraph
import io.kotelliada.kotlinconf.main.DefaultMainComponent
import io.kotelliada.kotlinconf.privacynotice.DefaultPrivacyNoticeComponent
import kotlinx.serialization.Serializable

class DefaultRootComponent(
    componentContext: ComponentContext,
    private val appGraph: AppGraph,
) : RootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    override val childStack: Value<ChildStack<*, RootComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.Main,
            handleBackButton = true,
            childFactory = ::createChild
        )

    init {
        lifecycle.subscribe(object: Lifecycle.Callbacks {
            override fun onCreate() {
                super.onCreate()
                println("RootComponent.onCreate")
            }

            override fun onDestroy() {
                super.onDestroy()
                println("RootComponent.onDestroy")
            }

            override fun onPause() {
                super.onPause()
                println("RootComponent.onPause")
            }

            override fun onResume() {
                super.onResume()
                println("RootComponent.onResume")
            }

            override fun onStart() {
                super.onStart()
                println("RootComponent.onStart")
            }

            override fun onStop() {
                super.onStop()
                println("RootComponent.onStop")
            }
        })
    }

    private fun createChild(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            is Config.Main -> RootComponent.Child.Main(DefaultMainComponent(componentContext))
            is Config.PrivacyNotice -> RootComponent.Child.PrivacyNotice(
                DefaultPrivacyNoticeComponent(componentContext))
        }
    }

    @Serializable
    private sealed class Config {
        @Serializable
        data object Main : Config()

        @Serializable
        data object PrivacyNotice : Config()
    }
}
