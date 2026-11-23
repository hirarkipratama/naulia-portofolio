package id.naulia.portfolio.ui.screens

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import id.naulia.portfolio.image.rememberImageBitmap
import id.naulia.portfolio.navigation.NavSection
import id.naulia.portfolio.navigation.Navigator
import id.naulia.portfolio.platform.prefersReducedMotion
import id.naulia.portfolio.ui.components.CredentialChip
import id.naulia.portfolio.ui.components.LuminaPrimaryButton
import id.naulia.portfolio.ui.components.LuminaSecondaryButton
import id.naulia.portfolio.ui.icons.LuminaIcons
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.rememberIsMobile

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HeroScreen(
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val isMobile = rememberIsMobile()
    val reducedMotion = remember { prefersReducedMotion() }
    val transition = rememberInfiniteTransition()
    val animatedAlpha by transition.animateFloat(
        initialValue = HeroStrings.MIN_INDICATOR_ALPHA,
        targetValue = HeroStrings.MAX_INDICATOR_ALPHA,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = HeroStrings.INDICATOR_DURATION_MS,
                easing = EaseInOutSine,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    val indicatorAlpha = if (reducedMotion) HeroStrings.MAX_INDICATOR_ALPHA else animatedAlpha
    val backgroundBrush = remember(colors) {
        Brush.linearGradient(listOf(colors.background, colors.surface))
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = LuminaDimens.size.heroMinHeight)
            .background(backgroundBrush),
    ) {
        if (isMobile) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = LuminaDimens.margin.mobile,
                        vertical = LuminaDimens.spacing.xl,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HeroPortrait(modifier = Modifier.padding(bottom = LuminaDimens.spacing.lg))
                HeroCopy(navigator = navigator, modifier = Modifier.fillMaxWidth())
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = LuminaDimens.margin.desktop,
                        vertical = LuminaDimens.spacing.xl,
                    ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HeroCopy(navigator = navigator, modifier = Modifier.weight(HERO_TEXT_WEIGHT))
                HeroPortrait(modifier = Modifier.weight(HERO_PORTRAIT_WEIGHT))
            }
        }

        Icon(
            imageVector = LuminaIcons.ChevronDown,
            contentDescription = HeroStrings.SCROLL_INDICATOR_DESCRIPTION,
            tint = colors.primary,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = LuminaDimens.spacing.md)
                .size(LuminaDimens.spacing.lg)
                .graphicsLayer { alpha = indicatorAlpha },
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HeroCopy(
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val isMobile = rememberIsMobile()
    Column(modifier = modifier) {
        FlowRow(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.sm)) {
            CredentialChip(
                label = HeroStrings.CREDENTIAL_PARTNER,
                icon = HeroStrings.CREDENTIAL_PARTNER_ICON,
                iconTint = colors.secondary,
            )
            CredentialChip(
                label = HeroStrings.CREDENTIAL_TITLES,
                icon = HeroStrings.CREDENTIAL_TITLES_ICON,
                iconTint = colors.primary,
            )
            CredentialChip(
                label = HeroStrings.CREDENTIAL_VO,
                icon = HeroStrings.CREDENTIAL_VO_ICON,
                iconTint = colors.tertiary,
            )
        }
        Spacer(Modifier.height(LuminaDimens.spacing.lg))
        Text(
            text = HeroStrings.HEADLINE,
            style = if (isMobile) type.displayHeroMobile else type.displayHero,
            color = colors.onSurface,
        )
        Text(
            text = HeroStrings.SUBHEADLINE,
            style = type.headlineSm,
            color = colors.primary,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.md))
        Text(
            text = HeroStrings.DESCRIPTION,
            style = type.bodyLg,
            color = colors.onSurfaceVariant,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.lg))
        Row(verticalAlignment = Alignment.CenterVertically) {
            LuminaPrimaryButton(
                label = HeroStrings.PRIMARY_CTA,
                onClick = { navigator.navigateTo(NavSection.Contact) },
                icon = LuminaIcons.Chat,
            )
            Spacer(Modifier.width(LuminaDimens.spacing.sm))
            LuminaSecondaryButton(
                label = HeroStrings.SECONDARY_CTA,
                onClick = { navigator.navigateTo(NavSection.Audio) },
                icon = LuminaIcons.Play,
            )
        }
    }
}

@Composable
private fun HeroPortrait(
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val portraitBrush = remember(colors) {
        Brush.linearGradient(listOf(colors.surfaceContainerHigh, colors.primaryContainer))
    }
    val portraitBitmap = rememberImageBitmap(HeroStrings.PORTRAIT_ASSET)
    val shape = RoundedCornerShape(LuminaDimens.cornerRadius.card)

    Box(
        modifier = modifier
            .size(
                width = LuminaDimens.size.portraitWidth,
                height = LuminaDimens.size.portraitHeight,
            )
            .shadow(
                elevation = LuminaDimens.elevation.level3,
                shape = shape,
                ambientColor = colors.shadowWarm,
                spotColor = colors.shadowWarm,
            )
            .clip(shape)
            .background(portraitBrush, shape)
            .semantics { contentDescription = HeroStrings.PORTRAIT_DESCRIPTION },
        contentAlignment = Alignment.Center,
    ) {
        if (portraitBitmap != null) {
            Image(
                bitmap = portraitBitmap,
                contentDescription = HeroStrings.PORTRAIT_DESCRIPTION,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
            )
        } else {
            Text(
                text = HeroStrings.PORTRAIT_PLACEHOLDER,
                style = LocalLuminaTypeScale.current.headlineMedium,
                color = colors.onPrimaryContainer,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(LuminaDimens.spacing.lg),
            )
        }
    }
}

private const val HERO_TEXT_WEIGHT = 1.2f
private const val HERO_PORTRAIT_WEIGHT = 0.8f

object HeroStrings {
    const val CREDENTIAL_PARTNER = "Mitra Resmi Gramedia"
    val CREDENTIAL_PARTNER_ICON: ImageVector = LuminaIcons.Star
    const val CREDENTIAL_TITLES = "15+ Judul Buku Diedit"
    val CREDENTIAL_TITLES_ICON: ImageVector = LuminaIcons.Pen
    const val CREDENTIAL_VO = "Suara Ekspresif VO"
    val CREDENTIAL_VO_ICON: ImageVector = LuminaIcons.Mic
    const val HEADLINE = "Naulia Zahra"
    const val SUBHEADLINE = "Editor Buku · MC Panggung · Seniman Suara"
    const val DESCRIPTION =
        "Multidisiplin kreatif yang menghadirkan buku anak dan STEM yang mudah dicintai, " +
            "memandu panggung dengan energi hangat, dan menghidupkan karakter lewat suara."
    const val PRIMARY_CTA = "Undang untuk Acara / VO"
    const val SECONDARY_CTA = "Jelajahi Demo Audio"
    const val SCROLL_INDICATOR_DESCRIPTION = "Gulir ke bawah untuk menjelajahi portofolio"
    const val PORTRAIT_ASSET = "images/hero/naulia_hero.webp"
    const val PORTRAIT_PLACEHOLDER = "Naulia Zahra"
    const val PORTRAIT_DESCRIPTION = "Foto potret Naulia Zahra"
    const val MIN_INDICATOR_ALPHA = 0.3f
    const val MAX_INDICATOR_ALPHA = 1f
    const val INDICATOR_DURATION_MS = 900
}
