package id.naulia.portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import id.naulia.portfolio.config.ContactConfig
import id.naulia.portfolio.navigation.NavSection
import id.naulia.portfolio.navigation.Navigator
import id.naulia.portfolio.platform.openEmail
import id.naulia.portfolio.ui.components.LuminaIconButton
import id.naulia.portfolio.ui.icons.LuminaIcons
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.rememberIsMobile
import id.naulia.portfolio.ui.theme.focusRing

@Composable
fun FooterSection(
    navigator: Navigator,
    contactConfig: ContactConfig,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val typeScale = LocalLuminaTypeScale.current
    val isMobile = rememberIsMobile()
    val horizontalPadding = if (isMobile) {
        LuminaDimens.margin.mobile
    } else {
        LuminaDimens.margin.desktop
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = colors.surfaceContainerLowest,
        contentColor = colors.onSurface,
        shadowElevation = LuminaDimens.elevation.level1,
        shape = RectangleShape,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = horizontalPadding,
                    vertical = LuminaDimens.spacing.xl,
                ),
        ) {
            if (isMobile) {
                FooterBrand(navigator = navigator, contactConfig = contactConfig)
                Spacer(Modifier.height(LuminaDimens.spacing.lg))
                FooterNavigation(navigator = navigator)
                Spacer(Modifier.height(LuminaDimens.spacing.lg))
                FooterContact(contactConfig = contactConfig)
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.lg),
                ) {
                    FooterBrand(
                        navigator = navigator,
                        contactConfig = contactConfig,
                        modifier = Modifier.weight(FOOTER_BRAND_WEIGHT),
                    )
                    FooterNavigation(
                        navigator = navigator,
                        modifier = Modifier.weight(FOOTER_NAV_WEIGHT),
                    )
                    FooterContact(
                        contactConfig = contactConfig,
                        modifier = Modifier.weight(FOOTER_CONTACT_WEIGHT),
                    )
                }
            }

            Spacer(Modifier.height(LuminaDimens.spacing.lg))
            FooterDivider()
            Spacer(Modifier.height(LuminaDimens.spacing.md))
            FooterBottomRow(navigator = navigator, isMobile = isMobile)
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
            .background(colors.outlineVariant),
    )
}

@Composable
private fun FooterBrand(
    navigator: Navigator,
    contactConfig: ContactConfig,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val typeScale = LocalLuminaTypeScale.current
    Column(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = FooterStrings.WORDMARK,
                style = typeScale.headlineSm,
                color = colors.onSurface,
            )
            Spacer(Modifier.width(LuminaDimens.spacing.xs))
            Surface(
                shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
                color = colors.secondaryFixed,
            ) {
                Text(
                    text = FooterStrings.STUDIO_BADGE,
                    style = typeScale.labelSmall,
                    color = colors.onSecondaryFixed,
                    modifier = Modifier.padding(
                        horizontal = LuminaDimens.spacing.xs,
                        vertical = LuminaDimens.spacing.xs / TWO,
                    ),
                )
            }
        }
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        Text(
            text = FooterStrings.BRAND_DESCRIPTION,
            style = typeScale.bodyMd,
            color = colors.onSurfaceVariant,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        Row(horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xs)) {
            LuminaIconButton(
                icon = LuminaIcons.Mail,
                contentDescription = FooterStrings.EMAIL_ICON_DESCRIPTION,
                onClick = { openEmail(contactConfig.emailAddress) },
                containerColor = colors.surfaceContainerLow,
            )
            LuminaIconButton(
                icon = LuminaIcons.Mic,
                contentDescription = FooterStrings.AUDIO_ICON_DESCRIPTION,
                onClick = { navigator.navigateTo(NavSection.Audio) },
                containerColor = colors.surfaceContainerLow,
            )
            LuminaIconButton(
                icon = LuminaIcons.Stage,
                contentDescription = FooterStrings.STAGE_ICON_DESCRIPTION,
                onClick = { navigator.navigateTo(NavSection.Stage) },
                containerColor = colors.surfaceContainerLow,
            )
        }
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
            color = colors.onSurface,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        NavSection.primaryNav
            .filter { section -> section != NavSection.Contact }
            .forEach { section ->
                Text(
                    text = section.navLabelBahasa,
                    style = typeScale.bodyMedium,
                    color = colors.onSurfaceVariant,
                    modifier = Modifier
                        .clickable { navigator.navigateTo(section) }
                        .focusRing(RoundedCornerShape(LuminaDimens.cornerRadius.button))
                        .semantics {
                            role = Role.Button
                            contentDescription = section.navLabelBahasa
                        }
                        .padding(
                            horizontal = LuminaDimens.spacing.xs,
                            vertical = LuminaDimens.spacing.xs,
                        ),
                )
            }
    }
}

