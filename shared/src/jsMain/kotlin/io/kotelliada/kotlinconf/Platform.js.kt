package io.kotelliada.kotlinconf

class JsPlatform: Platform {
    override val name: String = "Web with Kotlin/JS"

    override val id: String = "web-js"
}

actual fun getPlatform(): Platform = JsPlatform()
