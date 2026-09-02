package io.kotelliada.kotlinconf.network

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.kotelliada.kotlinconf.utils.Result
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom

@Inject
@SingleIn(AppScope::class)
class YearlyApi(
    private val httpClient: HttpClient,
    private val baseUrl: String = "http://127.0.0.1:8080", // iOS Simulator & Desktop
    // private val baseUrl: String = "http://10.0.2.2:8080", // Android Emulator
    private val globalLogger: Logger,
    private val year: Int = 2026
) {
    private val logger = globalLogger.withTag("YearlyApi")

    suspend fun acceptPrivacyNotice(userId: String): Result<Unit> {
        return httpClient.safeApiCall(
            logger = logger,
            request = {
                post {
                    apiUrl(userId, "sign")
                    setBody(userId)
                }
            },
            onSuccessBodyParser = { }
        )
    }

    suspend fun downloadAsset(userId: String, path: String): Result<String> {
        return httpClient.safeApiCall(
            logger = logger,
            request = { get { apiUrl(userId, path) } },
            onSuccessBodyParser = { bodyAsText() }
        )
    }

    private fun HttpRequestBuilder.apiUrl(userId: String, path: String) {
        header(HttpHeaders.Authorization, "Bearer $userId")
        header(HttpHeaders.CacheControl, "no-cache")
        url {
            takeFrom(baseUrl)
            encodedPath = "$year/$path"
        }
    }
}
