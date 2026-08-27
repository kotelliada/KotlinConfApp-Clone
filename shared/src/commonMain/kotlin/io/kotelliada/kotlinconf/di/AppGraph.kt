package io.kotelliada.kotlinconf.di

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import io.kotelliada.kotlinconf.ConferenceService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainCoroutineDispatcher

@DependencyGraph(AppScope::class)
interface AppGraph {
    val globalLogger: Logger
    val appCoroutineScope: CoroutineScope
    val conferenceService: ConferenceService

    @IoDispatcher
    val ioDispatcher: CoroutineDispatcher

    @DefaultDispatcher
    val defaultDispatcher: CoroutineDispatcher

    val mainDispatcher: MainCoroutineDispatcher

    @DependencyGraph.Factory
    interface Factory {
        fun create(
            @Provides dataStoreFactory: DataStoreFactory,
            @Provides @IoDispatcher ioDispatcher: CoroutineDispatcher
        ): AppGraph
    }
}
