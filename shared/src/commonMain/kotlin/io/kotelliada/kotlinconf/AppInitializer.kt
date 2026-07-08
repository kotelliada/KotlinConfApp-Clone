package io.kotelliada.kotlinconf

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.kotelliada.kotlinconf.storage.ApplicationStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

@SingleIn(AppScope::class)
@Inject
class AppInitializer(
    globalLogger: Logger,
    appCoroutineScope: CoroutineScope,
    private val applicationStorage: ApplicationStorage
) {

    val isAppInitializedFlow: StateFlow<Boolean> = flow {
        globalLogger.d(tag = "AppInitializer") { "Initialization flow started" }
        applicationStorage.initialize()
        emit(true)
    }.stateIn(appCoroutineScope, SharingStarted.Lazily, false)
}
