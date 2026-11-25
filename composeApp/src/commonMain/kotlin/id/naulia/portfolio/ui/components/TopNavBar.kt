package id.naulia.portfolio.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape as MaterialRectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import id.naulia.portfolio.navigation.NavSection
import id.naulia.portfolio.ui.icons.LuminaIcons
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.focusRing

@Composable
fun TopNavBar(
    activeSection: NavSection,
    onSectionClick: (NavSection) -> Unit,
    isCompact: Boolean,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    var menuExpanded by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(LuminaDimens.size.topBarHeight),
        color = colors.surfaceContainerLowest,
        shadowElevation = LuminaDimens.elevation.level1,
        shape = MaterialRectangleShape,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = if (isCompact) {
                        LuminaDimens.margin.mobile
                    } else {
                        LuminaDimens.margin.desktop
                    },
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = TopNavStrings.WORDMARK,
                    style = type.titleLarge,
                    color = colors.primary,
                )
                Spacer(Modifier.width(LuminaDimens.spacing.xs))
                Surface(
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(
                        LuminaDimens.cornerRadius.pill,
                    ),
                    color = colors.secondaryFixed,
                ) {
                    Text(
                        text = TopNavStrings.STUDIO_BADGE,
                        style = type.labelSmall,
                        color = colors.onSecondaryFixed,
                        modifier = Modifier.padding(
                            horizontal = LuminaDimens.spacing.xs,
                            vertical = LuminaDimens.spacing.xs / TWO,
                        ),
                    )
                }
            }
            Spacer(Modifier.weight(1f))
            if (isCompact) {
                IconButton(
                    onClick = { menuExpanded = true },
                    modifier = Modifier.semantics {
                        role = Role.Button
                        contentDescription = TopNavStrings.OPEN_MENU
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = TopNavStrings.OPEN_MENU,
                        tint = colors.onSurface,
                    )
                }
                DropdownMenu(
                    expanded = menuExpanded,
                    onDismissRequest = { menuExpanded = false },
                ) {
                    NavSection.all.forEach { section ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = section.navLabelBahasa,
                                    style = type.bodyMd,
                                    color = if (section == activeSection) colors.primary else colors.onSurfaceVariant,
                                )
                            },
                            onClick = {
                                menuExpanded = false
                                onSectionClick(section)
                            },
                        )
                    }
                }
            } else {
                Surface(
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(
                        LuminaDimens.cornerRadius.pill,
                    ),
                    color = colors.surfaceContainerLow,
                    shadowElevation = LuminaDimens.elevation.level1,
                ) {
                    Row(
                        modifier = Modifier.padding(LuminaDimens.spacing.xs / TWO),
                        horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xs / TWO),
                    ) {
                        NavSection.primaryNav.forEach { section ->
                            val isActive = section == activeSection
                            Surface(
                                onClick = { onSectionClick(section) },
                                shape = androidx.compose.foundation.shape.RoundedCornerShape(
                                    LuminaDimens.cornerRadius.pill,
                                ),
                                color = if (isActive) colors.primaryContainer else Color.Transparent,
                                contentColor = if (isActive) {
                                    colors.onPrimaryContainer
                                } else {
                                    colors.onSurfaceVariant
                                },
                                shadowElevation = if (isActive) {
                                    LuminaDimens.elevation.level1
                                } else {
                                    LuminaDimens.elevation.level0
                                },
                                modifier = Modifier
                                    .focusRing(
                                        androidx.compose.foundation.shape.RoundedCornerShape(
                                            LuminaDimens.cornerRadius.pill,
                                        ),
                                    )
                                    .semantics {
                                        role = Role.Button
                                        contentDescription = section.navLabelBahasa
                                        stateDescription = if (isActive) {
                                            TopNavStrings.ACTIVE_SECTION
                                        } else {
                                            TopNavStrings.INACTIVE_SECTION
                                        }
                                    },
                            ) {
                                Text(
                                    text = section.navLabelBahasa,
                                    style = type.labelMd,
                                    modifier = Modifier.padding(
                                        horizontal = LuminaDimens.spacing.sm,
                                        vertical = LuminaDimens.spacing.xs,
                                    ),
                                )
                            }
                        }
                    }
                }
                Spacer(Modifier.width(LuminaDimens.spacing.sm))
                LuminaPrimaryButton(
                    label = TopNavStrings.INVITE_CTA,
                    onClick = { onSectionClick(NavSection.Contact) },
                    icon = LuminaIcons.Mail,
                )
            }
        }
    }
}

object TopNavStrings {
    const val WORDMARK = "Naulia Zahra"
    const val STUDIO_BADGE = "Studio"
    const val OPEN_MENU = "Buka menu navigasi"
    const val INVITE_CTA = "Undang Sekarang"
    const val ACTIVE_SECTION = "Bagian aktif"
    const val INACTIVE_SECTION = "Bagian lain"
}

private const val TWO = 2
