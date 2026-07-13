package io.kotelliada.kotlinconf.di

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

expect fun createHttpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient
