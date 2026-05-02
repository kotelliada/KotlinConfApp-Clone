package io.kotelliada.kotlinconf.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme

@Composable
internal fun buildHighlightedString(
    text: String,
    highlights: List<IntRange>,
): AnnotatedString {
    return if (highlights.isEmpty()) {
        AnnotatedString(text)
    } else {
        buildAnnotatedString {
            append(text)
            highlights.forEach { range ->
                if (!range.isEmpty() && range.first in text.indices && range.last in text.indices) {
                    addStyle(
                        style = SpanStyle(
                            color = KotlinConfTheme.colors.primaryTextWhiteFixed,
                            background = KotlinConfTheme.colors.primaryBackground
                        ),
                        start = range.first,
                        end = range.last + 1
                    )
                }
            }
        }
    }
}
