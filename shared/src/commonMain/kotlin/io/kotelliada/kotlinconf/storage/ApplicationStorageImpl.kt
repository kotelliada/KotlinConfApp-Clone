package io.kotelliada.kotlinconf.storage

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import io.kotelliada.kotlinconf.getPlatform
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

class ApplicationStorageImpl(
    private val settings: DataStore<Preferences>,
    appScope: CoroutineScope
) : ApplicationStorage {

    private val keyUserId = stringPreferencesKey("userId")
    private val keyOnboardingComplete = booleanPreferencesKey("onboardingComplete")

    override val userId: StateFlow<String> = settings.data
        .map { preferences -> preferences[keyUserId] ?: "" }
        .stateIn(appScope, SharingStarted.Eagerly, "")

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

    @OptIn(ExperimentalUuidApi::class)
    override suspend fun initialize() {
        val existingUserId = settings.data
            .map { preferences -> preferences[keyUserId] ?: "" }
            .first()

        if (existingUserId.isBlank()) {
            val generatedUserId = "${getPlatform().id}-${Uuid.random()}"
            settings.updateData {
                it.toMutablePreferences().also { preferences ->
                    preferences[keyUserId] = generatedUserId
                }
            }
        }
    }
}
