package id.naulia.portfolio.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import id.naulia.portfolio.platform.prefersReducedMotion
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.focusRing

@Composable
fun LuminaFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val reducedMotion = remember { prefersReducedMotion() }
    val containerColor by animateColorAsState(
        targetValue = if (selected) colors.primary else colors.surfaceContainerLow,
        animationSpec = if (reducedMotion) snap() else spring(),
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) colors.onPrimary else colors.onSurfaceVariant,
        animationSpec = if (reducedMotion) snap() else spring(),
    )
    val borderColor by animateColorAsState(
        targetValue = if (selected) colors.primary else colors.outlineVariant,
        animationSpec = if (reducedMotion) snap() else spring(),
    )

    Surface(
        onClick = onClick,
        modifier = modifier
            .focusRing(RoundedCornerShape(LuminaDimens.cornerRadius.pill))
            .semantics {
                role = Role.RadioButton
                contentDescription = label
                stateDescription = if (selected) {
                    LuminaFilterChipStrings.selected
                } else {
                    LuminaFilterChipStrings.notSelected
                }
            },
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
        color = containerColor,
        contentColor = contentColor,
        border = BorderStroke(LuminaDimens.size.thinBorder, borderColor),
    ) {
        Text(
            text = label,
            style = type.labelMd,
            color = contentColor,
            modifier = Modifier.padding(
                horizontal = LuminaDimens.spacing.md,
                vertical = LuminaDimens.spacing.sm,
            ),
        )
    }
}

private object LuminaFilterChipStrings {
    const val selected = "Dipilih"
    const val notSelected = "Tidak dipilih"
}
