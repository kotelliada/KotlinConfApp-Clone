package io.kotelliada.kotlinconf.privacynotice

data class UiState(
    val isLoading: Boolean,
    val isError: Boolean,
    val isContent: Boolean
) {
    companion object {
        fun loadingState() = UiState(isLoading = true, isError = false, isContent = false)

        fun errorState() = UiState(isLoading = false, isError = true, isContent = false)

        fun contentState() = UiState(isLoading = false, isError = false, isContent = true)
    }
}
