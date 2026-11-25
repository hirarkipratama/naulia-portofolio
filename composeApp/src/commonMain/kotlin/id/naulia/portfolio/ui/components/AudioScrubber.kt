package id.naulia.portfolio.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import id.naulia.portfolio.data.model.toTimeString
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale

@Composable
fun AudioScrubber(
    progressSec: Double,
    durationSec: Double,
    onSeek: (Double) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val progress = remember(progressSec, durationSec) {
        if (durationSec > 0.0) {
            (progressSec / durationSec).toFloat().coerceIn(MIN_PROGRESS, MAX_PROGRESS)
        } else {
            MIN_PROGRESS
        }
    }

    Column(modifier = modifier) {
        Slider(
            value = progress,
            onValueChange = { value -> onSeek(value * durationSec) },
            colors = SliderDefaults.colors(
                thumbColor = colors.primary,
                activeTrackColor = colors.primary,
                inactiveTrackColor = colors.outlineVariant,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(LuminaDimens.size.scrubberHeight)
                .semantics {
                    contentDescription = AudioScrubberStrings.SCRUBBER_DESCRIPTION
                    stateDescription = AudioScrubberStrings.progressDescription(
                        current = progressSec.toTimeString(),
                        total = durationSec.toTimeString(),
                    )
                },
        )
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = progressSec.toTimeString(),
                style = LocalLuminaTypeScale.current.labelSm,
                color = colors.onSurfaceVariant,
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = durationSec.toTimeString(),
                style = LocalLuminaTypeScale.current.labelSm,
                color = colors.onSurfaceVariant,
            )
        }
    }
}

private const val MIN_PROGRESS = 0f
private const val MAX_PROGRESS = 1f

private object AudioScrubberStrings {
    const val SCRUBBER_DESCRIPTION = "Geser untuk mengatur posisi audio"
    const val PROGRESS_SEPARATOR = "dari"

    fun progressDescription(current: String, total: String): String =
        "$current $PROGRESS_SEPARATOR $total"
}
