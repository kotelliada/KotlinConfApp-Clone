package io.kotelliada.kotlinconf

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.lifecycle.resume
import com.arkivanov.essenty.lifecycle.stop
import dev.zacsweers.metro.createGraphFactory
import io.kotelliada.kotlinconf.di.AppGraph
import io.kotelliada.kotlinconf.di.DataStoreFactory
import io.kotelliada.kotlinconf.root.DefaultRootComponent
import web.dom.DocumentVisibilityState
import web.dom.document
import web.dom.visible
import web.events.Event
import web.events.VISIBILITY_CHANGE
import web.events.addEventListener

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val appGraph = createGraphFactory<AppGraph.Factory>()
        .create(DataStoreFactory())

    val lifecycle = LifecycleRegistry()
    val rootComponent = DefaultRootComponent(
        componentContext = DefaultComponentContext(lifecycle = lifecycle)
    )

    lifecycle.attachToDocument()

    ComposeViewport {
        App(rootComponent = rootComponent)
    }
}

// Attaches the LifecycleRegistry to the document
private fun LifecycleRegistry.attachToDocument() {
    fun onVisibilityChanged() {
        if (document.visibilityState == DocumentVisibilityState.visible) {
            resume()
        } else {
            stop()
        }
    }

    onVisibilityChanged()

    document.addEventListener(
        type = Event.VISIBILITY_CHANGE,
        handler = { onVisibilityChanged() }
    )
}
