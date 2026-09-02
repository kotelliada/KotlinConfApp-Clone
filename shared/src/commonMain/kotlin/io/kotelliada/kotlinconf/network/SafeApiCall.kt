package io.kotelliada.kotlinconf.network

import co.touchlab.kermit.Logger
import io.kotelliada.kotlinconf.utils.NetworkUnavailableError
import io.kotelliada.kotlinconf.utils.NotFoundError
import io.kotelliada.kotlinconf.utils.Result
import io.kotelliada.kotlinconf.utils.ServerError
import io.kotelliada.kotlinconf.utils.TimeoutError
import io.kotelliada.kotlinconf.utils.TooManyRequestsError
import io.kotelliada.kotlinconf.utils.UnknownError
import io.ktor.client.HttpClient
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.serialization.JsonConvertException
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.coroutines.CancellationException
import kotlinx.serialization.SerializationException

internal suspend fun <T> HttpClient.safeApiCall(
    logger: Logger,
    request: suspend HttpClient.() -> HttpResponse,
    onSuccessBodyParser: suspend HttpResponse.() -> T
): Result<T> {
    try {
        val response = request()

        return if (response.status.isSuccess()) {
            val parsedBody = onSuccessBodyParser(response)
            Result.Success(parsedBody)
        } else if (response.status.value == HttpStatusCode.NotFound.value) {
            Result.Failure(NotFoundError())
        } else if (response.status.value == HttpStatusCode.TooManyRequests.value) {
            Result.Failure(TooManyRequestsError())
        } else if (response.status.value in 500..599) {
            Result.Failure(ServerError())
        } else {
            Result.Failure(UnknownError())
        }
    } catch (ex: CancellationException) {
        throw ex
    } catch (ex: Throwable) {
        logger.e { "API call failed: ${ex.message}" }

        return when (ex) {
            is HttpRequestTimeoutException,
            is SocketTimeoutException,
            is ConnectTimeoutException -> Result.Failure(TimeoutError())

            is UnresolvedAddressException -> Result.Failure(NetworkUnavailableError())

            // A 2xx with a body we cannot parse is a contract bug, not a network problem
            is JsonConvertException,
            is SerializationException -> Result.Failure(UnknownError("Malformed response: ${ex.message}"))

            else -> Result.Failure(UnknownError("Unexpected client error: ${ex.message}"))
        }
    }
}
