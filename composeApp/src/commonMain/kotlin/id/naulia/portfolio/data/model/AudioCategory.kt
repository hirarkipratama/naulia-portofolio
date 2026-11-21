package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class AudioCategory(val labelBahasa: String, val labelEnglish: String) {
    ALL("Semua", "All"),
    STORY("Buku Cerita", "Story Books"),
    SCIENCE("Sains", "Science"),
    CHARACTER_VO("VO Karakter", "Character VO"),
}
