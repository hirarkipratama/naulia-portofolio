package id.naulia.portfolio.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Display/headline family per reference (`Outfit`); the actual web font is loaded once by
 * `index.html`/`FontLoader`. Compose falls back to a sans-serif family until bundled font
 * resources are added, so these constants stay fallback-safe.
 */
val OutfitFontFamily: FontFamily = FontFamily.SansSerif

/**
 * Body/label family per reference (`Hanken Grotesk`); loaded by the web shell alongside Outfit.
 */
val HankenGroteskFontFamily: FontFamily = FontFamily.Default

object LuminaTypeScale {
    val displayHero = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 72.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 75.6f.sp,
        letterSpacing = (-2.16f).sp,
    )
    val displayHeroMobile = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 44.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 48.4f.sp,
        letterSpacing = (-0.88f).sp,
    )
    val headlineLg = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 48.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 55.2f.sp,
        letterSpacing = (-1.2f).sp,
    )
    val headlineLgMobile = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 38.4f.sp,
        letterSpacing = (-0.64f).sp,
    )
    val headlineMd = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 32.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 40.sp,
        letterSpacing = (-0.48f).sp,
    )
    val headlineSm = TextStyle(
        fontFamily = OutfitFontFamily,
        fontSize = 22.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 28.6f.sp,
        letterSpacing = (-0.22f).sp,
    )
    val bodyLg = TextStyle(
        fontFamily = HankenGroteskFontFamily,
        fontSize = 18.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 28.8f.sp,
        letterSpacing = (-0.09f).sp,
    )
    val bodyMd = TextStyle(
        fontFamily = HankenGroteskFontFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 24.8f.sp,
        letterSpacing = 0.sp,
    )
    val bodySm = TextStyle(
        fontFamily = HankenGroteskFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        lineHeight = 21.sp,
        letterSpacing = 0.07f.sp,
    )
    val labelMd = TextStyle(
        fontFamily = HankenGroteskFontFamily,
        fontSize = 13.sp,
        fontWeight = FontWeight.SemiBold,
        lineHeight = 15.6f.sp,
        letterSpacing = 0.52f.sp,
    )
    val labelSm = TextStyle(
        fontFamily = HankenGroteskFontFamily,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        lineHeight = 12.1f.sp,
        letterSpacing = 0.66f.sp,
    )
    val buttonLabel = TextStyle(
        fontFamily = HankenGroteskFontFamily,
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        lineHeight = 20.sp,
        letterSpacing = 0.1f.sp,
    )

    val heroHeadline = displayHero
    val quoteMark = displayHero.copy(
        fontSize = 96.sp,
        lineHeight = 96.sp,
        fontStyle = FontStyle.Italic,
    )
    val displayLarge = displayHero
    val headlineLarge = headlineLg
    val headlineMedium = headlineMd
    val titleLarge = headlineSm
    val titleMedium = bodyMd.copy(fontWeight = FontWeight.Medium)
    val bodyLarge = bodyLg
    val bodyMedium = bodyMd
    val labelLarge = labelMd
    val labelSmall = labelSm
    val tabLabel = labelSm.copy(fontSize = 10.sp, letterSpacing = 0.4f.sp)
    val tabIcon = bodyMd
}

val LocalLuminaTypeScale = staticCompositionLocalOf { LuminaTypeScale }

val LuminaTypography = Typography(
    displayLarge = LuminaTypeScale.displayHero,
    displayMedium = LuminaTypeScale.displayHeroMobile,
    displaySmall = LuminaTypeScale.headlineLg,
    headlineLarge = LuminaTypeScale.headlineLg,
    headlineMedium = LuminaTypeScale.headlineMd,
    headlineSmall = LuminaTypeScale.headlineSm,
    titleLarge = LuminaTypeScale.headlineSm,
    titleMedium = LuminaTypeScale.titleMedium,
    titleSmall = LuminaTypeScale.bodyLg.copy(fontWeight = FontWeight.Medium),
    bodyLarge = LuminaTypeScale.bodyLg,
    bodyMedium = LuminaTypeScale.bodyMd,
    bodySmall = LuminaTypeScale.bodySm,
    labelLarge = LuminaTypeScale.labelMd,
    labelMedium = LuminaTypeScale.labelSm,
    labelSmall = LuminaTypeScale.labelSm,
)
