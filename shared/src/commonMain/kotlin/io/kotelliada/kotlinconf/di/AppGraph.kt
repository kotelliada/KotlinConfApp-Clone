package io.kotelliada.kotlinconf.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

@DependencyGraph(AppScope::class)
interface AppGraph {

    @DependencyGraph.Factory
    interface Factory {
        fun create(@Provides dataStoreFactory: DataStoreFactory): AppGraph
    }
}
