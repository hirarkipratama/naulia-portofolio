package id.naulia.portfolio.ui.components

import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors

@Composable
fun AnimatedEqualizer(
    isPlaying: Boolean,
    reducedMotion: Boolean,
    modifier: Modifier = Modifier,
) {
    val transition = rememberInfiniteTransition()
    Row(
        modifier = modifier.semantics {
            contentDescription = if (isPlaying) {
                EqualizerStrings.PLAYING_DESCRIPTION
            } else {
                EqualizerStrings.IDLE_DESCRIPTION
            }
        },
        horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xs),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(EQUALIZER_BAR_COUNT) { index ->
            EqualizerBar(
                index = index,
                isPlaying = isPlaying,
                reducedMotion = reducedMotion,
                transition = transition,
            )
        }
    }
}

@Composable
private fun EqualizerBar(
    index: Int,
    isPlaying: Boolean,
    reducedMotion: Boolean,
    transition: InfiniteTransition,
) {
    val colors = LocalLuminaColors.current
    val minHeight = LuminaDimens.size.equalizerBarMinHeight.value
    val midHeight = LuminaDimens.size.equalizerBarMidHeight.value
    val maxHeight = LuminaDimens.size.equalizerBarMaxHeight.value
    val animatedHeight by transition.animateFloat(
        initialValue = minHeight,
        targetValue = maxHeight,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = BASE_DURATION_MS + index * STAGGER_MS,
                easing = EaseInOutSine,
            ),
            repeatMode = RepeatMode.Reverse,
        ),
    )
    val height = when {
        !isPlaying -> minHeight
        reducedMotion -> midHeight
        else -> animatedHeight
    }
    Box(
        modifier = Modifier
            .width(LuminaDimens.size.equalizerBarWidth)
            .height(height.dp)
            .background(colors.primary, RoundedCornerShape(LuminaDimens.spacing.xs)),
    )
}

private const val EQUALIZER_BAR_COUNT = 5
private const val BASE_DURATION_MS = 300
private const val STAGGER_MS = 70

private object EqualizerStrings {
    const val PLAYING_DESCRIPTION = "Visualisasi audio sedang diputar"
    const val IDLE_DESCRIPTION = "Visualisasi audio tidak aktif"
}
