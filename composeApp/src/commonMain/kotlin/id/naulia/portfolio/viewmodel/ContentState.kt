package id.naulia.portfolio.viewmodel

import androidx.compose.runtime.Stable
import id.naulia.portfolio.data.model.AudioTrack
import id.naulia.portfolio.data.model.Book
import id.naulia.portfolio.data.model.BookFilter
import id.naulia.portfolio.data.model.McEvent
import id.naulia.portfolio.data.model.PortfolioMetrics

@Stable
data class ContentState(
    val tracks: List<AudioTrack> = emptyList(),
    val books: List<Book> = emptyList(),
    val selectedBookFilter: BookFilter = BookFilter.ALL,
    val events: List<McEvent> = emptyList(),
    val metrics: PortfolioMetrics = PortfolioMetrics.DEFAULT,
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
) {
    companion object {
        const val LOAD_ERROR_MESSAGE = "Gagal memuat konten. Silakan coba lagi."
    }
}
