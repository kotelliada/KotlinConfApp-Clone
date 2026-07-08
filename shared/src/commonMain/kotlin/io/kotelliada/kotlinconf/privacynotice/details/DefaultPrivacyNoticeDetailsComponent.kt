package io.kotelliada.kotlinconf.privacynotice.details

import com.arkivanov.decompose.ComponentContext

class DefaultPrivacyNoticeDetailsComponent(
    componentContext: ComponentContext
) : ComponentContext by componentContext, PrivacyNoticeDetailsComponent {
}
