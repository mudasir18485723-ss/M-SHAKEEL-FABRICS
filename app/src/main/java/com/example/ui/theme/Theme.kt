package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LuxuryLightColorScheme = lightColorScheme(
    primary = NavyPrimary,
    onPrimary = SilkWhite,
    primaryContainer = NavyLight,
    onPrimaryContainer = GoldLight,
    secondary = GoldPrimary,
    onSecondary = NavyDark,
    secondaryContainer = GoldContainer,
    onSecondaryContainer = GoldDark,
    tertiary = GoldAccent,
    onTertiary = NavyDark,
    background = WarmCream,
    onBackground = TextPrimary,
    surface = SilkWhite,
    onSurface = TextPrimary,
    surfaceVariant = CreamSurfaceVariant,
    onSurfaceVariant = TextSecondary,
    outline = CreamBorder,
    outlineVariant = GoldLight,
    error = ErrorRed,
    onError = Color.White
)

private val LuxuryDarkColorScheme = darkColorScheme(
    primary = GoldAccent,
    onPrimary = NavyDark,
    primaryContainer = NavyLight,
    onPrimaryContainer = GoldLight,
    secondary = GoldPrimary,
    onSecondary = NavyDark,
    secondaryContainer = NavySurface,
    onSecondaryContainer = GoldLight,
    tertiary = GoldLight,
    onTertiary = NavyDark,
    background = NavyDark,
    onBackground = SilkWhite,
    surface = NavyPrimary,
    onSurface = SilkWhite,
    surfaceVariant = NavySurface,
    onSurfaceVariant = GoldLight,
    outline = NavyLight,
    outlineVariant = GoldDark,
    error = ErrorRed,
    onError = Color.White
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) LuxuryDarkColorScheme else LuxuryLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
