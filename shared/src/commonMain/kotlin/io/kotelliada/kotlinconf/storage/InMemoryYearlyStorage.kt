package io.kotelliada.kotlinconf.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
@Inject
class InMemoryYearlyStorage(
    private val dataStore: DataStore<Preferences>
) : YearlyStorage {

    private val assetsMutex = Mutex()
    private val assets = hashMapOf<String, String>()
    private val assetsUpdates = MutableSharedFlow<String>()

    private val keyPolicySignatureStatus = booleanPreferencesKey("isPolicySigned")

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
        return dataStore.data
            .map { preferences -> preferences[keyPolicySignatureStatus] ?: false }
            .first()
    }

    override suspend fun setPolicySigned(value: Boolean) {
        dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[keyPolicySignatureStatus] = value
            }
        }
    }
}
