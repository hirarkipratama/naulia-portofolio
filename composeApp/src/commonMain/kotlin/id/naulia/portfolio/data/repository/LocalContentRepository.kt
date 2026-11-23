package id.naulia.portfolio.data.repository

import id.naulia.portfolio.data.model.AgeRange
import id.naulia.portfolio.data.model.AudioCategory
import id.naulia.portfolio.data.model.AudioTrack
import id.naulia.portfolio.data.model.Book
import id.naulia.portfolio.data.model.BookCategory
import id.naulia.portfolio.data.model.EditorRole
import id.naulia.portfolio.data.model.McEvent
import id.naulia.portfolio.data.model.PortfolioMetrics
import id.naulia.portfolio.data.model.Publisher

class LocalContentRepository : ContentRepository {
    private val audioTracks: List<AudioTrack> = listOf(
        AudioTrack(
            id = "track_01",
            title = "Audiobook: Sepatu Berkucing",
            category = AudioCategory.STORY,
            durationSec = 145,
            assetUrl = "audio/Audiobook_Sepatu_Berkucing.mp3",
            coverAsset = "images/audio/sepatu_berkucing.webp",
            description = "Cerita dongeng hangat tentang persahabatan dan keberanian, dibawakan dengan " +
                "karakter suara yang ceria untuk pendengar cilik.",
            tags = listOf("Cerita Dongeng", "Karakter Ekspresif"),
        ),
        AudioTrack(
            id = "track_02",
            title = "Audiobook: Kok Tidak Berbunga?",
            category = AudioCategory.SCIENCE,
            durationSec = 187,
            assetUrl = "audio/Audiobook_Kok_Tidak_Berbunga.mp3",
            coverAsset = "images/audio/kok_tidak_berbunga.webp",
            description = "Sains anak yang menjawab rasa ingin tahu tentang tanaman lewat narasi lembut " +
                "dan analogi yang mudah dibayangkan.",
            tags = listOf("Sains Anak", "Tumbuhan"),
        ),
        AudioTrack(
            id = "track_03",
            title = "LPBD: Nikola Tesla",
            category = AudioCategory.CHARACTER_VO,
            durationSec = 135,
            assetUrl = "audio/LPBD_Nikola_Tesla.mp3",
            coverAsset = "images/audio/lpbd_nikola_tesla.webp",
            description = "Biografi tokoh yang memadukan fakta sains dan alur cerita untuk " +
                "memperkenalkan penemu legendaris kepada pembaca muda.",
            tags = listOf("Biografi Tokoh", "Sains"),
        ),
        AudioTrack(
            id = "track_04",
            title = "LPBD: Charles Darwin",
            category = AudioCategory.SCIENCE,
            durationSec = 205,
            assetUrl = "audio/LPBD_Charles_Darwin.mp3",
            coverAsset = "images/audio/lpbd_charles_darwin.webp",
            description = "Perjalanan evolusi dan alam disajikan sebagai narasi edukatif yang " +
                "memantik rasa ingin tahu akan dunia sekitar.",
            tags = listOf("Biografi", "Evolusi & Alam"),
        ),
        AudioTrack(
            id = "track_05",
            title = "LPBD: Hans Christian Andersen",
            category = AudioCategory.STORY,
            durationSec = 228,
            assetUrl = "audio/AudioBook_LPBD_Hans_Christian_Andersen.mp3",
            coverAsset = "images/audio/lpbd_hans_christian_andersen.webp",
            description = "Kisah hidup sastrawan dongeng terkenal dinuturkan dengan kehangatan " +
                "dan ritme yang memikat anak-anak.",
            tags = listOf("Biografi Sastrawan", "Cerita"),
        ),
        AudioTrack(
            id = "track_06",
            title = "Aku Bisa Jadi Apa: Hobiku Bertualang",
            category = AudioCategory.STORY,
            durationSec = 210,
            assetUrl = "audio/Audiobook_Aku_Bisa_Jadi_Apa_Hobiku_Bertualang.mp3",
            coverAsset = "images/audio/abja_hobiku_bertualang.webp",
            description = "Petualangan imajinatif tentang profesi dan hobi, mengajak anak membayangkan " +
                "masa depan mereka dengan gembira.",
            tags = listOf("Petualangan", "Eksplorasi"),
        ),
        AudioTrack(
            id = "track_07",
            title = "Announcement: Rimba Kata",
            category = AudioCategory.CHARACTER_VO,
            durationSec = 96,
            assetUrl = "audio/Announcement_Rimba_Kata.mp3",
            coverAsset = "images/audio/rimba_kata.webp",
            description = "Voice over pengumuman dengan karakter vokal tegas dan ramah untuk " +
                "kebutuhan acara, kampanye, dan materi edukasi.",
            tags = listOf("Voice Over", "Karakter"),
        ),
    )

