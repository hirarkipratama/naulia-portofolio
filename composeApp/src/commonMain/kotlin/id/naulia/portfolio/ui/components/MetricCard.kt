package id.naulia.portfolio.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import id.naulia.portfolio.platform.prefersReducedMotion
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MetricCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val reducedMotion = remember { prefersReducedMotion() }
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val tilt by animateFloatAsState(
        targetValue = if (isHovered && !reducedMotion) HOVER_TILT_DEGREES else NO_TILT,
    )
    val cardBrush = remember(colors) {
        Brush.linearGradient(listOf(colors.primary, colors.primaryVariant))
    }

    Card(
        modifier = modifier
            .graphicsLayer {
                rotationX = tilt
                rotationY = -tilt
            }
            .hoverable(interactionSource = interactionSource)
            .semantics { contentDescription = MetricCardStrings.description(value, label) },
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.card),
        colors = CardDefaults.cardColors(containerColor = colors.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = LuminaDimens.size.cardElevation),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(cardBrush)
                .padding(LuminaDimens.spacing.lg),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.displayLarge,
                    color = colors.onPrimary,
                    textAlign = TextAlign.Center,
                )
                Spacer(Modifier.height(LuminaDimens.spacing.sm))
                Text(
                    text = label,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colors.onPrimary,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

private const val HOVER_TILT_DEGREES = 6f
private const val NO_TILT = 0f

private object MetricCardStrings {
    fun description(value: String, label: String): String = "$value $label"
}
