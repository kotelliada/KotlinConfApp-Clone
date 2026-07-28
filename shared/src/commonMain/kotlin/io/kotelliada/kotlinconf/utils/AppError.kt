package io.kotelliada.kotlinconf.utils

import kotlin.Throwable

open class AppError(message: String? = null) : Throwable(message)

class UnknownError(message: String? = null) : AppError(message)
