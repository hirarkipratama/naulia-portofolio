package id.naulia.portfolio.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import id.naulia.portfolio.ui.theme.LuminaColorScheme
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale

enum class CapabilityTone { Primary, Secondary, Tertiary }

@Composable
fun CapabilityCard(
    icon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    tone: CapabilityTone = CapabilityTone.Primary,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val tileColors = CapabilityCardStrings.tileColors(tone, colors)

    LuminaCard(
        modifier = modifier.semantics(mergeDescendants = true) {
            contentDescription = CapabilityCardStrings.description(title, description)
        },
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
            verticalAlignment = Alignment.Top,
        ) {
            Box(
                modifier = Modifier
                    .size(LuminaDimens.size.iconButton)
                    .clip(RoundedCornerShape(LuminaDimens.radius.md))
                    .background(tileColors.container),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tileColors.content,
                    modifier = Modifier.size(LuminaDimens.size.tabIcon),
                )
            }
            Column {
                Text(
                    text = title,
                    style = type.headlineSm,
                    color = colors.onSurface,
                )
                Spacer(Modifier.height(LuminaDimens.spacing.xs))
                Text(
                    text = description,
                    style = type.bodySm,
                    color = colors.onSurfaceVariant,
                )
            }
        }
    }
}

private data class TileColors(val container: Color, val content: Color)

private object CapabilityCardStrings {
    const val TILE_ALPHA = 0.35f

    fun tileColors(tone: CapabilityTone, colors: LuminaColorScheme): TileColors = when (tone) {
        CapabilityTone.Primary -> TileColors(
            colors.primaryContainer.copy(alpha = TILE_ALPHA),
            colors.primary,
        )
        CapabilityTone.Secondary -> TileColors(
            colors.secondaryContainer.copy(alpha = TILE_ALPHA),
            colors.secondary,
        )
        CapabilityTone.Tertiary -> TileColors(
            colors.tertiaryContainer.copy(alpha = TILE_ALPHA),
            colors.tertiary,
        )
    }

    fun description(title: String, description: String): String = "$title. $description"
}
