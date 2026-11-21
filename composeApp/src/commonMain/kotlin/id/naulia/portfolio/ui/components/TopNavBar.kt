package id.naulia.portfolio.ui.components

import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.Role
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.RectangleShape
import id.naulia.portfolio.navigation.NavSection
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale

@Composable
fun TopNavBar(
    activeSection: NavSection,
    onSectionClick: (NavSection) -> Unit,
    isCompact: Boolean,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    var menuExpanded by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(LuminaDimens.size.topBarHeight),
        color = colors.surface,
        shadowElevation = LuminaDimens.size.barElevation,
        shape = RectangleShape,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LuminaDimens.spacing.xl),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = TopNavStrings.WORDMARK,
                style = LocalLuminaTypeScale.current.titleLarge,
                color = colors.primary,
            )
            Spacer(Modifier.weight(1f))
            if (isCompact) {
                IconButton(
                    onClick = { menuExpanded = true },
                    modifier = Modifier.semantics {
                        role = Role.Button
                        contentDescription = TopNavStrings.OPEN_MENU
                    },
                ) {
                    Text(
                        text = TopNavStrings.MENU_ICON,
                        style = LocalLuminaTypeScale.current.titleLarge,
                        color = colors.onBackground,
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
                                    text = section.labelBahasa,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = if (section == activeSection) colors.primary else colors.onSurface,
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
                Row(horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.lg)) {
                    NavSection.all.forEach { section ->
                        val isActive = section == activeSection
                        Text(
                            text = section.labelBahasa,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isActive) colors.primary else colors.onBackground,
                            modifier = Modifier
                                .clip(RoundedCornerShape(LuminaDimens.cornerRadius.button))
                                .clickable { onSectionClick(section) }
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
        }
    }
}

object TopNavStrings {
    const val WORDMARK = "Naulia Zahra"
    const val OPEN_MENU = "Buka menu navigasi"
    const val MENU_ICON = "☰"
}
