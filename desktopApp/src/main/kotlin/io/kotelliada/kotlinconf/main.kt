package io.kotelliada.kotlinconf

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import dev.zacsweers.metro.createGraphFactory
import io.kotelliada.kotlinconf.di.AppGraph
import io.kotelliada.kotlinconf.di.DataStoreFactory
import io.kotelliada.kotlinconf.root.DefaultRootComponent

fun main() {
    val appGraph = createGraphFactory<AppGraph.Factory>()
        .create(DataStoreFactory())

    val lifecycle = LifecycleRegistry()
    val rootComponent = runOnUiThread {
        DefaultRootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle)
        )
    }

    application {
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            title = "Kotlinconfclone",
        ) {
            App(rootComponent = rootComponent)
        }
    }
}
