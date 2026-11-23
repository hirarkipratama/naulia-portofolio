package id.naulia.portfolio.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.staticCompositionLocalOf

val LocalLuminaColors = staticCompositionLocalOf { LuminaLightColors }

val LuminaShapes = Shapes(
    extraSmall = androidx.compose.foundation.shape.RoundedCornerShape(LuminaDimens.radius.sm),
    small = androidx.compose.foundation.shape.RoundedCornerShape(LuminaDimens.radius.base),
    medium = androidx.compose.foundation.shape.RoundedCornerShape(LuminaDimens.radius.md),
    large = androidx.compose.foundation.shape.RoundedCornerShape(LuminaDimens.radius.lg),
    extraLarge = androidx.compose.foundation.shape.RoundedCornerShape(LuminaDimens.radius.xl),
)

@Composable
fun LuminaTheme(content: @Composable () -> Unit) {
    LaunchedEffect(Unit) {
        injectLuminaFonts()
    }

    val colors = LuminaLightColors
    val colorScheme = lightColorScheme(
        primary = colors.primary,
        onPrimary = colors.onPrimary,
        primaryContainer = colors.primaryContainer,
        onPrimaryContainer = colors.onPrimaryContainer,
        inversePrimary = colors.inversePrimary,
        secondary = colors.secondary,
        onSecondary = colors.onSecondary,
        secondaryContainer = colors.secondaryContainer,
        onSecondaryContainer = colors.onSecondaryContainer,
        tertiary = colors.tertiary,
        onTertiary = colors.onTertiary,
        tertiaryContainer = colors.tertiaryContainer,
        onTertiaryContainer = colors.onTertiaryContainer,
        error = colors.error,
        onError = colors.onError,
        errorContainer = colors.errorContainer,
        onErrorContainer = colors.onErrorContainer,
        background = colors.background,
        onBackground = colors.onBackground,
        surface = colors.surface,
        onSurface = colors.onSurface,
        surfaceVariant = colors.surfaceVariant,
        onSurfaceVariant = colors.onSurfaceVariant,
        surfaceDim = colors.surfaceDim,
        surfaceBright = colors.surfaceBright,
        surfaceContainerLowest = colors.surfaceContainerLowest,
        surfaceContainerLow = colors.surfaceContainerLow,
        surfaceContainer = colors.surfaceContainer,
        surfaceContainerHigh = colors.surfaceContainerHigh,
        surfaceContainerHighest = colors.surfaceContainerHighest,
        inverseSurface = colors.inverseSurface,
        inverseOnSurface = colors.inverseOnSurface,
        outline = colors.outline,
        outlineVariant = colors.outlineVariant,
        surfaceTint = colors.surfaceTint,
    )

    CompositionLocalProvider(
        LocalLuminaColors provides colors,
        LocalLuminaTypeScale provides LuminaTypeScale,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = LuminaTypography,
            shapes = LuminaShapes,
            content = content,
        )
    }
}
