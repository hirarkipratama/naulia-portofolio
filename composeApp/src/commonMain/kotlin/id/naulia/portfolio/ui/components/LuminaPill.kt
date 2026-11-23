package id.naulia.portfolio.ui.components

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.semantics
import id.naulia.portfolio.platform.prefersReducedMotion
import id.naulia.portfolio.ui.theme.LuminaColorScheme
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale

enum class PillTone {
    Neutral,
    Primary,
    Secondary,
    Tertiary,
    Spotlight,
    Available,
    Inverse,
}

private data class PillColors(val container: Color, val content: Color)

private fun PillTone.colors(colors: LuminaColorScheme): PillColors = when (this) {
    PillTone.Neutral -> PillColors(colors.surfaceContainerHigh, colors.onSurface)
    PillTone.Primary -> PillColors(colors.primaryContainer.copy(alpha = 0.3f), colors.onPrimaryContainer)
    PillTone.Secondary -> PillColors(colors.secondaryContainer.copy(alpha = 0.5f), colors.onSecondaryContainer)
    PillTone.Tertiary -> PillColors(colors.tertiaryContainer.copy(alpha = 0.3f), colors.onTertiaryContainer)
    PillTone.Spotlight -> PillColors(colors.secondaryContainer, colors.onSecondaryContainer)
    PillTone.Available -> PillColors(colors.tertiaryContainer.copy(alpha = 0.2f), colors.onSurface)
    PillTone.Inverse -> PillColors(colors.inverseSurface.copy(alpha = INVERSE_PILL_ALPHA), colors.inverseOnSurface)
}

@Composable
fun SectionEyebrow(
    text: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    tone: PillTone = PillTone.Neutral,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val pill = tone.colors(colors)
    Surface(
        modifier = modifier.semantics(mergeDescendants = true) {},
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
        color = pill.container,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = LuminaDimens.size.eyebrowHorizontalPadding,
                vertical = LuminaDimens.size.eyebrowVerticalPadding,
            ),
            horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xs),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = pill.content,
                    modifier = Modifier.size(LuminaDimens.size.chipIcon),
                )
            }
            Text(text = text, style = type.labelMd, color = pill.content)
        }
    }
}

@Composable
fun StatusPill(
    label: String,
    modifier: Modifier = Modifier,
    tone: PillTone = PillTone.Available,
    icon: ImageVector? = null,
    showPulseDot: Boolean = tone == PillTone.Available,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val pill = tone.colors(colors)

    Surface(
        modifier = modifier.semantics(mergeDescendants = true) {},
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
        color = pill.container,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = LuminaDimens.size.eyebrowHorizontalPadding,
                vertical = LuminaDimens.spacing.xs,
            ),
            horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xs),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (showPulseDot) {
                PulsingDot(color = colors.tertiaryContainer)
            }
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = pill.content,
                    modifier = Modifier.size(LuminaDimens.size.chipIcon),
                )
            }
            Text(text = label, style = type.labelMd, color = pill.content)
        }
    }
}

@Composable
private fun PulsingDot(color: Color) {
    val reducedMotion = remember { prefersReducedMotion() }
    val transition = rememberInfiniteTransition()
    val animatedAlpha by transition.animateFloat(
        initialValue = PULSE_MIN_ALPHA,
        targetValue = PULSE_MAX_ALPHA,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = PULSE_DURATION_MS, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    Surface(
        shape = CircleShape,
        color = color,
        modifier = Modifier
            .size(LuminaDimens.size.chipDot)
            .alpha(if (reducedMotion) PULSE_MAX_ALPHA else animatedAlpha),
    ) {}
}

private const val INVERSE_PILL_ALPHA = 0.8f
private const val PULSE_MIN_ALPHA = 0.35f
private const val PULSE_MAX_ALPHA = 1f
private const val PULSE_DURATION_MS = 1400
