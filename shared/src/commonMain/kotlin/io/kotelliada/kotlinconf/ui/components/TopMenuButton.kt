package io.kotelliada.kotlinconf.ui.components

import androidx.compose.foundation.BasicTooltipBox
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper
import kotlinconfclone.shared.generated.resources.Res
import kotlinconfclone.shared.generated.resources.bookmark_24
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * A clickable top menu button with no selection state.
 */
@Composable
fun TopMenuButton(
    icon: DrawableResource,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    large: Boolean = false
) {
    TopMenuButtonImpl(
        icon = icon,
        contentDescription = contentDescription,
        modifier = modifier,
        interactionModifier = Modifier.clickable(onClick = onClick),
        backgroundColor = Color.Transparent,
        iconColor = KotlinConfTheme.colors.primaryText,
        large = large
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun TopMenuButtonImpl(
    icon: DrawableResource,
    contentDescription: String,
    interactionModifier: Modifier,
    backgroundColor: Color,
    iconColor: Color,
    large: Boolean,
    modifier: Modifier = Modifier,
) {
    BasicTooltipBox(
        positionProvider = rememberTooltipPositionProvider(),
        tooltip = { Tooltip(contentDescription) },
        state = rememberBasicTooltipState()
    ) {
        Icon(
            modifier = modifier
                .padding(if (large) 0.dp else 6.dp)
                .size(if (large) 40.dp else 36.dp)
                .clip(CircleShape)
                .then(interactionModifier)
                .background(backgroundColor)
                .padding(6.dp),
            painter = painterResource(icon),
            contentDescription = contentDescription,
            tint = iconColor
        )
    }
}

@PreviewLightDark
@Composable
fun TopMenuButtonSmallPreview() {
    PreviewHelper(paddingEnabled = false) {
        TopMenuButton(
            icon = Res.drawable.bookmark_24,
            contentDescription = "Bookmark",
            onClick = {},
            large = false
        )
    }
}

@PreviewLightDark
@Composable
fun TopMenuButtonLargePreview() {
    PreviewHelper(paddingEnabled = false) {
        TopMenuButton(
            icon = Res.drawable.bookmark_24,
            contentDescription = "Bookmark",
            onClick = {},
            large = true
        )
    }
}
