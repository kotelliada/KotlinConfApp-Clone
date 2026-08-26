package io.kotelliada.kotlinconf.ui.theme

import androidx.compose.ui.graphics.Color

data class Colors(
    val isDark: Boolean,

    val mainBackground: Color,
    val mainBackgroundInverted: Color,
    val primaryBackground: Color,
    val tileBackground: Color,
    val tooltipBackground: Color,

    val cardBackgroundPast: Color,

    val strokeFull: Color,
    val strokeAccent: Color,
    val strokeInputFocus: Color,
    val strokeHalf: Color,
    val strokePale: Color,

    val accentText: Color,
    val longText: Color,
    val noteText: Color,
    val placeholderText: Color,
    val primaryText: Color,
    val primaryTextInverted: Color,
    val primaryTextWhiteFixed: Color,
    val secondaryText: Color,

    val purpleText: Color,
    val magentaText: Color,
    val pinkText: Color,
    val orangeText: Color,

    val toggleOn: Color,
    val toggleOff: Color,

    val dialogBackground: Color,
)

val KotlinConfLightColors = Colors(
    isDark = false,

    mainBackground = UI.white100,
    mainBackgroundInverted = UI.black100,
    primaryBackground = Brand.magenta100,
    tileBackground = UI.black05,
    tooltipBackground = UI.grey900,

    cardBackgroundPast = UI.black05,

    strokeFull = UI.black100,
    strokeAccent = Brand.purple100,
    strokeInputFocus = UI.black80,
    strokeHalf = UI.black40,
    strokePale = UI.black15,

    accentText = Brand.magenta100,
    longText = UI.black70,
    noteText = UI.black40,
    placeholderText = UI.black30,
    primaryText = UI.black100,
    primaryTextInverted = UI.white100,
    primaryTextWhiteFixed = UI.white100,
    secondaryText = UI.black60,

    purpleText = Brand.purple100,
    magentaText = Brand.magenta100,
    pinkText = Brand.pink100,
    orangeText = Brand.orange,

    toggleOff = UI.grey400,
    toggleOn = Brand.purple100,

    dialogBackground = Color(0x80C2C2C2),
)

val KotlinConfDarkColors = Colors(
    isDark = true,

    mainBackground = UI.black100,
    mainBackgroundInverted = UI.white100,
    primaryBackground = Brand.magenta100,
    tileBackground = UI.white10,
    tooltipBackground = UI.grey100,

    cardBackgroundPast = UI.white05,

    strokeFull = UI.white100,
    strokeAccent = Brand.purpleTextDark,
    strokeInputFocus = UI.white80,
    strokeHalf = UI.white50,
    strokePale = UI.white20,

    accentText = Brand.magentaTextDark,
    longText = UI.white70,
    noteText = UI.white50,
    placeholderText = UI.white40,
    primaryText = UI.white100,
    primaryTextInverted = UI.black100,
    primaryTextWhiteFixed = UI.white100,
    secondaryText = UI.white70,

    purpleText = Brand.purpleTextDark,
    magentaText = Brand.magentaTextDark,
    pinkText = Brand.pinkTextDark,
    orangeText = Brand.orangeTextDark,

    toggleOff = UI.grey500,
    toggleOn = Brand.purpleTextDark,

    dialogBackground = Color(0x80686868),
)

val GoldenKodeeColors = KotlinConfDarkColors.copy(
    mainBackground = Color(0xFF3A0057),
)
