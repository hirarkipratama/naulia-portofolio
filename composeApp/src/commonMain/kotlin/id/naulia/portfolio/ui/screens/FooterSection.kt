package id.naulia.portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import id.naulia.portfolio.navigation.NavSection
import id.naulia.portfolio.navigation.Navigator
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.rememberIsMobile

@Composable
fun FooterSection(
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val typeScale = LocalLuminaTypeScale.current
    val isMobile = rememberIsMobile()

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = colors.onBackground,
        contentColor = colors.background,
        shape = RectangleShape,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = LuminaDimens.spacing.xl,
                    vertical = LuminaDimens.spacing.xl,
                ),
        ) {
            if (isMobile) {
                FooterBrand(modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(LuminaDimens.spacing.lg))
                FooterNavigation(navigator = navigator, modifier = Modifier.fillMaxWidth())
                Spacer(Modifier.height(LuminaDimens.spacing.lg))
                FooterContact(modifier = Modifier.fillMaxWidth())
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                ) {
                    FooterBrand(modifier = Modifier.weight(FOOTER_COLUMN_WEIGHT))
                    FooterNavigation(
                        navigator = navigator,
                        modifier = Modifier.weight(FOOTER_COLUMN_WEIGHT),
                    )
                    FooterContact(modifier = Modifier.weight(FOOTER_COLUMN_WEIGHT))
                }
            }

            Spacer(Modifier.height(LuminaDimens.spacing.lg))
            FooterDivider()
            Spacer(Modifier.height(LuminaDimens.spacing.md))
            Text(
                text = FooterStrings.COPYRIGHT,
                style = typeScale.labelSmall,
                color = colors.background.copy(alpha = FOOTER_MUTED_ALPHA),
            )
        }
    }
}

@Composable
private fun FooterDivider() {
    val colors = LocalLuminaColors.current
    Spacer(
        Modifier
            .fillMaxWidth()
            .height(LuminaDimens.size.thinBorder)
            .background(colors.background.copy(alpha = FOOTER_DIVIDER_ALPHA)),
    )
}

@Composable
private fun FooterBrand(
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val typeScale = LocalLuminaTypeScale.current
    Column(modifier = modifier) {
        Text(
            text = FooterStrings.WORDMARK,
            style = typeScale.titleLarge,
            color = colors.primary,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        Text(
            text = FooterStrings.TAGLINE,
            style = typeScale.labelLarge,
            color = colors.background,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.xs))
        Text(
            text = FooterStrings.LOCATION,
            style = typeScale.bodyMedium,
            color = colors.background.copy(alpha = FOOTER_MUTED_ALPHA),
        )
    }
}

@Composable
private fun FooterNavigation(
    navigator: Navigator,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val typeScale = LocalLuminaTypeScale.current
    Column(modifier = modifier) {
        Text(
            text = FooterStrings.NAVIGATION_HEADING,
            style = typeScale.labelLarge,
            color = colors.primary,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        NavSection.all.forEach { section ->
            Text(
                text = section.labelBahasa,
                style = typeScale.bodyMedium,
                color = colors.background,
                modifier = Modifier
                    .clip(RoundedCornerShape(LuminaDimens.cornerRadius.button))
                    .clickable { navigator.navigateTo(section) }
                    .semantics {
                        role = Role.Button
                        contentDescription = section.labelBahasa
                    }
                    .padding(
                        horizontal = LuminaDimens.spacing.sm,
                        vertical = LuminaDimens.spacing.xs,
                    ),
            )
        }
    }
}

@Composable
private fun FooterContact(
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val typeScale = LocalLuminaTypeScale.current
    Column(modifier = modifier) {
        Text(
            text = FooterStrings.CONTACT_HEADING,
            style = typeScale.labelLarge,
            color = colors.primary,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        Text(
            text = ContactStrings.WHATSAPP_NUMBER,
            style = typeScale.bodyMedium,
            color = colors.background,
        )
        Text(
            text = ContactStrings.EMAIL_ADDRESS,
            style = typeScale.bodyMedium,
            color = colors.background,
        )
        Text(
            text = FooterStrings.INSTAGRAM_HANDLE,
            style = typeScale.bodyMedium,
            color = colors.background.copy(alpha = FOOTER_MUTED_ALPHA),
        )
    }
}

private const val FOOTER_COLUMN_WEIGHT = 1f
private const val FOOTER_MUTED_ALPHA = 0.75f
private const val FOOTER_DIVIDER_ALPHA = 0.3f

object FooterStrings {
    const val WORDMARK = "Naulia Zahra"
    const val TAGLINE = "Editor · MC · Suara"
    const val LOCATION = "Jakarta & Daring 🌐"
    const val NAVIGATION_HEADING = "Navigasi"
    const val CONTACT_HEADING = "Hubungi"
    const val INSTAGRAM_HANDLE = "@nauliazahra"
    const val COPYRIGHT = "© 2024–2025 Naulia Zahra. All rights reserved."
}
