package io.kotelliada.kotlinconf

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
