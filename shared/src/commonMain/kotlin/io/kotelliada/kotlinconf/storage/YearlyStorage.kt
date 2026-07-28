package io.kotelliada.kotlinconf.storage

interface YearlyStorage {
    suspend fun getAsset(name: String): String?
    suspend fun setAsset(name: String, content: String)
}
