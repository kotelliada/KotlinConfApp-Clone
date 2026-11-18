package io.kotelliada.kotlinconf.speakers.list

import io.kotelliada.kotlinconf.ui.components.MainHeaderContainerState

data class UiState(
    val searchState: MainHeaderContainerState = MainHeaderContainerState.Title,
    val searchText: String = "",

    val isLoading: Boolean = false,

    val isError: Boolean = false,
    val errorMessage: String = "",

    val speakers: List<SpeakerWithHighlights> = emptyList()
)
