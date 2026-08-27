package io.kotelliada.kotlinconf.storage

import kotlinx.coroutines.flow.Flow

interface ApplicationStorage {
    val userId: Flow<String>

    fun isOnboardingComplete(): Flow<Boolean>
    suspend fun setOnboardingComplete(value: Boolean)
}
