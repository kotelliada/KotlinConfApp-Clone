package io.kotelliada.kotlinconf.privacynotice.main

import com.arkivanov.decompose.ComponentContext

class DefaultPrivacyNoticeMainComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, PrivacyNoticeMainComponent {
}
