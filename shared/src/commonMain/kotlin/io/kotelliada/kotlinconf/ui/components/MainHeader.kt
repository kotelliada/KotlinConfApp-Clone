package io.kotelliada.kotlinconf.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper
import kotlinconfclone.shared.generated.resources.Res
import kotlinconfclone.shared.generated.resources.arrow_left_24
import kotlinconfclone.shared.generated.resources.close_24
import kotlinconfclone.shared.generated.resources.main_header_back
import kotlinconfclone.shared.generated.resources.main_header_search_clear
import kotlinconfclone.shared.generated.resources.main_header_search_hint
import org.jetbrains.compose.resources.stringResource

enum class MainHeaderContainerState {
    Title,
    Search
}

@Composable
fun MainHeaderContainer(
    state: MainHeaderContainerState,
    modifier: Modifier = Modifier,
    titleContent: @Composable () -> Unit = {},
    searchContent: @Composable () -> Unit = {},
) {
    AnimatedContent(
        targetState = state,
        modifier = modifier
            .height(49.dp)
            .fillMaxWidth()
            .background(KotlinConfTheme.colors.mainBackground),
    ) { targetState ->
        when (targetState) {
            Title -> titleContent()
            Search -> searchContent()
        }
    }
}

@Composable
fun MainHeaderSearchBar(
    searchValue: String,
    onSearchValueChanged: (String) -> Unit,
    onClearClicked: () -> Unit,
    onCloseClicked: () -> Unit,
    modifier: Modifier = Modifier,
    hasAdditionalInputs: Boolean = false
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(KotlinConfTheme.colors.mainBackground),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TopMenuButton(
            icon = Res.drawable.arrow_left_24,
            onClick = {
                onCloseClicked()
                onSearchValueChanged("")
            },
            contentDescription = stringResource(Res.string.main_header_back)
        )

        var focusRequested by rememberSaveable { mutableStateOf(false) }
        val focusRequester = remember { FocusRequester() }
        if (!focusRequested) {
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
                focusRequested = true
            }
        }

        SearchInput(
            searchValue = searchValue,
            onSearchValueChanged = onSearchValueChanged,
            hint = stringResource(Res.string.main_header_search_hint),
            focusRequester = focusRequester,
            modifier = Modifier.weight(1F).fillMaxHeight()
        )

        AnimatedVisibility(visible = searchValue.isNotEmpty() || hasAdditionalInputs) {
            TopMenuButton(
                icon = Res.drawable.close_24,
                onClick = {
                    onSearchValueChanged("")
                    onClearClicked()
                    focusRequester.requestFocus()
                },
                contentDescription = stringResource(Res.string.main_header_search_clear)
            )
        }
    }
}

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

private class SearchBarParameterProvider : PreviewParameterProvider<String> {
    override val values: Sequence<String> = sequenceOf("", "Kotlin")

    override fun getDisplayName(index: Int): String? = when (index) {
        0 -> "Empty"
        1 -> "With text"
        else -> super.getDisplayName(index)
    }
}

@PreviewLightDark
@Composable
fun MainHeaderSearchBarPreview(
    @PreviewParameter(SearchBarParameterProvider::class) initSearchValue: String
) {
    PreviewHelper(paddingEnabled = false) {
        var searchValue by remember { mutableStateOf(initSearchValue) }

        MainHeaderSearchBar(
            searchValue = searchValue,
            onSearchValueChanged = { searchValue = it },
            onClearClicked = { searchValue = "" },
            onCloseClicked = {}
        )
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
