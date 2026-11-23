package id.naulia.portfolio.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale

@Composable
fun CredentialChip(
    label: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    iconTint: Color? = null,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    Surface(
        modifier = modifier.semantics { contentDescription = label },
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
        color = colors.surfaceContainerLow,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = LuminaDimens.size.eyebrowHorizontalPadding,
                vertical = LuminaDimens.spacing.xs,
            ),
            horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xs),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint ?: colors.primary,
                modifier = Modifier.size(LuminaDimens.size.buttonIcon),
            )
            Text(
                text = label,
                style = type.labelMd,
                color = colors.onSurface,
            )
        }
    }
}
