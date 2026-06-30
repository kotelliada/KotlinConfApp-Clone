package io.kotelliada.kotlinconf.privacynotice

import com.arkivanov.decompose.ComponentContext

class DefaultPrivacyNoticeComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, PrivacyNoticeComponent {
}
