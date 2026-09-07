package io.kotelliada.kotlinconf.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper

@Composable
fun SearchInput(
    searchValue: String,
    onSearchValueChanged: (String) -> Unit,
    hint: String,
    focusRequester: FocusRequester,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        BasicTextField(
            value = searchValue,
            onValueChange = onSearchValueChanged,
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            interactionSource = interactionSource,
            singleLine = true,
            textStyle = KotlinConfTheme.typography.text1
                .copy(color = KotlinConfTheme.colors.primaryText),
            cursorBrush = SolidColor(KotlinConfTheme.colors.primaryText)
        )

        AnimatedVisibility(visible = searchValue.isEmpty()) {
            Text(
                text = hint,
                style = KotlinConfTheme.typography.text1,
                color = KotlinConfTheme.colors.placeholderText
            )
        }
    }
}

private class SearchInputParameterProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf("", "Kotlin")

    override fun getDisplayName(index: Int): String? = when (index) {
        0 -> "Empty"
        1 -> "With text"
        else -> super.getDisplayName(index)
    }
}

@PreviewLightDark
@Composable
private fun SearchInputPreview(
    @PreviewParameter(SearchInputParameterProvider::class) initSearchValue: String
) {
    PreviewHelper {
        var searchValue by remember { mutableStateOf(initSearchValue) }

        SearchInput(
            searchValue = searchValue,
            onSearchValueChanged = { searchValue = it },
            hint = "Search...",
            focusRequester = remember { FocusRequester() }
        )
    }
}
