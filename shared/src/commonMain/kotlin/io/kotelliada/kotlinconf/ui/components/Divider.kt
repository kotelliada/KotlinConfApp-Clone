package io.kotelliada.kotlinconf.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper

@Composable
fun HorizontalDivider(
    thickness: Dp,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxWidth().height(thickness)) {
        drawLine(
            color = color,
            strokeWidth = thickness.toPx(),
            start = Offset(0F, thickness.toPx() / 2),
            end = Offset(size.width, thickness.toPx() / 2)
        )
    }
}

@Composable
fun VerticalDivider(
    thickness: Dp,
    color: Color,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier.fillMaxHeight().width(thickness)) {
        drawLine(
            color = color,
            strokeWidth = thickness.toPx(),
            start = Offset(thickness.toPx() / 2, 0F),
            end = Offset(thickness.toPx() / 2, size.height)
        )
    }
}

@PreviewLightDark
@Composable
fun HorizontalDividerPreview() {
    PreviewHelper(modifier = Modifier.width(100.dp)) {
        HorizontalDivider(thickness = 1.dp, color = KotlinConfTheme.colors.strokePale)
    }
}

@PreviewLightDark
@Composable
fun VerticalDividerPreview() {
    PreviewHelper(modifier = Modifier.height(100.dp)) {
        VerticalDivider(thickness = 1.dp, color = KotlinConfTheme.colors.strokePale)
    }
}
