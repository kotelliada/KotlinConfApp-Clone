package io.kotelliada.kotlinconf.utils

import com.arkivanov.essenty.lifecycle.Lifecycle

class ComponentLifecycleLogger(
    private val componentName: String,
    private val log: (String) -> Unit
): Lifecycle.Callbacks {

    override fun onCreate() {
        super.onCreate()
        log("$componentName.onCreate")
    }

    override fun onResume() {
        super.onResume()
        log("$componentName.onResume")
    }

    override fun onStart() {
        super.onStart()
        log("$componentName.onStart")
    }

    override fun onPause() {
        super.onPause()
        log("$componentName.onPause")
    }

    override fun onStop() {
        super.onStop()
        log("$componentName.onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        log("$componentName.onDestroy")
    }
}
