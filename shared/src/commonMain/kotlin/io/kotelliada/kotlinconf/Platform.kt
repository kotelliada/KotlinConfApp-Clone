package io.kotelliada.kotlinconf

interface Platform {
    val name: String
    val id: String
}

expect fun getPlatform(): Platform
