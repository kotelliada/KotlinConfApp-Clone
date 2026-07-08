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
import kotlinx.coroutines.Dispatchers

fun main() {
    val appGraph = createGraphFactory<AppGraph.Factory>()
        .create(
            dataStoreFactory = DataStoreFactory(),
            ioDispatcher = Dispatchers.IO
        )

    val lifecycle = LifecycleRegistry()
    val rootComponent = runOnUiThread {
        DefaultRootComponent(
            componentContext = DefaultComponentContext(lifecycle = lifecycle),
            appGraph = appGraph
        )
    }

    application {
        val windowState = rememberWindowState()

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            title = "Kotlinconfclone",
        ) {
            App(component = rootComponent)
        }
    }
}
