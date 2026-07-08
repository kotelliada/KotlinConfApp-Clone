package io.kotelliada.kotlinconf

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewLightDark
import io.kotelliada.kotlinconf.root.PreviewRootComponent
import io.kotelliada.kotlinconf.root.RootComponent
import io.kotelliada.kotlinconf.root.RootComponentUi
import io.kotelliada.kotlinconf.ui.theme.KotlinConfTheme

@Composable
fun App(component: RootComponent) {
    KotlinConfTheme {
        RootComponentUi(component = component)
    }
}

@PreviewLightDark
@Composable
private fun AppPreview() {
    App(component = PreviewRootComponent())
}
