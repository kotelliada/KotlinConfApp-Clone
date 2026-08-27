package io.kotelliada.kotlinconf.storage

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.ExperimentalAtomicApi

@OptIn(ExperimentalAtomicApi::class)
@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
@Inject
class InMemoryYearlyStorage : YearlyStorage {

    private val assetsMutex = Mutex()
    private val assets = hashMapOf<String, String>()
    private val assetsUpdates = MutableSharedFlow<String>()

    private val policyStatus = AtomicBoolean(false)

    override fun observeAsset(name: String): Flow<String> {
        return assetsUpdates
            .filter { updatedAssetName -> updatedAssetName == name }
            .map { updatedAssetName -> getAsset(updatedAssetName)!! }
            .onStart {
                val asset = getAsset(name)
                if (asset != null) {
                    emit(asset)
                }
            }
    }

    override suspend fun getAsset(name: String): String? {
        return assetsMutex.withLock { assets[name] }
    }

    override suspend fun setAsset(name: String, content: String) {
        assetsMutex.withLock { assets[name] = content }
        assetsUpdates.emit(name)
    }

    override suspend fun isPolicySigned(): Boolean {
        return policyStatus.load()
    }

    override suspend fun setPolicySigned(value: Boolean) {
        policyStatus.store(value)
    }
}
