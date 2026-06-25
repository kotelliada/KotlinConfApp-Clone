package io.kotelliada.kotlinconf

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"

    override val id: String = "desktop"
}

actual fun getPlatform(): Platform = JVMPlatform()
