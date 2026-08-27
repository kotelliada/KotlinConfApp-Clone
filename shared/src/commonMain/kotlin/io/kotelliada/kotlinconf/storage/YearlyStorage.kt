package io.kotelliada.kotlinconf.storage

import kotlinx.coroutines.flow.Flow

interface YearlyStorage {
    suspend fun getAsset(name: String): String?
    fun observeAsset(name: String): Flow<String>
    suspend fun setAsset(name: String, content: String)

    suspend fun isPolicySigned(): Boolean
    suspend fun setPolicySigned(value: Boolean)
}
