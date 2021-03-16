package eu.baroncelli.dkmpsample.shared.viewmodel.styling

object Themes {
    val LightColorPalette = AppColorset(
        primary = PURPLE_700,
        primaryVariant = PURPLE_200,
        secondary = FUCHSIA,
        secondaryVariant = FUCHSIA,
        background = WHITE,
        surface = WHITE,
        error = RED_ERROR,
        onPrimary = WHITE,
        onSecondary = WHITE,
        onBackground = BLACK,
        onSurface = BLACK,
        onError = WHITE,
        focus = YELLOW_LIGHT,
    )


    val DarkColorPalette = AppColorset(
        primary = PURPLE_200,
        primaryVariant = PURPLE_700,
        secondary = TEAL_200,
        secondaryVariant = TEAL_200,
        background = DARK_BG,
        surface = DARK_BG,
        error = PINK_ERROR,
        onPrimary = BLACK,
        onSecondary = BLACK,
        onBackground = WHITE,
        onSurface = WHITE,
        onError = BLACK,
        focus = YELLOW_LIGHT,
    )
}