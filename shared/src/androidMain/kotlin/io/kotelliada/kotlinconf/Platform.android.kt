package io.kotelliada.kotlinconf

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"

    override val id: String = "android"
}

actual fun getPlatform(): Platform = AndroidPlatform()
