package id.naulia.portfolio.data.repository

import id.naulia.portfolio.data.model.AgeRange
import id.naulia.portfolio.data.model.AudioCategory
import id.naulia.portfolio.data.model.AudioTrack
import id.naulia.portfolio.data.model.Book
import id.naulia.portfolio.data.model.EditorRole
import id.naulia.portfolio.data.model.McEvent
import id.naulia.portfolio.data.model.PortfolioMetrics
import id.naulia.portfolio.data.model.Publisher

class LocalContentRepository : ContentRepository {
    private val audioTracks: List<AudioTrack> = listOf(
        AudioTrack(
            id = "track_01",
            title = "Kisah Si Kancil dan Buaya",
            category = AudioCategory.STORY,
            durationSec = 145,
            assetUrl = "audio/track_01.mp3",
            tags = listOf("Cerita Rakyat", "Karakter Ekspresif"),
        ),
        AudioTrack(
            id = "track_02",
            title = "Mengapa Langit Biru? — Buku Sains Anak",
            category = AudioCategory.SCIENCE,
            durationSec = 187,
            assetUrl = "audio/track_02.mp3",
            tags = listOf("Sains", "Edukatif"),
        ),
        AudioTrack(
            id = "track_03",
            title = "Demo Karakter: Nenek Sihir & Peri",
            category = AudioCategory.CHARACTER_VO,
            durationSec = 93,
            assetUrl = "audio/track_03.mp3",
            tags = listOf("Karakter", "Dramatik"),
        ),
        AudioTrack(
            id = "track_04",
            title = "Buku Cerita: Petualangan Dino Kecil",
            category = AudioCategory.STORY,
            durationSec = 210,
            assetUrl = "audio/track_04.mp3",
            tags = listOf("Adventure", "Anak-anak"),
        ),
        AudioTrack(
            id = "track_05",
            title = "Seri Sains: Sistem Tata Surya",
            category = AudioCategory.SCIENCE,
            durationSec = 168,
            assetUrl = "audio/track_05.mp3",
            tags = listOf("Astronomi", "STEM"),
        ),
        AudioTrack(
            id = "track_06",
            title = "Narasi Dokumenter: Hutan Hujan Indonesia",
            category = AudioCategory.CHARACTER_VO,
            durationSec = 124,
            assetUrl = "audio/track_06.mp3",
            tags = listOf("Narasi", "Alam"),
        ),
    )

    private val books: List<Book> = listOf(
        Book(
            id = "book_01",
            title = "Dino Kecil Belajar Berbagi",
            publisher = Publisher.GRAMEDIA,
            ageRange = AgeRange.AGE_4_8,
            roles = listOf(EditorRole.DEVELOPMENTAL_EDITOR),
            coverAsset = "images/covers/book_01.webp",
            year = 2023,
        ),
        Book(
            id = "book_02",
            title = "Seri STEM: Kenapa Air Mendidih?",
            publisher = Publisher.BIP,
            ageRange = AgeRange.AGE_7_12,
            roles = listOf(EditorRole.STEM_FACT_CHECKER, EditorRole.DEVELOPMENTAL_EDITOR),
            coverAsset = "images/covers/book_02.webp",
            year = 2023,
        ),
        Book(
            id = "book_03",
            title = "Peri Hutan dan Teman-Teman",
            publisher = Publisher.MIZAN,
            ageRange = AgeRange.AGE_3_6,
            roles = listOf(EditorRole.DEVELOPMENTAL_EDITOR),
            coverAsset = "images/covers/book_03.webp",
            year = 2022,
        ),
        Book(
            id = "book_04",
            title = "Atlas Anak: Hewan Indonesia",
            publisher = Publisher.ERLANGGA,
            ageRange = AgeRange.AGE_7_12,
            roles = listOf(EditorRole.STEM_FACT_CHECKER, EditorRole.LANGUAGE_CONSULTANT),
            coverAsset = "images/covers/book_04.webp",
            year = 2024,
        ),
        Book(
            id = "book_05",
            title = "Kisah Pahlawan Cilik",
            publisher = Publisher.GRAMEDIA,
            ageRange = AgeRange.AGE_4_8,
            roles = listOf(EditorRole.SERIES_EDITOR),
            coverAsset = "images/covers/book_05.webp",
            year = 2024,
        ),
        Book(
            id = "book_06",
            title = "Sains Ajaib: Tubuh Kita",
            publisher = Publisher.BIP,
            ageRange = AgeRange.AGE_7_12,
            roles = listOf(EditorRole.STEM_FACT_CHECKER),
            coverAsset = "images/covers/book_06.webp",
            year = 2023,
        ),
    )

    private val mcEvents: List<McEvent> = listOf(
        McEvent(
            id = "event_01",
            eventName = "MC Ulang Tahun Penerbit BIP ke-32",
            date = "2022-05",
            venue = "Gramedia Xperience",
            city = "Jakarta",
            estimatedAttendees = 300,
            photoAsset = "images/events/event_01.webp",
            tags = listOf("MC", "Penerbitan"),
        ),
        McEvent(
            id = "event_02",
            eventName = "Moderator Islamic Book Fair — Peluncuran 30 Kisah Nabi",
            date = "2023-03",
            venue = "Islamic Book Fair",
            city = "Jakarta",
            estimatedAttendees = 500,
            photoAsset = "images/events/event_02.webp",
            tags = listOf("Moderator", "Book Fair"),
        ),
        McEvent(
            id = "event_03",
            eventName = "MC Dongeng & Aktivitas Seru — MB Fair",
            date = "2023-08",
            venue = "Mall Book Festival",
            city = "Jakarta",
            estimatedAttendees = 450,
            photoAsset = "images/events/event_03.webp",
            tags = listOf("MC", "Anak-anak"),
        ),
        McEvent(
            id = "event_04",
            eventName = "Host Live Shopee — Buku-buku Anak Gramedia",
            date = "2023-11",
            venue = "Shopee Live Studio",
            city = "Jakarta",
            estimatedAttendees = 1200,
            photoAsset = "images/events/event_04.webp",
            tags = listOf("Live Hosting", "Gramedia"),
        ),
        McEvent(
            id = "event_05",
            eventName = "Moderator IIBF Book Talk — Ensiklopedia Cilik Nusantara",
            date = "2024-04",
            venue = "Indonesia International Book Fair",
            city = "Jakarta",
            estimatedAttendees = 400,
            photoAsset = "images/events/event_05.webp",
            tags = listOf("Moderator", "Science Expo"),
        ),
        McEvent(
            id = "event_06",
            eventName = "Host Book Talk — Novel Gerbang Dua Semesta",
            date = "2024-09",
            venue = "Gramedia Book Store",
            city = "Jakarta",
            estimatedAttendees = 250,
            photoAsset = "images/events/event_06.webp",
            tags = listOf("Book Talk", "MC"),
        ),
    )

    private val metrics = PortfolioMetrics(
        totalEvents = 50,
        officialPartners = "Mitra Resmi Gramedia",
        audiobooksProduced = 10,
        clientRetentionPct = 98,
        titlesEdited = 15,
    )

    override fun getAudioTracks(): List<AudioTrack> = audioTracks

    override fun getBooks(): List<Book> = books

    override fun getMcEvents(): List<McEvent> = mcEvents

    override fun getMetrics(): PortfolioMetrics = metrics

    override fun getTracksByCategory(category: AudioCategory): List<AudioTrack> =
        if (category == AudioCategory.ALL) audioTracks
        else audioTracks.filter { it.category == category }
}
