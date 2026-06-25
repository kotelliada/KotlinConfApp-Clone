package io.kotelliada.kotlinconf

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"

    override val id: String = "web-wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()
