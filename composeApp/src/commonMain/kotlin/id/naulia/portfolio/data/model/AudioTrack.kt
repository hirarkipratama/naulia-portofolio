package id.naulia.portfolio.data.model

data class AudioTrack(
    val id: String,
    val title: String,
    val category: AudioCategory,
    val durationSec: Int,
    val assetUrl: String,
    val coverAsset: String = "",
    val tags: List<String> = emptyList(),
)
