package id.naulia.portfolio.data.repository

import id.naulia.portfolio.data.model.AudioCategory
import id.naulia.portfolio.data.model.AudioTrack
import id.naulia.portfolio.data.model.Book
import id.naulia.portfolio.data.model.McEvent
import id.naulia.portfolio.data.model.PortfolioMetrics

interface ContentRepository {
    fun getAudioTracks(): List<AudioTrack>
    fun getBooks(): List<Book>
    fun getMcEvents(): List<McEvent>
    fun getMetrics(): PortfolioMetrics
    fun getTracksByCategory(category: AudioCategory): List<AudioTrack>
}
