package io.kotelliada.kotlinconf.utils

open class AppError(message: String? = null) : Throwable(message)

/** No connectivity at all: host cannot be resolved, interface is down. */
class NetworkUnavailableError(
    message: String? = "Network is unavailable. Check your connection."
) : AppError(message)

class TimeoutError(message: String? = "The request timed out.") : AppError(message)

/** 404 */
class NotFoundError(message: String? = "Not found.") : AppError(message)

/** 429 */
class TooManyRequestsError(
    message: String? = "Too many requests. Try again later."
) : AppError(message)

/** 5xx */
class ServerError(message: String? = "Server error.") : AppError(message)

class UnknownError(message: String? = null) : AppError(message)
