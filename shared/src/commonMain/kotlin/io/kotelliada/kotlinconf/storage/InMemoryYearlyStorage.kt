package io.kotelliada.kotlinconf.storage

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
@Inject
class InMemoryYearlyStorage : YearlyStorage {

    private val assetsMutex = Mutex()
    private val assets = hashMapOf<String, String>()

    override suspend fun getAsset(name: String): String? {
        return assetsMutex.withLock { assets[name] }
    }

    override suspend fun setAsset(name: String, content: String) {
        assetsMutex.withLock { assets[name] = content }
    }
}
