package id.naulia.portfolio.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import id.naulia.portfolio.platform.prefersReducedMotion
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors

@Composable
fun LuminaCard(
    modifier: Modifier = Modifier,
    containerColor: Color? = null,
    contentPadding: PaddingValues = PaddingValues(LuminaDimens.spacing.md),
    content: @Composable ColumnScope.() -> Unit,
) {
    val colors = LocalLuminaColors.current
    val reducedMotion = remember { prefersReducedMotion() }
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val shape = RoundedCornerShape(LuminaDimens.cornerRadius.card)
    val lift by animateDpAsState(
        targetValue = if (isHovered && !reducedMotion) LuminaDimens.motion.hoverLift else 0.dp,
    )
    val elevation by animateDpAsState(
        targetValue = if (isHovered && !reducedMotion) {
            LuminaDimens.elevation.level2
        } else {
            LuminaDimens.elevation.level1
        },
    )
    val shadowColor = if (isHovered) colors.shadowCoral else colors.shadowWarm

    Surface(
        modifier = modifier
            .graphicsLayer { translationY = -lift.toPx() }
            .shadow(elevation = elevation, shape = shape, ambientColor = shadowColor, spotColor = shadowColor)
            .hoverable(interactionSource = interactionSource),
        shape = shape,
        color = containerColor ?: colors.surfaceContainerLowest,
        border = BorderStroke(LuminaDimens.size.thinBorder, colors.outlineVariant.copy(alpha = CARD_BORDER_ALPHA)),
    ) {
        Column(modifier = Modifier.padding(contentPadding), content = content)
    }
}

private const val CARD_BORDER_ALPHA = 0.6f
