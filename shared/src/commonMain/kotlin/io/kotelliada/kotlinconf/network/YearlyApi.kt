package io.kotelliada.kotlinconf.network

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.kotelliada.kotlinconf.utils.Result
import io.kotelliada.kotlinconf.utils.UnknownError
import io.ktor.client.HttpClient
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.encodedPath
import io.ktor.http.takeFrom
import kotlinx.coroutines.CancellationException

@Inject
@SingleIn(AppScope::class)
class YearlyApi(
    private val httpClient: HttpClient,
    // private val baseUrl: String = "http://127.0.0.1:8080", // iOS Simulator & Desktop
    private val baseUrl: String = "http://10.0.2.2:8080", // Android Emulator
    private val globalLogger: Logger,
    private val year: Int = 2026
) {
    private val logger = globalLogger.withTag("YearlyApi")

    suspend fun downloadAsset(userId: String, path: String): Result<String> {
        return safeApiCall {
            httpClient.get { apiUrl(userId, path) }.bodyAsText()
        }
    }

    private fun HttpRequestBuilder.apiUrl(userId: String, path: String) {
        header(HttpHeaders.Authorization, "Bearer $userId")
        header(HttpHeaders.CacheControl, "no-cache")
        url {
            takeFrom(baseUrl)
            encodedPath = "$year/$path"
        }
    }

    private suspend fun <T> safeApiCall(call: suspend () -> T): Result<T> {
        return try {
            // TODO: Add status codes mapping
            val response = call()
            Result.Success(response)
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: Throwable) {
            // TODO: Add errors mapping
            logger.e { "API call failed: ${ex.message}" }
            Result.Failure(UnknownError("Something went wrong: ${ex.message}"))
        }
    }
}
