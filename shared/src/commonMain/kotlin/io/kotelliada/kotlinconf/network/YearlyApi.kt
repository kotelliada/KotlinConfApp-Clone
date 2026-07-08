package io.kotelliada.kotlinconf.network

import co.touchlab.kermit.Logger
import io.ktor.client.HttpClient
import kotlinx.coroutines.CancellationException

class YearlyApi(
    private val httpClient: HttpClient,
    private val globalLogger: Logger
) {
    private val logger = Logger.withTag("YearlyApi")

    suspend fun downloadAsset(path: String): String? {
        throw NotImplementedError()
    }

    private suspend fun <T> safeApiCall(call: suspend () -> T): T? {
        return try {
            call()
        } catch (ex: CancellationException) {
            throw ex
        } catch (ex: Throwable) {
            logger.e { "API call failed: ${ex.message}" }
            null
        }
    }
}
