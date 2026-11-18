package io.kotelliada.kotlinconf.speakers.list

import kotlinx.coroutines.flow.StateFlow

interface SpeakersListComponent {
    val uiState: StateFlow<UiState>

    fun onSearchClicked()

    fun onSearchValueChanged(newSearchValue: String)

    fun onCloseSearchClicked()

    fun onRetryToLoadClicked()

    fun onSpeakerClicked(speaker: SpeakerWithHighlights)
}
