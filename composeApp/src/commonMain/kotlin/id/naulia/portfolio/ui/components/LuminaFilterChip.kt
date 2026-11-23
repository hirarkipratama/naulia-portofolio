package id.naulia.portfolio.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale

@Composable
fun LuminaFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val containerColor by animateColorAsState(
        targetValue = if (selected) colors.primary else colors.surfaceContainerLow,
    )
    val contentColor by animateColorAsState(
        targetValue = if (selected) colors.onPrimary else colors.onSurfaceVariant,
    )
    val borderColor by animateColorAsState(
        targetValue = if (selected) colors.primary else colors.outlineVariant,
    )

    Surface(
        onClick = onClick,
        modifier = modifier.semantics {
            role = Role.Button
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
