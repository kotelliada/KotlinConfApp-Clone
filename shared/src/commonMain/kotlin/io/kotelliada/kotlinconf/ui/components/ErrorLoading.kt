package io.kotelliada.kotlinconf.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper
import kotlinconfclone.shared.generated.resources.Res
import kotlinconfclone.shared.generated.resources.action_error_reload
import kotlinconfclone.shared.generated.resources.loading
import org.jetbrains.compose.resources.stringResource

@Composable
fun NormalErrorWithLoading(
    errorMessage: String,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    loadingText: String = stringResource(Res.string.loading),
    retryText: String = stringResource(Res.string.action_error_reload),
    onRetry: (() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            Loading()
        }

        Spacer(modifier = Modifier.height(16.dp))

        AnimatedContent(targetState = isLoading) { loadingState: Boolean ->
            if (loadingState) {
                ErrorText(message = loadingText)
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    ErrorText(message = errorMessage)

                    if (onRetry != null) {
                        Spacer(modifier = Modifier.height(12.dp))

                        Button(label = retryText, onClick = onRetry)
                    }
                }
            }
        }
    }
}

@Composable
private fun ErrorText(
    message: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = message,
        color = KotlinConfTheme.colors.secondaryText,
        modifier = Modifier.widthIn(max = 220.dp),
        style = KotlinConfTheme.typography.text1.copy(
            textAlign = TextAlign.Center
        )
    )
}

// TODO: Implement the original loader
@Composable
fun Loading(
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    CircularProgressIndicator(
        modifier = modifier.size(width = 80.dp, height = 80.dp),
        color = KotlinConfTheme.colors.toggleOn
    )
}

@PreviewLightDark
@Composable
private fun NormalErrorWithLoadingPreview() {
    PreviewHelper {
        NormalErrorWithLoading(
            errorMessage = "Something went wrong",
            isLoading = true,
            onRetry = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun LoadingPreview() {
    PreviewHelper {
        Loading()
    }
}
