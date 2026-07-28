package io.kotelliada.kotlinconf.privacynotice.details

import com.mikepenz.markdown.model.State
import kotlinx.coroutines.flow.StateFlow

interface PrivacyNoticeDetailsComponent {
    val uiState: StateFlow<State>

    fun onBackButtonClicked()
}
