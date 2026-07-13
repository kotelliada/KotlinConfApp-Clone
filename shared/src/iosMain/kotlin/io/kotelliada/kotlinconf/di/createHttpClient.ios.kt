package io.kotelliada.kotlinconf.di

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin

actual fun createHttpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient {
    return HttpClient(Darwin) {
        config(this)

        engine {
            configureRequest {
                setAllowsCellularAccess(true)
            }
        }
    }
}
