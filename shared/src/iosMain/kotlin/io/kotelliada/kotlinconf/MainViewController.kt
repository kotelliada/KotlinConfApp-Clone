package io.kotelliada.kotlinconf

import androidx.compose.ui.window.ComposeUIViewController
import dev.zacsweers.metro.createGraphFactory
import io.kotelliada.kotlinconf.di.AppGraph
import io.kotelliada.kotlinconf.di.DataStoreFactory
import io.kotelliada.kotlinconf.root.RootComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

val iosAppGraph = createGraphFactory<AppGraph.Factory>()
    .create(
        dataStoreFactory = DataStoreFactory(),
        ioDispatcher = Dispatchers.IO
    )

fun MainViewController(rootComponent: RootComponent) = ComposeUIViewController {
    App(component = rootComponent)
}
