package id.naulia.portfolio.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf

val LocalLuminaColors = staticCompositionLocalOf { LuminaLightColors }

@Composable
fun LuminaTheme(content: @Composable () -> Unit) {
    LaunchedEffect(Unit) {
        injectOutfitFont()
    }

    val colors = LuminaLightColors
    val colorScheme = lightColorScheme(
        primary = colors.primary,
        onPrimary = colors.onPrimary,
        primaryContainer = colors.primaryVariant,
        onPrimaryContainer = colors.onPrimary,
        secondary = colors.secondary,
        onSecondary = colors.onPrimary,
        secondaryContainer = colors.secondary,
        onSecondaryContainer = colors.onPrimary,
        tertiary = colors.tertiary,
        onTertiary = colors.onPrimary,
        background = colors.background,
        onBackground = colors.onBackground,
        surface = colors.surface,
        onSurface = colors.onSurface,
        surfaceVariant = colors.surfaceVariant,
        onSurfaceVariant = colors.onSurface,
        outline = colors.outline,
    )

    CompositionLocalProvider(LocalLuminaColors provides colors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = LuminaTypography,
            content = content,
        )
    }
}
