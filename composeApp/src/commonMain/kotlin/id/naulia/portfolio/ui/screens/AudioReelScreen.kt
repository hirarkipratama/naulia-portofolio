package id.naulia.portfolio.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import id.naulia.portfolio.data.model.AudioCategory
import id.naulia.portfolio.platform.prefersReducedMotion
import id.naulia.portfolio.ui.components.AudioTrackCard
import id.naulia.portfolio.ui.components.SectionHeader
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
                horizontal = LuminaDimens.spacing.xl,
                vertical = LuminaDimens.spacing.xxl,
            ),
    ) {
        SectionHeader(
            title = AudioReelStrings.TITLE,
            subtitle = AudioReelStrings.SUBTITLE,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
        ) {
            AudioCategory.entries.forEach { category ->
                val isSelected = category == uiState.selectedCategory
                FilterChip(
                    selected = isSelected,
                    onClick = { audioViewModel.selectCategory(category) },
                    label = {
                        Text(
                            text = category.labelBahasa,
                            style = MaterialTheme.typography.labelLarge,
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = colors.primary,
                        selectedLabelColor = colors.onPrimary,
                        containerColor = colors.surfaceVariant,
                        labelColor = colors.onSurface,
                    ),
                    modifier = Modifier.semantics {
                        role = Role.Button
                        contentDescription = category.labelBahasa
                        stateDescription = if (isSelected) {
                            AudioReelStrings.SELECTED
                        } else {
                            AudioReelStrings.NOT_SELECTED
                        }
                    },
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
    const val TITLE = "Buku Suara & Demo VO"
    const val SUBTITLE = "Dengarkan cuplikan buku suara, narasi sains, dan karakter suara Naulia Zahra"
    const val SELECTED = "Dipilih"
    const val NOT_SELECTED = "Tidak dipilih"
}
