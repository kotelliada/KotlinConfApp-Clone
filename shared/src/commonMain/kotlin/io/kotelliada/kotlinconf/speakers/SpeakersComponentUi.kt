package io.kotelliada.kotlinconf.speakers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import io.kotelliada.kotlinconf.speakers.list.SpeakersListComponentUi

@Composable
fun SpeakersComponentUi(
    component: SpeakersComponent,
    modifier: Modifier = Modifier
) {
    Children(
        stack = component.childStack,
        modifier = modifier
    ) {
        when (val child = it.instance) {
            is SpeakersList -> SpeakersListComponentUi(
                component = child.component,
                modifier = Modifier
            )

            is SpeakerDetails -> {}
        }
    }
}
