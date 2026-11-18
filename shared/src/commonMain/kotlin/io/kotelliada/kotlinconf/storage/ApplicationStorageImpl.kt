package io.kotelliada.kotlinconf.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.kotelliada.kotlinconf.getPlatform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.transform
import kotlin.uuid.Uuid

// @ContributesBinding automatically binds your implementation to its interface
// and contributes it to any graph with the matching scope.
@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
@Inject
class ApplicationStorageImpl(
    private val settings: DataStore<Preferences>,
    appScope: CoroutineScope
) : ApplicationStorage {

    private val keyUserId = stringPreferencesKey("userId")
    private val keyOnboardingComplete = booleanPreferencesKey("onboardingComplete")

    override val userId: Flow<String> = settings.data
        .transform {preferences ->
            val existingUserId = preferences[keyUserId]

            if (existingUserId.isNullOrBlank()) {
                val generatedUserId = "${getPlatform().id}-${Uuid.random()}"
                settings.updateData {
                    it.toMutablePreferences().also { mutablePreferences ->
                        mutablePreferences[keyUserId] = generatedUserId
                    }
                }
            } else {
                emit(existingUserId)
            }
        }
        .distinctUntilChanged()
        .shareIn(
            scope = appScope,
            started = SharingStarted.Lazily,
            replay = 1
        )

    override fun isOnboardingComplete(): Flow<Boolean> {
        return settings.data
            .map { preferences -> preferences[keyOnboardingComplete] ?: false }
    }

    override suspend fun setOnboardingComplete(value: Boolean) {
        settings.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[keyOnboardingComplete] = value
            }
        }
    }
}
