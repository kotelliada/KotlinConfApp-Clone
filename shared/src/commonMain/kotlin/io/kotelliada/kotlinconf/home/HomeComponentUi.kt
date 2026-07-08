package io.kotelliada.kotlinconf.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.kotelliada.kotlinconf.ui.theme.PreviewHelper

@Composable
fun HomeComponentUi(
    component: HomeComponent,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        Text("Home", modifier = Modifier.align(Alignment.Center))
    }
}

@PreviewLightDark
@Composable
private fun HomeComponentUiPreview() {
    PreviewHelper(paddingEnabled = false) {
        HomeComponentUi(component = PreviewHomeComponent())
    }
}
