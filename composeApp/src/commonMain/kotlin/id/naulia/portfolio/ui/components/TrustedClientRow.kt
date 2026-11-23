package id.naulia.portfolio.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import id.naulia.portfolio.ui.theme.LuminaColorScheme
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.rememberIsMobile

data class TrustedClient(val label: String, val icon: ImageVector, val tone: PillTone)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TrustedClientRow(
    label: String,
    clients: List<TrustedClient>,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val isMobile = rememberIsMobile()

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(LuminaDimens.radius.lg),
        color = colors.surfaceContainerLow,
    ) {
        if (isMobile) {
            Column(
                modifier = Modifier.padding(LuminaDimens.spacing.md),
                verticalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.md),
            ) {
                TrustedClientLabel(label = label)
                TrustedClientBadges(clients = clients)
            }
        } else {
            Row(
                modifier = Modifier.padding(LuminaDimens.spacing.md),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TrustedClientLabel(
                    label = label,
                    modifier = Modifier.weight(TRUSTED_LABEL_WEIGHT),
                )
                TrustedClientBadges(clients = clients)
            }
        }
    }
}

@Composable
private fun TrustedClientLabel(
    label: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = label,
        style = LocalLuminaTypeScale.current.labelSm,
        color = LocalLuminaColors.current.onSurfaceVariant,
        modifier = modifier,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TrustedClientBadges(
    clients: List<TrustedClient>,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.lg),
        verticalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
    ) {
        clients.forEach { client ->
            Row(
                modifier = Modifier.semantics(mergeDescendants = true) {
                    contentDescription = TrustedClientRowStrings.clientDescription(client.label)
                },
                horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = client.icon,
                    contentDescription = null,
                    tint = TrustedClientRowStrings.iconColor(client.tone, colors),
                    modifier = Modifier.size(LuminaDimens.size.tabIcon),
                )
                Text(
                    text = client.label,
                    style = LocalLuminaTypeScale.current.headlineSm.copy(fontWeight = FontWeight.Bold),
                    color = colors.onSurface,
                )
            }
        }
    }
}

private const val TRUSTED_LABEL_WEIGHT = 1f

private object TrustedClientRowStrings {
    fun clientDescription(label: String): String = "Klien terpercaya: $label"

    fun iconColor(tone: PillTone, colors: LuminaColorScheme) = when (tone) {
        PillTone.Primary -> colors.primary
        PillTone.Secondary -> colors.secondary
        PillTone.Tertiary -> colors.tertiary
        else -> colors.onSurfaceVariant
    }
}
