package com.valentimsts.threatzoo.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Orange80,
    secondary = Grey80,
    tertiary = Orange80,
    background = DarkGrey,
    surface = DarkGrey,
    onPrimary = DarkGrey,
    onSecondary = DarkGrey,
    onTertiary = DarkGrey,
    onBackground = LightGrey,
    onSurface = LightGrey,
    outline = DarkGrey
)

private val LightColorScheme = lightColorScheme(
    primary = Orange40,
    secondary = Grey40,
    tertiary = Orange40,
    background = LightGrey,
    surface = LightGrey,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = DarkGrey,
    onSurface = DarkGrey,
    outline = LightGrey,
)

@Composable
fun ThreatZooTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
