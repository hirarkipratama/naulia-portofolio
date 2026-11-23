package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class ServiceType(val labelBahasa: String) {
    BOOK_EDITOR("Editor Buku"),
    MC_EVENT("MC Acara / Moderator"),
    CHARACTER_VO("Voice Over Karakter"),
    FULL_COLLABORATION("Kolaborasi Lengkap"),
}
