package io.kotelliada.kotlinconf.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher
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

    @Provides
    fun provideGlobalLogger(): Logger {
        return Logger
    }

    @Provides
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Provides
    fun provideMainDispatcher(): MainCoroutineDispatcher {
        return Dispatchers.Main
    }

    @Provides
    @SingleIn(AppScope::class)
    fun provideHttpClient(globalLogger: Logger): HttpClient {
        return createHttpClient {
            install(ContentNegotiation) {
                json()
            }

            install(Logging) {
                level = LogLevel.HEADERS
                logger = object: io.ktor.client.plugins.logging.Logger {
                    private val taggedLogger = globalLogger.withTag("HttpClient")

                    override fun log(message: String) {
                        taggedLogger.d { message }
                    }
                }
            }

            expectSuccess = true

            install(HttpTimeout) {
                requestTimeoutMillis = 5000
            }

            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 3)
                exponentialDelay()
            }
        }
    }
}
