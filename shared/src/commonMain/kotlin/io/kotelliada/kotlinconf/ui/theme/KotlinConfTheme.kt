package io.kotelliada.kotlinconf.ui.theme

import androidx.compose.foundation.IndicationNodeFactory
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.node.DelegatableNode
import androidx.compose.ui.node.DrawModifierNode

private object NoIndication : IndicationNodeFactory {

    override fun create(interactionSource: InteractionSource): DelegatableNode {
        return object : DelegatableNode, DrawModifierNode, Modifier.Node() {
            override fun ContentDrawScope.draw() {
                drawContent()
            }
        }
    }

    override fun equals(other: Any?): Boolean = (other === NoIndication)

    override fun hashCode(): Int = 0
}

val LocalColors = staticCompositionLocalOf<Colors> {
    error("KotlinConfTheme must be part of the call hierarchy to provide colors")
}

val LocalShapes = staticCompositionLocalOf<Shapes> {
    error("KotlinConfTheme must be part of the call hierarchy to provide shapes")
}

val LocalTypography = staticCompositionLocalOf<Typography> {
    error("KotlinConfTheme must be part of the call hierarchy to provide typography")
}

object KotlinConfTheme {
    val colors: Colors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current

    val shapes: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalShapes.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current
}

@Composable
fun KotlinConfTheme(
    colors: Colors = if (isSystemInDarkTheme()) KotlinConfDarkColors else KotlinConfLightColors,
    rippleEnabled: Boolean = true,
    content: @Composable () -> Unit
) {
    val ripple = if (rippleEnabled) rememberRippleIndication() else NoIndication

    CompositionLocalProvider(
        LocalColors provides colors,
        LocalShapes provides KotlinConfShapes,
        LocalTypography provides KotlinConfTypography,
        LocalIndication provides ripple,
        content = content
    )
}
