package io.kotelliada.kotlinconf.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.PreviewLightDark
import com.mikepenz.markdown.compose.Markdown
import com.mikepenz.markdown.model.DefaultMarkdownColors
import com.mikepenz.markdown.model.DefaultMarkdownTypography
import com.mikepenz.markdown.model.State
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper

@Composable
fun MarkdownView(
    state: State,
    modifier: Modifier = Modifier,
    onCustomUriClicked: (String) -> Unit = {}
) {
    MarkdownImpl(
        state = state,
        modifier = modifier,
        onCustomUriClicked = onCustomUriClicked
    )
}

@Composable
private fun MarkdownImpl(
    state: State,
    modifier: Modifier = Modifier,
    onCustomUriClicked: (String) -> Unit = {}
) {
    val regularUriHandler = LocalUriHandler.current
    val currentOnCustomUriClicked by rememberUpdatedState(onCustomUriClicked)
    val customUriHandler = remember(regularUriHandler) {
        object : UriHandler {
            override fun openUri(uri: String) {
                if (uri.startsWith("http://") || uri.startsWith("https://")) {
                    regularUriHandler.openUri(uri)
                } else {
                    currentOnCustomUriClicked(uri)
                }
            }
        }
    }

    CompositionLocalProvider(LocalUriHandler provides customUriHandler) {
        Markdown(
            state = state,
            colors = DefaultMarkdownColors(
                text = KotlinConfTheme.colors.longText,
                codeBackground = KotlinConfTheme.colors.mainBackground,
                inlineCodeBackground = KotlinConfTheme.colors.mainBackground,
                dividerColor = KotlinConfTheme.colors.strokePale,
                tableBackground = KotlinConfTheme.colors.mainBackground,
            ),
            typography = DefaultMarkdownTypography(
                text = KotlinConfTheme.typography.text1,
                code = KotlinConfTheme.typography.text1,
                h1 = KotlinConfTheme.typography.h1,
                h2 = KotlinConfTheme.typography.h2,
                h3 = KotlinConfTheme.typography.h3,
                h4 = KotlinConfTheme.typography.h4,
                h5 = KotlinConfTheme.typography.h4,
                h6 = KotlinConfTheme.typography.h4,
                quote = KotlinConfTheme.typography.text2,
                paragraph = KotlinConfTheme.typography.text1,
                ordered = KotlinConfTheme.typography.text1,
                bullet = KotlinConfTheme.typography.text1,
                list = KotlinConfTheme.typography.text1,
                inlineCode = KotlinConfTheme.typography.text1,
                table = KotlinConfTheme.typography.text1,
                textLink = TextLinkStyles(
                    style = SpanStyle(
                        color = KotlinConfTheme.colors.primaryText,
                        textDecoration = TextDecoration.Underline,
                    ),
                ),
            ),
            modifier = modifier
        )
    }
}

/*
@PreviewLightDark
@Composable
private fun MarkdownViewPreview() {
    PreviewHelper {
        MarkdownView(
            text = """
                # Hello Markdown

                This is a simple markdown example with:

                - Bullet points
                - **Bold text**
                - *Italic text*
            """.trimIndent()
        )
    }
}*/
