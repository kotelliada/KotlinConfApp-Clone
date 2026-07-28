package io.kotelliada.kotlinconf.privacynotice.main

import com.arkivanov.decompose.ComponentContext
import io.kotelliada.kotlinconf.utils.ComponentLifecycleLogger

class DefaultPrivacyNoticeMainComponent(
    componentContext: ComponentContext,
    private val onReadPrivacyNotice: () -> Unit
) : ComponentContext by componentContext, PrivacyNoticeMainComponent {

    init {
        lifecycle.subscribe(ComponentLifecycleLogger(
            componentName = "PrivacyNoticeMainComponent",
            log = { println(it) }
        ))
    }

    override fun onReadPrivacyNoticeClicked() {
        onReadPrivacyNotice()
    }
}
