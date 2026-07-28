package io.kotelliada.kotlinconf.privacynotice.details

import com.mikepenz.markdown.model.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PreviewPrivacyNoticeDetailsComponent(uiState: State) : PrivacyNoticeDetailsComponent {

    override val uiState: StateFlow<State> = MutableStateFlow(uiState)

    override fun onBackButtonClicked() {
    }
}
