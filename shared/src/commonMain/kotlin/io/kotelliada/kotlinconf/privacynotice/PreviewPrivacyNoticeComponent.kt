package io.kotelliada.kotlinconf.privacynotice

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import io.kotelliada.kotlinconf.privacynotice.main.PreviewPrivacyNoticeMainComponent

class PreviewPrivacyNoticeComponent : PrivacyNoticeComponent {

    override val childStack: Value<ChildStack<*, PrivacyNoticeComponent.Child>> =
        MutableValue(
            ChildStack(
                configuration = Unit,
                instance = PrivacyNoticeComponent.Child.Main(PreviewPrivacyNoticeMainComponent())
            )
        )

    override fun onRejectNoticeClicked() {
    }

    override fun onAcceptNoticeClicked() {
    }

    override fun onAppTermsOfUseClicked() {
    }
}
