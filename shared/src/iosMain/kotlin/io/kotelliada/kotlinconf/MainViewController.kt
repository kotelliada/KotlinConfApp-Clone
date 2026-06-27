package io.kotelliada.kotlinconf

import androidx.compose.ui.window.ComposeUIViewController
import dev.zacsweers.metro.createGraphFactory
import io.kotelliada.kotlinconf.di.AppGraph
import io.kotelliada.kotlinconf.di.DataStoreFactory

private val appGraph = createGraphFactory<AppGraph.Factory>()
    .create(DataStoreFactory())

fun MainViewController() = ComposeUIViewController { App() }
