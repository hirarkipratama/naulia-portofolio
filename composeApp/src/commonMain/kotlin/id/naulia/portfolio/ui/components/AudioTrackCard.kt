package id.naulia.portfolio.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import id.naulia.portfolio.data.model.AudioCategory
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
    val badgeTone = categoryTone(track.category)

    LuminaCard(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(LuminaDimens.spacing.md),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            StatusPill(
                label = track.category.labelBahasa,
                tone = badgeTone,
                showPulseDot = false,
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xs),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = LuminaIcons.Schedule,
                    contentDescription = null,
                    tint = colors.tertiary,
                    modifier = Modifier.size(LuminaDimens.size.chipIcon),
                )
                Text(
                    text = track.formattedDuration(),
                    style = type.labelSm,
                    color = colors.onSurfaceVariant,
                )
            }
        }
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        Text(
            text = track.title,
            style = type.headlineSm,
            color = colors.onSurface,
        )
        if (track.description.isNotBlank()) {
            Spacer(Modifier.height(LuminaDimens.spacing.xs))
            Text(
                text = track.description,
                style = type.bodySm,
                color = colors.onSurfaceVariant,
            )
        }
        Spacer(Modifier.height(LuminaDimens.spacing.md))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (albumArtBitmap != null) {
                    Image(
                        bitmap = albumArtBitmap,
                        contentDescription = AudioTrackCardStrings.coverDescription(track.title),
                        modifier = Modifier
                            .size(LuminaDimens.size.playButton)
                            .clip(RoundedCornerShape(LuminaDimens.cornerRadius.media)),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(Modifier.width(LuminaDimens.spacing.sm))
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
                Spacer(Modifier.width(LuminaDimens.spacing.sm))
                if (isActive) {
                    AnimatedEqualizer(
                        isPlaying = isPlaying,
                        reducedMotion = reducedMotion,
                    )
                }
                Spacer(Modifier.weight(TRACK_INFO_WEIGHT))
                Text(
                    text = if (isActive) {
                        AudioTrackCardStrings.PLAYING_LABEL
                    } else {
                        AudioTrackCardStrings.IDLE_LABEL
                    },
                    style = type.labelSm,
                    color = colors.onSurfaceVariant,
                )
            }
            Spacer(Modifier.height(LuminaDimens.spacing.sm))
            if (isActive) {
                AudioScrubber(
                    progressSec = progressSec,
                    durationSec = durationSec,
                    onSeek = onSeek,
                    modifier = Modifier.fillMaxWidth(),
                )
            } else {
                AudioTrackProgressPreview(durationLabel = track.formattedDuration())
            }
            if (track.tags.isNotEmpty()) {
                Spacer(Modifier.height(LuminaDimens.spacing.sm))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xs),
                ) {
                    track.tags.take(VISIBLE_TAG_COUNT).forEach { tag ->
                        AudioTagChip(label = tag)
                    }
                }
            }
        }
    }
}

@Composable
private fun AudioTrackProgressPreview(durationLabel: String) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(LuminaDimens.size.thinBorder)
            .clip(RoundedCornerShape(LuminaDimens.radius.pill))
            .background(colors.outlineVariant),
    )
    Spacer(Modifier.height(LuminaDimens.spacing.xs))
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = AudioTrackCardStrings.ZERO_TIME,
            style = type.labelSm,
            color = colors.onSurfaceVariant,
        )
        Spacer(Modifier.weight(TRACK_INFO_WEIGHT))
        Text(
            text = durationLabel,
            style = type.labelSm,
            color = colors.onSurfaceVariant,
        )
    }
}

@Composable
private fun AudioTagChip(label: String) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    Surface(
        shape = RoundedCornerShape(LuminaDimens.radius.md),
        color = colors.surfaceContainer,
    ) {
        Text(
            text = label,
            style = type.labelSm,
            color = colors.onSurfaceVariant,
            modifier = Modifier.padding(
                horizontal = LuminaDimens.spacing.sm,
                vertical = LuminaDimens.spacing.xs,
            ),
        )
    }
}

private fun categoryTone(category: AudioCategory): PillTone = when (category) {
    AudioCategory.STORY -> PillTone.Secondary
    AudioCategory.SCIENCE -> PillTone.Primary
    AudioCategory.CHARACTER_VO -> PillTone.Tertiary
    AudioCategory.ALL -> PillTone.Neutral
}

private const val TRACK_INFO_WEIGHT = 1f
private const val VISIBLE_TAG_COUNT = 2

object AudioTrackCardStrings {
    const val PLAYING_LABEL = "Memutar cuplikan"
    const val IDLE_LABEL = "Dengarkan cuplikan"
    const val ZERO_TIME = "0:00"

    fun playDescription(title: String): String = "Play $title"

    fun pauseDescription(title: String): String = "Pause $title"

    fun coverDescription(title: String): String = "Cover $title"
}
