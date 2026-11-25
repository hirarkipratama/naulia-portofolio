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
import id.naulia.portfolio.data.model.ServiceType
import id.naulia.portfolio.platform.prefersReducedMotion
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.focusRing

@Composable
fun SelectableServicePill(
    service: ServiceType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val reducedMotion = remember { prefersReducedMotion() }
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) colors.secondaryContainer else colors.surfaceContainerHighest,
        animationSpec = if (reducedMotion) snap() else spring(),
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) colors.onSecondaryContainer else colors.onSurfaceVariant,
        animationSpec = if (reducedMotion) snap() else spring(),
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) colors.secondaryContainer else colors.outlineVariant,
        animationSpec = if (reducedMotion) snap() else spring(),
    )

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
        color = backgroundColor,
        border = BorderStroke(LuminaDimens.size.thinBorder, borderColor),
        modifier = modifier
            .focusRing(RoundedCornerShape(LuminaDimens.cornerRadius.pill))
            .semantics {
            role = Role.Checkbox
            stateDescription = if (isSelected) {
                SelectableServicePillStrings.SELECTED
            } else {
                SelectableServicePillStrings.NOT_SELECTED
            }
            contentDescription = service.labelBahasa
        },
    ) {
        Text(
            text = service.labelBahasa,
            style = type.labelMd,
            color = textColor,
            modifier = Modifier.padding(
                horizontal = LuminaDimens.spacing.md,
                vertical = LuminaDimens.spacing.sm,
            ),
        )
    }
}

private object SelectableServicePillStrings {
    const val SELECTED = "Dipilih"
    const val NOT_SELECTED = "Tidak dipilih"
}
