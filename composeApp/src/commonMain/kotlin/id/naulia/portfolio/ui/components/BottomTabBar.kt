package id.naulia.portfolio.ui.components

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import id.naulia.portfolio.navigation.NavSection
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.focusRing

@Composable
fun BottomTabBar(
    activeSection: NavSection,
    onSectionClick: (NavSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val pillShape = RoundedCornerShape(LuminaDimens.cornerRadius.pill)

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(LuminaDimens.size.bottomBarHeight),
        color = colors.surfaceContainerLowest,
        shadowElevation = LuminaDimens.elevation.level2,
        shape = pillShape,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LuminaDimens.spacing.xs),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NavSection.all.forEach { section ->
                val isActive = section == activeSection
                val iconColor = if (isActive) colors.onPrimary else colors.onSurfaceVariant
                Column(
                    modifier = Modifier
                        .clip(pillShape)
                        .background(if (isActive) colors.primary else colors.surfaceContainerLowest)
                        .clickable { onSectionClick(section) }
                        .focusRing(pillShape)
                        .semantics {
                            role = Role.Button
                            contentDescription = section.navLabelBahasa
                            stateDescription = if (isActive) {
                                BottomTabBarStrings.ACTIVE_SECTION
                            } else {
                                BottomTabBarStrings.INACTIVE_SECTION
                            }
                        }
                        .padding(
                            horizontal = LuminaDimens.spacing.xs,
                            vertical = LuminaDimens.spacing.xs,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = section.imageVector,
                        contentDescription = section.navLabelBahasa,
                        tint = iconColor,
                        modifier = Modifier.size(LuminaDimens.size.tabIcon),
                    )
                    Spacer(Modifier.height(LuminaDimens.size.tabLabelGap))
                    Text(
                        text = section.labelBahasa,
                        style = type.tabLabel,
                        color = iconColor,
                    )
                }
            }
        }
    }
}

private object BottomTabBarStrings {
    const val ACTIVE_SECTION = "Bagian aktif"
    const val INACTIVE_SECTION = "Bagian lain"
}
