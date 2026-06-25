package io.kotelliada.kotlinconf.storage

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ApplicationStorage {
    val userId: StateFlow<String>

    fun isOnboardingComplete(): Flow<Boolean>
    suspend fun setOnboardingComplete(value: Boolean)

    suspend fun initialize()
}