    private val books: List<Book> = listOf(
        Book(
            id = "book_01",
            title = "Ensiklopedia Cilik Nusantara: Hewan Ternak",
            publisher = Publisher.BIP,
            category = BookCategory.ENSIKLOPEDIA,
            ageRange = AgeRange.AGE_4_8,
            roles = listOf(EditorRole.DEVELOPMENTAL_EDITOR),
            coverAsset = "images/projects/books/Ensiklopedia_Cilik_Nusantara_Hewan_Ternak.webp",
            year = 2023,
            synopsis = "Ensiklopedia bergambar tentang hewan ternak di Nusantara, disajikan dengan kosakata ramah anak dan fakta singkat yang mudah diingat.",
        ),
        Book(
            id = "book_02",
            title = "Ensiklopedia Anak Hebat: Tumbuhan",
            publisher = Publisher.BIP,
            category = BookCategory.ENSIKLOPEDIA,
            ageRange = AgeRange.AGE_7_12,
            roles = listOf(EditorRole.STEM_FACT_CHECKER, EditorRole.DEVELOPMENTAL_EDITOR),
            coverAsset = "images/projects/books/Ensiklopedia_Anak_Hebat_Tumbuhan.webp",
            year = 2023,
            synopsis = "Menjelajahi dunia tumbuhan lewat ilustrasi dan penjelasan tentang pertumbuhan, bagian tanaman, dan perannya dalam kehidupan sehari-hari.",
        ),
        Book(
            id = "book_03",
            title = "5 Menit Mengenal Laut Fantastis",
            publisher = Publisher.GRAMEDIA,
            category = BookCategory.SAINS,
            ageRange = AgeRange.AGE_4_8,
            roles = listOf(EditorRole.STEM_FACT_CHECKER),
            coverAsset = "images/projects/books/5_Menit_Mengenal_Laut_Fantastis.webp",
            year = 2022,
            synopsis = "Perjalanan singkat ke bawah laut yang memperkenalkan makhluk laut dan ekosistemnya lewat bahasa sederhana dan ilustrasi ceria.",
        ),
        Book(
            id = "book_04",
            title = "Ensiklopedia Cilik Nusantara: Bandara",
            publisher = Publisher.BIP,
            category = BookCategory.ENSIKLOPEDIA,
            ageRange = AgeRange.AGE_4_8,
            roles = listOf(EditorRole.DEVELOPMENTAL_EDITOR, EditorRole.LANGUAGE_CONSULTANT),
            coverAsset = "images/projects/books/Ensiklopedia_Cilik_Nusantara_Bandara.webp",
            year = 2024,
            synopsis = "Mengenal bandara, pesawat, dan proses perjalanan udara melalui gambar serta penjelasan yang sesuai untuk pembaca usia dini.",
        ),
        Book(
            id = "book_05",
            title = "The Empathy Effect",
            publisher = Publisher.GRAMEDIA,
            category = BookCategory.BUKU_CERITA,
            ageRange = AgeRange.AGE_7_12,
            roles = listOf(EditorRole.DEVELOPMENTAL_EDITOR),
            coverAsset = "images/projects/books/The_Empathy_Effect.webp",
            year = 2024,
            synopsis = "Sebuah cerita tentang empati dan keberanian memahami perasaan orang lain, dikemas dengan ritme yang hangat dan dekat dengan anak.",
        ),
        Book(
            id = "book_06",
            title = "Brain Power",
            publisher = Publisher.GRAMEDIA,
            category = BookCategory.SAINS,
            ageRange = AgeRange.AGE_7_12,
            roles = listOf(EditorRole.STEM_FACT_CHECKER),
            coverAsset = "images/projects/books/Brain_Power.webp",
            year = 2023,
            synopsis = "Mengajak anak memahami cara kerja otak, ingatan, dan konsentrasi melalui analogi yang akrab dengan keseharian mereka.",
        ),
        Book(
            id = "book_07",
            title = "Ensiklopedia Cilik Nusantara: Gunung Api",
            publisher = Publisher.BIP,
            category = BookCategory.ENSIKLOPEDIA,
            ageRange = AgeRange.AGE_7_12,
            roles = listOf(EditorRole.STEM_FACT_CHECKER),
            coverAsset = "images/projects/books/Ensiklopedia_Cilik_Nusantara_Gunung_Api.webp",
            year = 2023,
            synopsis = "Fakta geologi gunung api disederhanakan menjadi penjelasan yang jelas tentang letusan, lapisan bumi, dan manfaatnya bagi lingkungan.",
        ),
        Book(
            id = "book_08",
            title = "Hiu, Sang Predator Lautan",
            publisher = Publisher.ERLANGGA,
            category = BookCategory.SAINS,
            ageRange = AgeRange.AGE_7_12,
            roles = listOf(EditorRole.STEM_FACT_CHECKER),
            coverAsset = "images/projects/books/Hiu,_Sang_Predator_Lautan.webp",
            year = 2023,
            synopsis = "Mengenal keanekaragaman hiu, perilaku berburu, dan peran pentingnya dalam menjaga keseimbangan ekosistem laut.",
        ),
        Book(
            id = "book_09",
            title = "Ensiklopedia Aku Ingin Tahu: Alam Sekitar",
            publisher = Publisher.MIZAN,
            category = BookCategory.ENSIKLOPEDIA,
            ageRange = AgeRange.AGE_4_8,
            roles = listOf(EditorRole.DEVELOPMENTAL_EDITOR),
            coverAsset = "images/projects/books/Ensiklopedia_Aku_Ingin_Tahu_Alam_Sekitar.webp",
            year = 2022,
            synopsis = "Rangkaian fakta alam sekitar—cuaca, musim, dan makhluk di sekitar rumah—yang memantik rasa ingin tahu pembaca muda.",
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
            photoAsset = "images/projects/events/MC_Ulang_Tahun_Penerbit_BIP_32.webp",
            tags = listOf("Manajemen Audiens Anak", "Mendongeng Interaktif", "Bilingual ID/EN"),
            role = "Pemandu Panggung Utama & Pencerita",
            description = "Memandu perayaan ulang tahun penerbit dengan sesi mendongeng interaktif, peluncuran buku anak, dan kuis panggung yang penuh keceriaan.",
        ),
        McEvent(
            id = "event_02",
            eventName = "Moderator Islamic Book Fair — Peluncuran 30 Kisah Nabi",
            date = "2023-03",
            venue = "Islamic Book Fair",
            city = "Jakarta",
            estimatedAttendees = 500,
            photoAsset = "images/projects/events/Moderator_Islamic_Book_Fair_Launching_Buku_30_Kisah_Nabi_Muhammad_SAW.webp",
            tags = listOf("Fasilitasi Panel", "Peluncuran Buku", "Bilingual ID/EN"),
            role = "Moderator Diskusi Panel",
            description = "Memfasilitasi diskusi peluncuran buku dengan transisi yang hangat, pertanyaan yang memantik rasa ingin tahu, dan ringkasan ide yang mudah dicerna pembaca muda.",
        ),
        McEvent(
            id = "event_03",
            eventName = "MC Dongeng & Aktivitas Seru — MB Fair",
            date = "2023-08",
            venue = "Mall Book Festival",
            city = "Jakarta",
            estimatedAttendees = 450,
            photoAsset = "images/projects/events/MC_Dongeng_&_Aktivitas_Seru_MB_Fair.webp",
            tags = listOf("MC", "Anak-anak"),
        ),
        McEvent(
            id = "event_04",
            eventName = "Host Live Shopee — Buku-buku Anak Gramedia",
            date = "2023-11",
            venue = "Shopee Live Studio",
            city = "Jakarta",
            estimatedAttendees = 1200,
            photoAsset = "images/projects/events/Host_Live_Shopee_Buku-buku_Anak_Gramedia.webp",
            tags = listOf("Live Hosting", "Gramedia"),
        ),
        McEvent(
            id = "event_05",
            eventName = "Moderator IIBF Book Talk — Ensiklopedia Cilik Nusantara",
            date = "2024-04",
            venue = "Indonesia International Book Fair",
            city = "Jakarta",
            estimatedAttendees = 400,
            photoAsset = "images/projects/events/Moderator_IIBF_Book_Talk_Ensiklopedia_Cilik_Nusantara.webp",
            tags = listOf("Moderator", "Science Expo"),
        ),
        McEvent(
            id = "event_06",
            eventName = "Host Book Talk — Novel Gerbang Dua Semesta",
            date = "2024-09",
            venue = "Gramedia Book Store",
            city = "Jakarta",
            estimatedAttendees = 250,
            photoAsset = "images/projects/events/Host_Book_Talk_Novel_Gerbang_Dua_Semesta.webp",
            tags = listOf("Book Talk", "MC"),
        ),
        McEvent(
            id = "event_07",
            eventName = "MC Ulang Tahun Penerbit ELEX MEDIA ke-40",
            date = "2024-01",
            venue = "Gedung Kompas Gramedia",
            city = "Jakarta",
            estimatedAttendees = 350,
            photoAsset = "images/projects/events/MC_Ulang_Tahun_Penerbit_ELEX_MEDIA_40th.webp",
            tags = listOf("MC", "Elex Media"),
        ),
        McEvent(
            id = "event_08",
            eventName = "Host Live Shopee — Buku Ensiklopedia & Sains",
            date = "2024-03",
            venue = "Shopee Live Studio",
            city = "Jakarta",
            estimatedAttendees = 1500,
            photoAsset = "images/projects/events/Host_Live_Shopee_Buku-Buku_Ensiklopedia_&_Sains.webp",
            tags = listOf("Live Hosting", "Sains"),
        ),
        McEvent(
            id = "event_09",
            eventName = "Moderator Parent Talks — Launching Rumah Uma Bintaro",
            date = "2024-06",
            venue = "Rumah Uma Bintaro",
            city = "Tangerang Selatan",
            estimatedAttendees = 200,
            photoAsset = "images/projects/events/Moderator_Parent_Talks__Launching_Rumah_Uma_Bintaro.webp",
            tags = listOf("Moderator", "Parenting"),
        ),
    )

    private val metrics = PortfolioMetrics.DEFAULT

    override fun getAudioTracks(): List<AudioTrack> = audioTracks

    override fun getBooks(): List<Book> = books

    override fun getMcEvents(): List<McEvent> = mcEvents

    override fun getMetrics(): PortfolioMetrics = metrics

    override fun getTracksByCategory(category: AudioCategory): List<AudioTrack> =
        if (category == AudioCategory.ALL) audioTracks
        else audioTracks.filter { it.category == category }
}
