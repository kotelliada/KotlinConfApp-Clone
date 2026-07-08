package io.kotelliada.kotlinconf.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TooltipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper

@Composable
fun Tooltip(text: String) {
    Text(
        text = text,
        color = KotlinConfTheme.colors.primaryTextInverted,
        style = KotlinConfTheme.typography.text2,
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(KotlinConfTheme.colors.tooltipBackground)
            .padding(vertical = 4.dp, horizontal = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberTooltipPositionProvider(): PopupPositionProvider {
    // TODO: Implement custom logic
    return TooltipDefaults.rememberPlainTooltipPositionProvider()
}

@PreviewLightDark
@Composable
fun TooltipPreview() {
    PreviewHelper {
        Tooltip(text = "Tooltip")
    }
}
