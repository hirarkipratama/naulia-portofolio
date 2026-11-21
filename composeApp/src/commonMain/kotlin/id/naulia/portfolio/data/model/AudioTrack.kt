package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AudioTrack(
    val id: String,
    val title: String,
    val category: AudioCategory,
    val durationSec: Int,
    val assetUrl: String,
    val tags: List<String>,
    val description: String = "",
)
