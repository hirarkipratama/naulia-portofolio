package id.naulia.portfolio.data.model

import androidx.compose.runtime.Immutable

@Immutable
data class AudioTrack(
    val id: String,
    val title: String,
    val category: AudioCategory,
    val durationSec: Int,
    val assetUrl: String,
    val coverAsset: String = "",
    val description: String = "",
    val tags: List<String> = emptyList(),
)
