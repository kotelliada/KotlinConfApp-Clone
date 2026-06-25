package io.kotelliada.kotlinconf.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

internal const val DATA_STORE_FILE_NAME = "app_settings.preferences_pb"

expect class DataStoreFactory {
    fun createDataStore(): DataStore<Preferences>
}
