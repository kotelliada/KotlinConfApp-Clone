package io.kotelliada.kotlinconf

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.zacsweers.metro.createGraphFactory
import io.kotelliada.kotlinconf.di.AppGraph
import io.kotelliada.kotlinconf.di.DataStoreFactory

fun main() = application {
    val appGraph = createGraphFactory<AppGraph.Factory>()
        .create(DataStoreFactory())

    Window(
        onCloseRequest = ::exitApplication,
        title = "Kotlinconfclone",
    ) {
        App()
    }
}
