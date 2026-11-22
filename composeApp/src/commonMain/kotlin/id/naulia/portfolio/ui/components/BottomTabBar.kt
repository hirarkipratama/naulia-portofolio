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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import id.naulia.portfolio.navigation.NavSection
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale

@Composable
fun BottomTabBar(
    activeSection: NavSection,
    onSectionClick: (NavSection) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(LuminaDimens.size.bottomBarHeight),
        color = colors.surface,
        shadowElevation = LuminaDimens.size.barElevation,
        shape = RectangleShape,
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
                val iconColor = if (isActive) colors.onPrimary else colors.onSurface
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(LuminaDimens.cornerRadius.button))
                        .background(if (isActive) colors.primary else colors.surface)
                        .clickable { onSectionClick(section) }
                        .semantics {
                            role = Role.Button
                            contentDescription = section.labelBahasa
                        }
                        .padding(
                            horizontal = LuminaDimens.spacing.xs,
                            vertical = LuminaDimens.spacing.xs,
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = section.imageVector,
                        contentDescription = section.labelBahasa,
                        tint = iconColor,
                        modifier = Modifier.size(20.dp),
                    )
                    Spacer(Modifier.height(2.dp))
                    Text(
                        text = section.labelBahasa,
                        style = LocalLuminaTypeScale.current.tabLabel,
                        color = iconColor,
                    )
                }
            }
        }
    }
}
