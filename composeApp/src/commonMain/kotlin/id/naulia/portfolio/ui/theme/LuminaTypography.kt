package id.naulia.portfolio.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val OutfitFontFamily: FontFamily = FontFamily.SansSerif

object LuminaTypeScale {
    val displayLarge = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 40.sp,
        fontWeight = FontWeight.Bold,
    )
    val headlineLarge = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
    )
    val headlineMedium = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 24.sp,
        fontWeight = FontWeight.SemiBold,
    )
    val titleLarge = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 20.sp,
        fontWeight = FontWeight.Medium,
    )
    val titleMedium = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Medium,
    )
    val bodyLarge = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
    )
    val bodyMedium = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
    )
    val labelLarge = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
    )
    val labelSmall = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
    )
}

val LuminaTypography = Typography(
    displayLarge = LuminaTypeScale.displayLarge,
    headlineLarge = LuminaTypeScale.headlineLarge,
    headlineMedium = LuminaTypeScale.headlineMedium,
    titleLarge = LuminaTypeScale.titleLarge,
    titleMedium = LuminaTypeScale.titleMedium,
    bodyLarge = LuminaTypeScale.bodyLarge,
    bodyMedium = LuminaTypeScale.bodyMedium,
    labelLarge = LuminaTypeScale.labelLarge,
    labelSmall = LuminaTypeScale.labelSmall,
)
