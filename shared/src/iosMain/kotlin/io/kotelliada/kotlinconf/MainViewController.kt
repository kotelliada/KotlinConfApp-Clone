package io.kotelliada.kotlinconf

import androidx.compose.ui.window.ComposeUIViewController
import dev.zacsweers.metro.createGraphFactory
import io.kotelliada.kotlinconf.di.AppGraph
import io.kotelliada.kotlinconf.di.DataStoreFactory
import io.kotelliada.kotlinconf.root.RootComponent

private val appGraph = createGraphFactory<AppGraph.Factory>()
    .create(DataStoreFactory())

fun MainViewController(rootComponent: RootComponent) = ComposeUIViewController {
    App(rootComponent = rootComponent)
}
