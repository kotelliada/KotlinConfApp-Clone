package io.kotelliada.kotlinconf

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import dev.zacsweers.metro.createGraphFactory
import io.kotelliada.kotlinconf.di.AppGraph
import io.kotelliada.kotlinconf.di.DataStoreFactory

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val appGraph = createGraphFactory<AppGraph.Factory>()
        .create(DataStoreFactory())

    ComposeViewport {
        App()
    }
}
