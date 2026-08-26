package io.kotelliada.kotlinconf.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper
import kotlinconfclone.shared.generated.resources.Res
import kotlinconfclone.shared.generated.resources.action_close
import kotlinconfclone.shared.generated.resources.action_error_reload
import kotlinconfclone.shared.generated.resources.loading
import org.jetbrains.compose.resources.stringResource

@Composable
fun DialogErrorLoading(
    errorMessage: String,
    isLoading: Boolean = false,
    closeText: String = stringResource(Res.string.action_close),
    loadingText: String = stringResource(Res.string.loading),
    retryText: String = stringResource(Res.string.action_error_reload),
    onRetry: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null
) {
    Dialog(
        onDismissRequest = {},
        properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        DialogContent(
            errorMessage = errorMessage,
            isLoading = isLoading,
            loadingText = loadingText,
            retryText = retryText,
            onRetry = onRetry,
            closeText = closeText,
            onClose = onClose
        )
    }
}

@Composable
private fun DialogContent(
    errorMessage: String,
    isLoading: Boolean = false,
    loadingText: String = stringResource(Res.string.loading),
    closeText: String = stringResource(Res.string.action_close),
    retryText: String = stringResource(Res.string.action_error_reload),
    onRetry: (() -> Unit)? = null,
    onClose: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .background(
                shape = KotlinConfTheme.shapes.roundedCornerSm,
                color = KotlinConfTheme.colors.dialogBackground
            )
            .sizeIn(minWidth = 280.dp, maxWidth = 560.dp)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            Loading()

            Spacer(modifier = Modifier.height(16.dp))
        }

        AnimatedContent(targetState = isLoading) { loadingState: Boolean ->
            if (loadingState) {
                ErrorText(message = loadingText)
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ErrorText(message = errorMessage)

                    if (onRetry != null || onClose != null) {
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            if (onRetry != null) {
                                Button(label = retryText, onClick = onRetry)
                            }

                            if (onClose != null) {
                                Button(
                                    primary = false,
                                    label = closeText,
                                    onClick = onClose
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@PreviewLightDark
@Composable
private fun LoadingPreview() {
    PreviewHelper {
        DialogContent(
            errorMessage = "Something went wrong",
            isLoading = true,
            onRetry = {},
            onClose = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun ErrorPreview() {
    PreviewHelper {
        DialogContent(
            errorMessage = "Something went wrong",
            isLoading = false
        )
    }
}

@PreviewLightDark
@Composable
private fun ErrorWithRetryPreview() {
    PreviewHelper {
        DialogContent(
            errorMessage = "Something went wrong",
            isLoading = false,
            onRetry = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun ErrorWithRetryAndClosePreview() {
    PreviewHelper {
        DialogContent(
            errorMessage = "Something went wrong",
            isLoading = false,
            onRetry = {},
            onClose = {}
        )
    }
}
