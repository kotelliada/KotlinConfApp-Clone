package io.kotelliada.kotlinconf.privacynotice.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper

@Composable
fun PrivacyNoticeDetailsComponentUi(
    component: PrivacyNoticeDetailsComponent,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text("PrivacyNoticeDetailsComponentUi", Modifier.align(Alignment.Center))
    }
}

@PreviewLightDark
@Composable
private fun PrivacyNoticeDetailsComponentUiPreview() {
    PreviewHelper(paddingEnabled = false) {
        PrivacyNoticeDetailsComponentUi(PreviewPrivacyNoticeDetailsComponent())
    }
}
