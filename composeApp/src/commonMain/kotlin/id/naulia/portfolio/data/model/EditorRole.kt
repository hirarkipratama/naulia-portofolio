package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class EditorRole(val displayBadge: String) {
    DEVELOPMENTAL_EDITOR("Editor Pengembangan"),
    STEM_FACT_CHECKER("Pemeriksa Fakta STEM"),
    LANGUAGE_CONSULTANT("Konsultan Bahasa"),
    SERIES_EDITOR("Editor Seri"),
}
