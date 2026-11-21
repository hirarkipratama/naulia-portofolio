package id.naulia.portfolio.ui.theme

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color

@Stable
data class LuminaColorScheme(
    val background: Color = Color(0xFFFFF8F3),
    val surface: Color = Color(0xFFF9F2ED),
    val surfaceVariant: Color = Color(0xFFFFFFFF),
    val primary: Color = Color(0xFFFF8E72),
    val primaryVariant: Color = Color(0xFFF59E0B),
    val secondary: Color = Color(0xFF6EAB9E),
    val tertiary: Color = Color(0xFFA78BCA),
    val onBackground: Color = Color(0xFF2D1F14),
    val onSurface: Color = Color(0xFF4A3728),
    val onPrimary: Color = Color(0xFFFFFFFF),
    val outline: Color = Color(0xFFE8DDD6),
)

val LuminaLightColors = LuminaColorScheme()
