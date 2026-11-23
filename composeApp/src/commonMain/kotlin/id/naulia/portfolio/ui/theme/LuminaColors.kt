package id.naulia.portfolio.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

/**
 * Curated Lumina color tokens.
 *
 * Source of truth: `reference/DESIGN.md` frontmatter (canonical per CR-AUDIT D2).
 * The prose "Palette Architecture" is treated as descriptive intent only.
 */
@Stable
data class LuminaColorScheme(
    val primary: Color = Color(0xFF9D422C),
    val onPrimary: Color = Color(0xFFFFFFFF),
    val primaryContainer: Color = Color(0xFFFF8E72),
    val onPrimaryContainer: Color = Color(0xFF762612),
    val inversePrimary: Color = Color(0xFFFFB4A2),
    val secondary: Color = Color(0xFF735C00),
    val onSecondary: Color = Color(0xFFFFFFFF),
    val secondaryContainer: Color = Color(0xFFFECF27),
    val onSecondaryContainer: Color = Color(0xFF705900),
    val tertiary: Color = Color(0xFF006C52),
    val onTertiary: Color = Color(0xFFFFFFFF),
    val tertiaryContainer: Color = Color(0xFF55C09C),
    val onTertiaryContainer: Color = Color(0xFF004B38),
    val error: Color = Color(0xFFBA1A1A),
    val onError: Color = Color(0xFFFFFFFF),
    val errorContainer: Color = Color(0xFFFFDAD6),
    val onErrorContainer: Color = Color(0xFF93000A),
    val background: Color = Color(0xFFFFF8F3),
    val onBackground: Color = Color(0xFF1D1B18),
    val surface: Color = Color(0xFFFFF8F3),
    val onSurface: Color = Color(0xFF1D1B18),
    val surfaceVariant: Color = Color(0xFFE7E1DC),
    val onSurfaceVariant: Color = Color(0xFF56423E),
    val surfaceDim: Color = Color(0xFFDFD9D4),
    val surfaceBright: Color = Color(0xFFFFF8F3),
    val surfaceContainerLowest: Color = Color(0xFFFFFFFF),
    val surfaceContainerLow: Color = Color(0xFFF9F2ED),
    val surfaceContainer: Color = Color(0xFFF3EDE7),
    val surfaceContainerHigh: Color = Color(0xFFEDE7E2),
    val surfaceContainerHighest: Color = Color(0xFFE7E1DC),
    val inverseSurface: Color = Color(0xFF32302D),
    val inverseOnSurface: Color = Color(0xFFF6F0EA),
    val outline: Color = Color(0xFF89726D),
    val outlineVariant: Color = Color(0xFFDCC0BA),
    val surfaceTint: Color = Color(0xFF9D422C),
    val primaryFixed: Color = Color(0xFFFFDAD2),
    val primaryFixedDim: Color = Color(0xFFFFB4A2),
    val onPrimaryFixed: Color = Color(0xFF3C0700),
    val onPrimaryFixedVariant: Color = Color(0xFF7E2C17),
    val secondaryFixed: Color = Color(0xFFFFE087),
    val secondaryFixedDim: Color = Color(0xFFEFC112),
    val onSecondaryFixed: Color = Color(0xFF231A00),
    val onSecondaryFixedVariant: Color = Color(0xFF574500),
    val tertiaryFixed: Color = Color(0xFF8CF6D0),
    val tertiaryFixedDim: Color = Color(0xFF70DAB4),
    val onTertiaryFixed: Color = Color(0xFF002117),
    val onTertiaryFixedVariant: Color = Color(0xFF00513D),
    val shadowWarm: Color = Color(0x0A1C1A17),
    val shadowCoral: Color = Color(0x1FFF8E72),
)

val LuminaLightColors = LuminaColorScheme()
