package io.kotelliada.kotlinconf.privacynotice

data class UiState(
    val isDocumentLoading: Boolean = false,
    val isDocumentLoadingError: Boolean = false,
    val isDocumentReady: Boolean = false,

    val isAcceptingPrivacy: Boolean = false,
    val isAcceptingPrivacyError: Boolean = false,
    val isAcceptingPrivacyAvailable: Boolean = false,

    val isRejectingPrivacy: Boolean = false,
    val isRejectingPrivacyError: Boolean = false,
    val isRejectingPrivacyAvailable: Boolean = false,
)
