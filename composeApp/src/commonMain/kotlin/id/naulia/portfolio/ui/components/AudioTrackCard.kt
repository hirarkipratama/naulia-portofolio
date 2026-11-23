package id.naulia.portfolio.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import id.naulia.portfolio.data.model.AudioTrack
import id.naulia.portfolio.data.model.formattedDuration
import id.naulia.portfolio.image.rememberImageBitmap
import id.naulia.portfolio.ui.icons.LuminaIcons
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale

@Composable
fun AudioTrackCard(
    track: AudioTrack,
    isActive: Boolean,
    isPlaying: Boolean,
    progressSec: Double,
    durationSec: Double,
    reducedMotion: Boolean,
    onTogglePlay: () -> Unit,
    onSeek: (Double) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val isActivePlaying = isActive && isPlaying
    val albumArtBitmap = rememberImageBitmap(track.coverAsset)

    LuminaCard(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = LuminaDimens.spacing.xs),
        containerColor = if (isActive) colors.surfaceContainerLow else colors.surfaceContainerLowest,
        contentPadding = PaddingValues(0.dp),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LuminaDimens.spacing.md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (albumArtBitmap != null) {
                    Image(
                        bitmap = albumArtBitmap,
                        contentDescription = "Cover ${track.title}",
                        modifier = Modifier
                            .size(LuminaDimens.size.playButton)
                            .clip(RoundedCornerShape(LuminaDimens.cornerRadius.media)),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(Modifier.width(LuminaDimens.spacing.md))
                }
                LuminaIconButton(
                    icon = if (isActivePlaying) LuminaIcons.Pause else LuminaIcons.Play,
                    contentDescription = if (isActivePlaying) {
                        AudioTrackCardStrings.pauseDescription(track.title)
                    } else {
                        AudioTrackCardStrings.playDescription(track.title)
                    },
                    onClick = onTogglePlay,
                    containerColor = if (isActivePlaying) colors.primary else colors.surfaceContainerHigh,
                    contentColor = if (isActivePlaying) colors.onPrimary else colors.primary,
                )
                Spacer(Modifier.width(LuminaDimens.spacing.md))
                Column(modifier = Modifier.weight(TRACK_INFO_WEIGHT)) {
                    Text(
                        text = track.title,
                        style = type.headlineSm,
                        color = colors.onSurface,
                    )
                    Spacer(Modifier.height(LuminaDimens.spacing.xs))
                    StatusPill(
                        label = track.category.labelBahasa,
                        tone = PillTone.Secondary,
                        showPulseDot = false,
                    )
                    if (track.tags.isNotEmpty()) {
                        Spacer(Modifier.height(LuminaDimens.spacing.xs))
                        Text(
                            text = track.tags.joinToString(AudioTrackCardStrings.TAG_SEPARATOR),
                            style = type.bodySm,
                            color = colors.onSurfaceVariant,
                        )
                    }
                }
                Text(
                    text = track.formattedDuration(),
                    style = type.labelSm,
                    color = colors.onSurfaceVariant,
                )
            }

            if (isActive) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = LuminaDimens.spacing.md,
                            vertical = LuminaDimens.spacing.sm,
                        )
                        .animateContentSize(),
                ) {
                    AnimatedEqualizer(
                        isPlaying = isPlaying,
                        reducedMotion = reducedMotion,
                        modifier = Modifier.padding(bottom = LuminaDimens.spacing.sm),
                    )
                    AudioScrubber(
                        progressSec = progressSec,
                        durationSec = durationSec,
                        onSeek = onSeek,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }
    }
}

private const val TRACK_INFO_WEIGHT = 1f

object AudioTrackCardStrings {
    const val TAG_SEPARATOR = " · "

    fun playDescription(title: String): String = "Play $title"

    fun pauseDescription(title: String): String = "Pause $title"
}