@Composable
private fun FooterContact(
    contactConfig: ContactConfig,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val typeScale = LocalLuminaTypeScale.current
    Column(modifier = modifier) {
        Text(
            text = FooterStrings.CONTACT_HEADING,
            style = typeScale.labelLarge,
            color = colors.onSurface,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        Text(
            text = FooterStrings.AGENCY_DESCRIPTION,
            style = typeScale.bodyMedium,
            color = colors.onSurfaceVariant,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        Surface(
            shape = RoundedCornerShape(LuminaDimens.cornerRadius.media),
            color = colors.surfaceContainerLow,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(modifier = Modifier.padding(LuminaDimens.spacing.md)) {
                Text(
                    text = FooterStrings.AGENCY_CONTACT_LABEL,
                    style = typeScale.labelSmall,
                    color = colors.onSurfaceVariant,
                )
                Spacer(Modifier.height(LuminaDimens.spacing.xs))
                Text(
                    text = contactConfig.emailAddress,
                    style = typeScale.bodyMedium,
                    color = colors.primary,
                    modifier = Modifier
                        .clickable { openEmail(contactConfig.emailAddress) }
                        .focusRing(RoundedCornerShape(LuminaDimens.cornerRadius.button))
                        .semantics {
                            role = Role.Button
                            contentDescription = contactConfig.emailAddress
                        },
                )
            }
        }
    }
}

@Composable
private fun FooterBottomRow(
    navigator: Navigator,
    isMobile: Boolean,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val typeScale = LocalLuminaTypeScale.current
    val content: @Composable () -> Unit = {
        Text(
            text = FooterStrings.COPYRIGHT,
            style = typeScale.bodySm,
            color = colors.onSurfaceVariant,
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = FooterStrings.LOCATION,
                style = typeScale.labelSmall,
                color = colors.onSurfaceVariant,
            )
            Text(
                text = FooterStrings.AVAILABILITY_CTA,
                style = typeScale.labelMd,
                color = colors.primary,
                modifier = Modifier
                    .clickable { navigator.navigateTo(NavSection.Contact) }
                    .focusRing(RoundedCornerShape(LuminaDimens.cornerRadius.button))
                    .semantics {
                        role = Role.Button
                        contentDescription = FooterStrings.AVAILABILITY_CTA
                    },
            )
        }
    }
    if (isMobile) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
        ) {
            content()
        }
    } else {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            content()
        }
    }
}

private const val FOOTER_BRAND_WEIGHT = 5f
private const val FOOTER_NAV_WEIGHT = 3f
private const val FOOTER_CONTACT_WEIGHT = 4f
private const val TWO = 2

object FooterStrings {
    const val WORDMARK = "Naulia Zahra"
    const val STUDIO_BADGE = "Studio"
    const val BRAND_DESCRIPTION = "Menciptakan perjalanan masa kecil yang berkesan melalui karya editorial, penceritaan suara bernyawa, dan kehadiran panggung yang hangat nan elegan."
    const val NAVIGATION_HEADING = "Navigasi"
    const val CONTACT_HEADING = "Kontak & Manajemen Agensi"
    const val AGENCY_DESCRIPTION = "Terbuka untuk kolaborasi penyuntingan literatur anak, pengisian suara animasi/audiobook internasional, dan pemanduan panggung festival."
    const val AGENCY_CONTACT_LABEL = "Hubungi Agensi & Studio"
    const val COPYRIGHT = "© 2025 Naulia Zahra. Dihadirkan dengan ketelitian editorial & kehangatan cerita."
    const val LOCATION = "Jakarta • Daring & Global"
    const val AVAILABILITY_CTA = "Tanyakan Ketersediaan"
    const val EMAIL_ICON_DESCRIPTION = "Kirim email"
    const val AUDIO_ICON_DESCRIPTION = "Lihat repertoar audio"
    const val STAGE_ICON_DESCRIPTION = "Lihat panggung acara"
}
