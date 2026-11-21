package id.naulia.portfolio.data.cms

import id.naulia.portfolio.data.model.AudioTrack
import id.naulia.portfolio.data.model.Book
import id.naulia.portfolio.data.model.McEvent
import io.ktor.client.HttpClient

// Phase 2: Remote CMS integration via ktor-client-js
// Will fetch JSON from a configured backend URL
class CmsDataSource(private val httpClient: HttpClient) {
    suspend fun fetchTracks(): List<AudioTrack> = TODO("Phase 2 — Remote CMS")
    suspend fun fetchBooks(): List<Book> = TODO("Phase 2 — Remote CMS")
    suspend fun fetchEvents(): List<McEvent> = TODO("Phase 2 — Remote CMS")
}
