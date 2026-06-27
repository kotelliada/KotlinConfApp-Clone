package io.kotelliada.kotlinconf.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@ContributesTo(AppScope::class)
@BindingContainer
object AppBindings {

    @Provides
    @SingleIn(AppScope::class)
    fun provideAppCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.Default + SupervisorJob())
    }

    @Provides
    @SingleIn(AppScope::class)
    fun provideDataStorePreferences(dataStoreFactory: DataStoreFactory): DataStore<Preferences> {
        return dataStoreFactory.createDataStore()
    }
}
