package io.kotelliada.kotlinconf

import android.app.Application
import dev.zacsweers.metro.createGraphFactory
import io.kotelliada.kotlinconf.di.AppGraph
import io.kotelliada.kotlinconf.di.DataStoreFactory

class KotlinConfApplication : Application() {

    val appGraph: AppGraph by lazy(LazyThreadSafetyMode.NONE) {
        createGraphFactory<AppGraph.Factory>()
            .create(DataStoreFactory(this))
    }
}
