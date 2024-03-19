package eu.baroncelli.dkmpsample.styling

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun MyMaterialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {

    MaterialTheme(
        colorScheme = if (darkTheme) { DarkColors } else { LightColors },
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}