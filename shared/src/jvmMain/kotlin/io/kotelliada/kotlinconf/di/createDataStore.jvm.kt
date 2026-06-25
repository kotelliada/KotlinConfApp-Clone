package io.kotelliada.kotlinconf.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import okio.Path.Companion.toPath
import java.io.File

actual class DataStoreFactory {

    actual fun createDataStore(): DataStore<Preferences> {
        return PreferenceDataStoreFactory.createWithPath(
            produceFile = {
                // Resolves to a hidden directory in the user's home folder, e.g., ~/.myapp/
                val userHome = System.getProperty("user.home")
                println("User home is $userHome")

                val appConfigDir = File(userHome, ".kotlin-conf-clone-app")

                // Ensure the directory exists before DataStore tries to write to it
                if (!appConfigDir.exists()) {
                    println("Folder ${appConfigDir.absolutePath} does not exist")
                    appConfigDir.mkdirs()
                } else {
                    println("Folder ${appConfigDir.absolutePath} already exists")
                }

                val prefsFile = File(appConfigDir, DATA_STORE_FILE_NAME)
                println("Prefs file is ${prefsFile.absolutePath}")

                prefsFile.absolutePath.toPath()
            }
        )
    }
}
