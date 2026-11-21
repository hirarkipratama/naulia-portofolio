package id.naulia.portfolio.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import id.naulia.portfolio.data.model.AudioTrack
import id.naulia.portfolio.data.model.formattedDuration
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
    val isActivePlaying = isActive && isPlaying

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = LuminaDimens.spacing.xs),
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.card),
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = LuminaDimens.size.cardElevation),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(LuminaDimens.spacing.md),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .width(LuminaDimens.size.cardAccent)
                        .height(LuminaDimens.size.playButton)
                        .background(colors.primary),
                )
                Spacer(Modifier.width(LuminaDimens.spacing.sm))
                Surface(
                    modifier = Modifier
                        .width(LuminaDimens.size.playButton)
                        .clickable { onTogglePlay() }
                        .semantics {
                            role = Role.Button
                            contentDescription = if (isActivePlaying) {
                                AudioTrackCardStrings.pauseDescription(track.title)
                            } else {
                                AudioTrackCardStrings.playDescription(track.title)
                            }
                        },
                    shape = CircleShape,
                    color = if (isActivePlaying) colors.primary else colors.surfaceVariant,
                ) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(
                            text = if (isActivePlaying) {
                                AudioTrackCardStrings.PAUSE_ICON
                            } else {
                                AudioTrackCardStrings.PLAY_ICON
                            },
                            style = LocalLuminaTypeScale.current.labelLarge,
                            color = if (isActivePlaying) colors.onPrimary else colors.primary,
                        )
                    }
                }
                Spacer(Modifier.width(LuminaDimens.spacing.md))
                Column(modifier = Modifier.weight(TRACK_INFO_WEIGHT)) {
                    Text(
                        text = track.title,
                        style = MaterialTheme.typography.bodyLarge,
                        color = colors.onBackground,
                    )
                    Spacer(Modifier.height(LuminaDimens.spacing.xs))
                    Surface(
                        shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
                        color = colors.secondary,
                    ) {
                        Text(
                            text = track.category.labelBahasa,
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.onPrimary,
                            modifier = Modifier.padding(
                                horizontal = LuminaDimens.spacing.sm,
                                vertical = LuminaDimens.spacing.xs,
                            ),
                        )
                    }
                    if (track.tags.isNotEmpty()) {
                        Spacer(Modifier.height(LuminaDimens.spacing.xs))
                        Text(
                            text = track.tags.joinToString(AudioTrackCardStrings.TAG_SEPARATOR),
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.onSurface,
                        )
                    }
                }
                Text(
                    text = track.formattedDuration(),
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.onSurface,
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
    const val PLAY_ICON = "▶"
    const val PAUSE_ICON = "⏸"
    const val TAG_SEPARATOR = " · "

    fun playDescription(title: String): String = "Play $title"

    fun pauseDescription(title: String): String = "Pause $title"
}
