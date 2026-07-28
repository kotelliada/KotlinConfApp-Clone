package io.kotelliada.kotlinconf.privacynotice

sealed class UiState {
    object Loading : UiState()

    object Error : UiState()

    data class Content(val privacyNotice: String) : UiState()
}
