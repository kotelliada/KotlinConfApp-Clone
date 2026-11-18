package io.kotelliada.kotlinconf.speakers.list

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PreviewSpeakersListComponent(uiState: UiState) : SpeakersListComponent {

    override val uiState: StateFlow<UiState> = MutableStateFlow(uiState)

    override fun onSearchClicked() {
    }

    override fun onSearchValueChanged(newSearchValue: String) {
    }

    override fun onCloseSearchClicked() {
    }

    override fun onRetryToLoadClicked() {
    }

    override fun onSpeakerClicked(speaker: SpeakerWithHighlights) {
    }
}
