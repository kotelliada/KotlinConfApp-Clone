package io.kotelliada.kotlinconf.privacynotice

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.stack.Children
import io.kotelliada.kotlinconf.privacynotice.details.PrivacyNoticeDetailsComponentUi
import io.kotelliada.kotlinconf.privacynotice.main.PrivacyNoticeMainComponentUi
import io.kotelliada.kotlinconf.ui.components.Button
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
    Column(modifier = modifier.background(color = KotlinConfTheme.colors.mainBackground)) {
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

                is PrivacyNoticeComponent.Child.Details -> PrivacyNoticeDetailsComponentUi(child.component)
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
                onClick = { component.onRejectNoticeClicked() }
            )

            Button(
                label = stringResource(Res.string.privacy_notice_accept),
                onClick = { component.onAcceptNoticeClicked() },
                modifier = Modifier.weight(1F),
                primary = true
            )
        }
    }
}

@PreviewLightDark
@Composable
private fun PrivacyNoticeComponentUiPreview() {
    PreviewHelper(paddingEnabled = false) {
        PrivacyNoticeComponentUi(component = PreviewPrivacyNoticeComponent())
    }
}
