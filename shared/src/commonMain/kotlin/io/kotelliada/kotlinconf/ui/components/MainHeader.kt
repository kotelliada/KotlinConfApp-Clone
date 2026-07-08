package io.kotelliada.kotlinconf.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper
import kotlinconfclone.shared.generated.resources.Res
import kotlinconfclone.shared.generated.resources.arrow_left_24

@Composable
fun MainHeaderTitleBar(
    title: String,
    modifier: Modifier = Modifier,
    startContent: @Composable RowScope.() -> Unit = {},
    endContent: @Composable RowScope.() -> Unit = {},
) {
    Box(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(KotlinConfTheme.colors.mainBackground)
    ) {
        Row(modifier = Modifier.align(Alignment.CenterStart)) {
            startContent()
        }

        Text(
            text = title,
            modifier = Modifier.align(Alignment.Center).semantics { heading() },
            style = KotlinConfTheme.typography.h3,
            color = KotlinConfTheme.colors.primaryText
        )

        Row(modifier = Modifier.align(Alignment.CenterEnd)) {
            endContent()
        }
    }
}

@PreviewLightDark
@Composable
fun MainHeaderBackPreview() {
    PreviewHelper(paddingEnabled = false) {
        MainHeaderTitleBar(
            title = "Privacy notice",
            startContent = {
                TopMenuButton(
                    icon = Res.drawable.arrow_left_24,
                    contentDescription = "Back",
                    onClick = {}
                )
            }
        )
    }
}

@PreviewLightDark
@Composable
fun MainHeaderTitleOnlyPreview() {
    PreviewHelper(paddingEnabled = false) {
        MainHeaderTitleBar(title = "Info")
    }
}
