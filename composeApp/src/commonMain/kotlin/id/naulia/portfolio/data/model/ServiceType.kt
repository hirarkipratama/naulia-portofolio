package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class ServiceType(val labelBahasa: String) {
    BOOK_EDITOR("Editor Buku"),
    MC_EVENT("MC Acara"),
    CHARACTER_VO("VO Karakter"),
    FULL_COLLABORATION("Kolaborasi Lengkap"),
}
