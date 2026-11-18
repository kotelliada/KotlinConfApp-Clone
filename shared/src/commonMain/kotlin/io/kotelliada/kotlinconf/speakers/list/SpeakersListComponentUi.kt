package io.kotelliada.kotlinconf.speakers.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.kotelliada.kotlinconf.Speaker
import io.kotelliada.kotlinconf.SpeakerId
import io.kotelliada.kotlinconf.ui.components.HorizontalDivider
import io.kotelliada.kotlinconf.ui.components.MainHeaderContainer
import io.kotelliada.kotlinconf.ui.components.MainHeaderContainerState
import io.kotelliada.kotlinconf.ui.components.MainHeaderSearchBar
import io.kotelliada.kotlinconf.ui.components.MainHeaderTitleBar
import io.kotelliada.kotlinconf.ui.components.NormalErrorWithLoading
import io.kotelliada.kotlinconf.ui.components.SpeakerCard
import io.kotelliada.kotlinconf.ui.components.Text
import io.kotelliada.kotlinconf.ui.components.TopMenuButton
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper
import kotlinconfclone.shared.generated.resources.Res
import kotlinconfclone.shared.generated.resources.main_header_search_hint
import kotlinconfclone.shared.generated.resources.search_24
import kotlinconfclone.shared.generated.resources.speakers_number_of_results
import kotlinconfclone.shared.generated.resources.speakers_title
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SpeakersListComponentUi(
    component: SpeakersListComponent,
    modifier: Modifier = Modifier
) {
    val uiState by component.uiState.collectAsStateWithLifecycle()

    val gridState = rememberLazyGridState()

    Column(
        modifier = modifier.fillMaxSize()
            .background(color = KotlinConfTheme.colors.mainBackground)
    ) {
        MainHeaderContainer(
            state = uiState.searchState,
            titleContent = {
                MainHeaderTitleBar(
                    title = stringResource(Res.string.speakers_title),
                    endContent = {
                        TopMenuButton(
                            icon = Res.drawable.search_24,
                            onClick = { component.onSearchClicked() },
                            contentDescription = stringResource(Res.string.main_header_search_hint)
                        )
                    }
                )
            },
            searchContent = {
                MainHeaderSearchBar(
                    searchValue = uiState.searchText,
                    onSearchValueChanged = { component.onSearchValueChanged(it) },
                    onClearClicked = { component.onSearchValueChanged("") },
                    onCloseClicked = { component.onCloseSearchClicked() }
                )
            }
        )

        HorizontalDivider(thickness = 1.dp, color = KotlinConfTheme.colors.strokePale)

        if (uiState.isLoading || uiState.isError) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                NormalErrorWithLoading(
                    errorMessage = uiState.errorMessage,
                    isLoading = uiState.isLoading,
                    onRetry = { component.onRetryToLoadClicked() }
                )
            }
        } else {
            // TODO: Implement the following composables:
            // ScrollToTopHandle
            // HideKeyboardOnDragHandler

            LazyVerticalGrid(
                state = gridState,
                columns = GridCells.Adaptive(300.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (uiState.searchState == MainHeaderContainerState.Search) {
                    item(
                        span = { GridItemSpan(maxLineSpan) },
                        key = "number-of-speakers"
                    ) {
                        Text(
                            text = pluralStringResource(
                                Res.plurals.speakers_number_of_results,
                                uiState.speakers.size,
                                uiState.speakers.size
                            ),
                            color = KotlinConfTheme.colors.secondaryText,
                            style = KotlinConfTheme.typography.text2,
                            modifier = Modifier
                                .animateItem()
                                .semantics { liveRegion = LiveRegionMode.Polite }
                        )
                    }
                }

                items(
                    items = uiState.speakers,
                    key = { it.speaker.id.id }
                ) { speakerWithHighlights ->
                    val speaker = speakerWithHighlights.speaker
                    SpeakerCard(
                        name = speaker.name,
                        title = speaker.position,
                        photoUrl = speaker.photoUrl,
                        nameHighlights = speakerWithHighlights.nameHighlights,
                        titleHighlights = speakerWithHighlights.titleHighlights,
                        onClick = { component.onSpeakerClicked(speakerWithHighlights) },
                        modifier = Modifier
                            .animateItem()
                            .fillMaxWidth(),
                    )
                }
            }
        }
    }
}

private val previewSpeakers = listOf(
    SpeakerWithHighlights(Speaker(SpeakerId("1"), "Speaker 1", "Developer 1", "", "")),
    SpeakerWithHighlights(Speaker(SpeakerId("2"), "Speaker 2", "Developer 2", "", "")),
    SpeakerWithHighlights(Speaker(SpeakerId("3"), "Speaker 3", "Developer 3", "", "")),
    SpeakerWithHighlights(Speaker(SpeakerId("4"), "Speaker 4", "Developer 4", "", "")),
    SpeakerWithHighlights(Speaker(SpeakerId("5"), "Speaker 5", "Developer 5", "", "")),
    SpeakerWithHighlights(Speaker(SpeakerId("6"), "Speaker 6", "Developer 6", "", "")),
    SpeakerWithHighlights(Speaker(SpeakerId("7"), "Speaker 7", "Developer 7", "", "")),
    SpeakerWithHighlights(Speaker(SpeakerId("8"), "Speaker 8", "Developer 8", "", "")),
    SpeakerWithHighlights(Speaker(SpeakerId("9"), "Speaker 9", "Developer 9", "", "")),
    SpeakerWithHighlights(Speaker(SpeakerId("10"), "Speaker 10", "Developer 10", "", "")),
)

private class ComponentPreviewParameterProvider : PreviewParameterProvider<UiState> {
    override val values: Sequence<UiState> = sequenceOf(
        UiState(isLoading = true),
        UiState(isError = true, errorMessage = "Something went wrong"),
        UiState(speakers = previewSpeakers),
        UiState(searchState = MainHeaderContainerState.Search, speakers = previewSpeakers),
    )

    override fun getDisplayName(index: Int): String? {
        return when (index) {
            0 -> "Loading"
            1 -> "Error"
            2 -> "All Speakers"
            3 -> "Search"
            else -> super.getDisplayName(index)
        }
    }
}

@PreviewLightDark
@Composable
private fun SpeakersListComponentUiPreview(
    @PreviewParameter(ComponentPreviewParameterProvider::class) uiState: UiState
) {
    PreviewHelper(paddingEnabled = false) {
        SpeakersListComponentUi(component = PreviewSpeakersListComponent(uiState))
    }
}


@Preview(device = Devices.TABLET)
@Composable
private fun SpeakersListComponentUiPreviewOnTablet() {
    PreviewHelper(paddingEnabled = false) {
        val uiState = UiState(searchState = MainHeaderContainerState.Search, speakers = previewSpeakers)
        SpeakersListComponentUi(component = PreviewSpeakersListComponent(uiState))
    }
}
