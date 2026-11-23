package id.naulia.portfolio.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import id.naulia.portfolio.data.model.AudioCategory
import id.naulia.portfolio.platform.prefersReducedMotion
import id.naulia.portfolio.ui.components.AudioTrackCard
import id.naulia.portfolio.ui.components.LuminaFilterChip
import id.naulia.portfolio.ui.components.PillTone
import id.naulia.portfolio.ui.components.SectionHeader
import id.naulia.portfolio.ui.icons.LuminaIcons
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.viewmodel.AudioViewModel

@Composable
fun AudioReelScreen(
    audioViewModel: AudioViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by audioViewModel.uiState.collectAsState()
    val reducedMotion = remember { prefersReducedMotion() }
    val colors = LocalLuminaColors.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LuminaDimens.margin.mobile,
                vertical = LuminaDimens.spacing.xl,
            ),
    ) {
        SectionHeader(
            title = AudioReelStrings.TITLE,
            subtitle = AudioReelStrings.SUBTITLE,
            eyebrow = AudioReelStrings.EYEBROW,
            eyebrowIcon = LuminaIcons.Mic,
            eyebrowTone = PillTone.Tertiary,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
        ) {
            AudioCategory.entries.forEach { category ->
                LuminaFilterChip(
                    label = category.labelBahasa,
                    selected = category == uiState.selectedCategory,
                    onClick = { audioViewModel.selectCategory(category) },
                )
            }
        }
        Spacer(Modifier.height(LuminaDimens.spacing.md))
        if (uiState.isLoading) {
            CircularProgressIndicator(color = colors.primary)
        }
        uiState.filteredTracks.forEach { track ->
            AudioTrackCard(
                track = track,
                isActive = uiState.activeTrackId == track.id,
                isPlaying = uiState.isPlaying,
                progressSec = uiState.progressSec,
                durationSec = uiState.durationSec,
                reducedMotion = reducedMotion,
                onTogglePlay = { audioViewModel.togglePlay(track) },
                onSeek = { seconds -> audioViewModel.seekTo(seconds) },
            )
        }
    }
}

private object AudioReelStrings {
    const val EYEBROW = "REEL SUARA & BUKU SUARA"
    const val TITLE = "Buku Suara & Demo VO"
    const val SUBTITLE = "Dengarkan cuplikan buku suara, narasi sains, dan karakter suara Naulia Zahra"
}
