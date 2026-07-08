package io.kotelliada.kotlinconf.privacynotice.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.kotelliada.kotlinconf.ui.components.Action
import io.kotelliada.kotlinconf.ui.components.ActionSize
import io.kotelliada.kotlinconf.ui.components.Text
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper
import kotlinconfclone.shared.generated.resources.Res
import kotlinconfclone.shared.generated.resources.arrow_right_24
import kotlinconfclone.shared.generated.resources.kodee_privacy
import kotlinconfclone.shared.generated.resources.privacy_notice_description
import kotlinconfclone.shared.generated.resources.privacy_notice_read_action
import kotlinconfclone.shared.generated.resources.privacy_notice_title
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource

@Composable
fun PrivacyNoticeMainComponentUi(
    component: PrivacyNoticeMainComponent,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Image(
            imageVector = vectorResource(Res.drawable.kodee_privacy),
            contentDescription = null,
            modifier = Modifier
                .size(160.dp)
                .align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(Res.string.privacy_notice_title),
            style = KotlinConfTheme.typography.h1
        )

        Text(
            text = stringResource(Res.string.privacy_notice_description),
            color = KotlinConfTheme.colors.longText
        )

        Action(
            label = stringResource(Res.string.privacy_notice_read_action),
            icon = Res.drawable.arrow_right_24,
            size = ActionSize.Large,
            enabled = true,
            onClick = {}
        )
    }
}

@PreviewLightDark
@Composable
private fun PrivacyNoticeMainComponentUiPreview() {
    PreviewHelper(paddingEnabled = false) {
        PrivacyNoticeMainComponentUi(PreviewPrivacyNoticeMainComponent())
    }
}
