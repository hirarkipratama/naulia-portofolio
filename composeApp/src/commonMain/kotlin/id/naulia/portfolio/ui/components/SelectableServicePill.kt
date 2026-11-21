package id.naulia.portfolio.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import id.naulia.portfolio.data.model.ServiceType
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors

@Composable
fun SelectableServicePill(
    service: ServiceType,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected) colors.primary else colors.surface,
    )
    val textColor by animateColorAsState(
        targetValue = if (isSelected) colors.onPrimary else colors.primary,
    )

    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
        color = backgroundColor,
        border = androidx.compose.foundation.BorderStroke(
            LuminaDimens.size.accentBorder,
            colors.primary,
        ),
        modifier = modifier.semantics {
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
            style = MaterialTheme.typography.labelLarge,
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
