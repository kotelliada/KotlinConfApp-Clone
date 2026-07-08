package io.kotelliada.kotlinconf

import android.app.Application
import dev.zacsweers.metro.createGraphFactory
import io.kotelliada.kotlinconf.di.AppGraph
import io.kotelliada.kotlinconf.di.DataStoreFactory
import kotlinx.coroutines.Dispatchers

class KotlinConfApplication : Application() {

    val appGraph: AppGraph by lazy(LazyThreadSafetyMode.NONE) {
        createGraphFactory<AppGraph.Factory>()
            .create(
                dataStoreFactory = DataStoreFactory(this),
                ioDispatcher = Dispatchers.IO
            )
    }
}
