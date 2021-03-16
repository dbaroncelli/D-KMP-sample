package eu.baroncelli.dkmpsample.android.styling

import androidx.compose.runtime.Composable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.graphics.Color
import eu.baroncelli.dkmpsample.shared.viewmodel.KMPViewModel
import eu.baroncelli.dkmpsample.shared.viewmodel.styling.AppColorset


@Composable
fun DKmpSampleTheme(KMPViewModel: KMPViewModel, darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {

    val themeColors : Colors = if (darkTheme) {
        getThemeColors(KMPViewModel.themes.DarkColorPalette, false)
    } else {
        getThemeColors(KMPViewModel.themes.LightColorPalette, true)
    }

    MaterialTheme(
        colors = themeColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}


fun String.toColor() : Color {
    return Color(("FF"+this).toLong(16))
}


fun getThemeColors(colorset: AppColorset, lightColors : Boolean) : Colors {
    return Colors(
        primary = colorset.primary.toColor(),
        primaryVariant = colorset.primaryVariant.toColor(),
        secondary = colorset.secondary.toColor(),
        secondaryVariant = colorset.secondaryVariant.toColor(),
        background = colorset.background.toColor(),
        surface = colorset.surface.toColor(),
        error = colorset.error.toColor(),
        onPrimary = colorset.onPrimary.toColor(),
        onSecondary = colorset.onSecondary.toColor(),
        onBackground = colorset.onBackground.toColor(),
        onSurface = colorset.onSurface.toColor(),
        onError = colorset.onError.toColor(),
        isLight = lightColors
    )
}