package io.kotelliada.kotlinconf.privacynotice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.arkivanov.decompose.extensions.compose.stack.Children
import io.kotelliada.kotlinconf.privacynotice.details.PrivacyNoticeDetailsComponentUi
import io.kotelliada.kotlinconf.privacynotice.main.PrivacyNoticeMainComponentUi
import io.kotelliada.kotlinconf.ui.components.Button
import io.kotelliada.kotlinconf.ui.components.DialogErrorLoading
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper
import kotlinconfclone.shared.generated.resources.Res
import kotlinconfclone.shared.generated.resources.privacy_notice_accept
import kotlinconfclone.shared.generated.resources.privacy_notice_reject
import org.jetbrains.compose.resources.stringResource

@Composable
fun PrivacyNoticeComponentUi(
    component: PrivacyNoticeComponent,
    modifier: Modifier = Modifier
) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = KotlinConfTheme.colors.mainBackground)
    ) {
        PrivacyNoticeComponentUiContent(
            component = component,
            uiState = uiState,
            modifier = Modifier
        )

        if (uiState.isDocumentLoading || uiState.isDocumentLoadingError) {
            DialogErrorLoading(
                errorMessage = "Loading document failed",
                loadingText = "Loading document...",
                isLoading = uiState.isDocumentLoading,
                onRetry = { component.onReloadDocumentClicked() },
            )
        }

        if (uiState.isAcceptingPrivacy || uiState.isAcceptingPrivacyError) {
            DialogErrorLoading(
                errorMessage = "Accepting privacy failed",
                loadingText = "Accepting privacy...",
                isLoading = uiState.isAcceptingPrivacy,
                onRetry = { component.onAcceptNoticeClicked() },
                onClose = { component.closeLoadingDialogClicked() }
            )
        }

        if (uiState.isRejectingPrivacy || uiState.isRejectingPrivacyError) {
            DialogErrorLoading(
                errorMessage = "Rejecting privacy failed",
                loadingText = "Rejecting privacy...",
                isLoading = uiState.isRejectingPrivacy,
                onRetry = { component.onRejectNoticeClicked() },
                onClose = { component.closeLoadingDialogClicked() }
            )
        }
    }
}

@Composable
private fun PrivacyNoticeComponentUiContent(
    component: PrivacyNoticeComponent,
    uiState: UiState,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Children(
            stack = component.childStack,
            modifier = Modifier.weight(1F)
        ) {
            when (val child = it.instance) {
                is PrivacyNoticeComponent.Child.Main -> PrivacyNoticeMainComponentUi(
                    component = child.component,
                    modifier = Modifier
                        .consumeWindowInsets(PaddingValues(start = 16.dp, end = 16.dp))
                        .padding(start = 16.dp, end = 16.dp, top = 16.dp)
                        .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Start + WindowInsetsSides.End + WindowInsetsSides.Top))
                )

                is PrivacyNoticeComponent.Child.Details -> PrivacyNoticeDetailsComponentUi(
                    component = child.component,
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Start + WindowInsetsSides.End + WindowInsetsSides.Top))
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .consumeWindowInsets(PaddingValues(horizontal = 12.dp, vertical = 16.dp))
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Start + WindowInsetsSides.End + WindowInsetsSides.Bottom))
        ) {
            Button(
                label = stringResource(Res.string.privacy_notice_reject),
                onClick = { component.onRejectNoticeClicked() },
                enabled = uiState.isDocumentReady && uiState.isRejectingPrivacyAvailable
            )

            Button(
                label = stringResource(Res.string.privacy_notice_accept),
                onClick = { component.onAcceptNoticeClicked() },
                modifier = Modifier.weight(1F),
                primary = true,
                enabled = uiState.isDocumentReady && uiState.isAcceptingPrivacyAvailable
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PrivacyNoticeComponentUiPreviewInLoadingState() {
    PreviewHelper(paddingEnabled = false) {
        PrivacyNoticeComponentUi(
            component = PreviewPrivacyNoticeComponent(UiState(isDocumentLoading = true))
        )
    }
}

@PreviewLightDark
@Composable
private fun PrivacyNoticeComponentUiPreviewInErrorState() {
    PreviewHelper(paddingEnabled = false) {
        PrivacyNoticeComponentUi(
            component = PreviewPrivacyNoticeComponent(UiState(isDocumentLoadingError = true))
        )
    }
}

@PreviewLightDark
@Composable
private fun PrivacyNoticeComponentUiPreviewInContentState() {
    PreviewHelper(paddingEnabled = false) {
        PrivacyNoticeComponentUi(
            component = PreviewPrivacyNoticeComponent(UiState(isDocumentReady = true))
        )
    }
}
