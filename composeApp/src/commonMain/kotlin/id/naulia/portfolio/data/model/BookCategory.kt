package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class BookCategory(val label: String) {
    SAINS("Sains"),
    BUKU_CERITA("Buku Cerita"),
    ENSIKLOPEDIA("Ensiklopedia"),
}
