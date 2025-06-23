package com.example.anotherregisterapp.ui.theme

import android.app.Activity
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
    primary = Bluee,
    onPrimary = OnBlueDark,
    primaryContainer = LighterBlue,
    onPrimaryContainer = Color.Black,

    secondary = DarkBluee,
    onSecondary = Color.Black,
    secondaryContainer = Bluee,
    onSecondaryContainer = Color.Black,

    background = BackgroundDark,
    onBackground = Color.White,

    surface = SurfaceDark,
    onSurface = OnSurfaceDark
)

private val LightColorScheme = lightColorScheme(
    primary = Blue,
    onPrimary = OnBlue,
    primaryContainer = LightBlue,
    onPrimaryContainer = Color.Black,

    secondary = DarkBlue,
    onSecondary = Color.White,
    secondaryContainer = Blue,
    onSecondaryContainer = Color.Black,

    background = Background,
    onBackground = Color.Black,

    surface = Surface,
    onSurface = OnSurface
)

@Composable
fun AnotherRegisterAppTheme(
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
        content = content
    )
}