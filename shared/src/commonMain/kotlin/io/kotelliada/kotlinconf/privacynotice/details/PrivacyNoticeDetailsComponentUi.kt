package io.kotelliada.kotlinconf.privacynotice.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mikepenz.markdown.model.State
import io.kotelliada.kotlinconf.ui.components.HorizontalDivider
import io.kotelliada.kotlinconf.ui.components.MainHeaderTitleBar
import io.kotelliada.kotlinconf.ui.components.MarkdownView
import io.kotelliada.kotlinconf.ui.components.TopMenuButton
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper
import kotlinconfclone.shared.generated.resources.Res
import kotlinconfclone.shared.generated.resources.arrow_left_24
import kotlinconfclone.shared.generated.resources.privacy_notice_back
import kotlinconfclone.shared.generated.resources.privacy_notice_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun PrivacyNoticeDetailsComponentUi(
    component: PrivacyNoticeDetailsComponent,
    modifier: Modifier = Modifier
) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        MainHeaderTitleBar(
            title = stringResource(Res.string.privacy_notice_title),
            startContent = {
                TopMenuButton(
                    icon = Res.drawable.arrow_left_24,
                    contentDescription = stringResource(Res.string.privacy_notice_back),
                    onClick = { component.onBackButtonClicked() }
                )
            }
        )
        HorizontalDivider(
            thickness = 1.dp,
            color = KotlinConfTheme.colors.strokePale
        )

        val scrollState = rememberScrollState()
        // TODO: ScrollToTopHandler(scrollState)

        MarkdownView(
            state = uiState,
            modifier = Modifier
                .weight(1F)
                .padding(horizontal = 12.dp)
                .verticalScroll(scrollState),
            onCustomUriClicked = {
                // TODO: Implement
            }
        )

        HorizontalDivider(
            thickness = 1.dp,
            color = KotlinConfTheme.colors.strokePale
        )
    }
}

@PreviewLightDark
@Composable
private fun PrivacyNoticeDetailsComponentUiPreview() {
    PreviewHelper(paddingEnabled = false) {
        PrivacyNoticeDetailsComponentUi(PreviewPrivacyNoticeDetailsComponent(State.Loading()))
    }
}
