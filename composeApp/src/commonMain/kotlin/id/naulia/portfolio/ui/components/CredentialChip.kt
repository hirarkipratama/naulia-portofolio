package id.naulia.portfolio.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale

@Composable
fun CredentialChip(
    label: String,
    icon: String,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    Surface(
        modifier = modifier.semantics { contentDescription = label },
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
        color = colors.primary,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = LuminaDimens.spacing.md,
                vertical = LuminaDimens.spacing.sm,
            ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = icon, style = LocalLuminaTypeScale.current.labelSmall)
            Spacer(Modifier.width(LuminaDimens.spacing.xs))
            Text(
                text = label,
                style = LocalLuminaTypeScale.current.labelSmall,
                color = colors.onPrimary,
            )
        }
    }
}
