package id.naulia.portfolio.viewmodel

import androidx.compose.runtime.Stable
import id.naulia.portfolio.data.model.AudioTrack
import id.naulia.portfolio.data.model.Book
import id.naulia.portfolio.data.model.McEvent
import id.naulia.portfolio.data.model.PortfolioMetrics

@Stable
data class ContentState(
    val tracks: List<AudioTrack> = emptyList(),
    val books: List<Book> = emptyList(),
    val events: List<McEvent> = emptyList(),
    val metrics: PortfolioMetrics = DEFAULT_METRICS,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
) {
    companion object {
        const val LOAD_ERROR_MESSAGE = "Gagal memuat konten. Silakan coba lagi."

        val DEFAULT_METRICS = PortfolioMetrics(
            totalEvents = 50,
            officialPartners = "Mitra Resmi Gramedia",
            audiobooksProduced = 10,
            clientRetentionPct = 98,
            titlesEdited = 15,
        )
    }
}
