package id.naulia.portfolio.ui.screens

import androidx.compose.ui.graphics.vector.ImageVector
import id.naulia.portfolio.ui.icons.LuminaIcons

data class BioCapability(
    val icon: ImageVector,
    val label: String,
    val description: String,
)

data class BioCopy(
    val title: String,
    val narrativeParagraphs: List<String>,
    val quote: String,
    val attribution: String,
    val capabilities: List<BioCapability>,
)

object BioStrings {
    const val QUOTE_MARK = "“"
    const val TOGGLE_BAHASA = "ID"
    const val TOGGLE_ENGLISH = "EN"
    const val TOGGLE_SEPARATOR = " | "
    const val TOGGLE_DESCRIPTION = "Alihkan bahasa konten antara Bahasa Indonesia dan English"
    const val TOGGLE_STATE_BAHASA = "Bahasa Indonesia aktif"
    const val TOGGLE_STATE_ENGLISH = "Bahasa Inggris aktif"
    const val EYEBROW = "FILOSOFI EDITORIAL"
    const val ATTRIBUTION = "— Naulia Zahra (Editor Buku Anak & Pemandu Panggung)"

    private val capabilities = listOf(
        BioCapability(
            icon = LuminaIcons.Pen,
            label = "Penyuntingan Pengembangan Buku",
            description = "Membimbing naskah dari draf awal, ritme penuturan, alur plot, hingga penjenjangan kosakata pembaca usia 3 hingga 12 tahun.",
        ),
        BioCapability(
            icon = LuminaIcons.Science,
            label = "Penyederhanaan Materi Sains & STEM",
            description = "Menerjemahkan konsep astronomi, mikrobiologi, dan fisika ke analogi yang nyata tanpa mengurangi akurasi fakta ilmiah.",
        ),
        BioCapability(
            icon = LuminaIcons.RecordVoiceOver,
            label = "Narasi Suara Karakter Ekspresif",
            description = "Karakterisasi vokal yang kaya, kontrol tempo suara, dan narasi imersif untuk buku bergambar, animasi, dan seri siniar.",
        ),
        BioCapability(
            icon = LuminaIcons.Mic,
            label = "MC Panggung Energik & Ceria",
            description = "Pemandu acara handal untuk auditorium berskala besar, festival literasi keluarga, kuis interaktif, dan tur edukasi.",
        ),
        BioCapability(
            icon = LuminaIcons.Forum,
            label = "Moderasi Panel Interaktif Anak & Remaja",
            description = "Memfasilitasi dialog bermakna dan seru antara penulis, ilustrator, pendidik, dan inovator muda.",
        ),
        BioCapability(
            icon = LuminaIcons.Palette,
            label = "Alur Narasi & Bahasa Visual Anak",
            description = "Bekerja berdampingan dengan ilustrator untuk menyelaraskan harmoni teks-gambar dan dinamika pembalikan halaman.",
        ),
    )

    val bahasa = BioCopy(
        title = "Titik Temu Ketelitian Editorial & Kehangatan Ekspresif",
        narrativeParagraphs = listOf(
            "Berlatar belakang linguistik edukasi dan perkembangan kognitif anak, Naulia ahli mengubah konsep sains rumit menjadi narasi menyenangkan yang memantik rasa ingin tahu seumur hidup.",
            "Sebagai pemandu panggung dan pengisi suara, ia membawa kepekaan yang sama terhadap ritme emosional, menjaga audiens muda terpikat dari kalimat pembuka hingga tepuk tangan penutup.",
        ),
        quote = "Anak-anak dapat merasakan ketidaktulusan dalam sekejap. Baik dalam tulisan, modulasi suara, maupun di atas panggung, antusiasme yang tulus adalah jembatan cerita terbaik.",
        attribution = ATTRIBUTION,
        capabilities = capabilities,
    )

    val english = BioCopy(
        title = "Where Editorial Precision Meets Expressive Warmth",
        narrativeParagraphs = listOf(
            "With a background in educational linguistics and child cognitive development, Naulia turns complex science concepts into joyful narratives that spark lifelong curiosity.",
            "As a stage host and voice actor, she brings the same sensitivity to emotional rhythm, keeping young audiences captivated from the opening line to the final round of applause.",
        ),
        quote = "Children can sense insincerity in an instant. Whether in writing, vocal modulation, or on stage, genuine enthusiasm is the best bridge into a story.",
        attribution = "— Naulia Zahra (Children's Book Editor & Stage Host)",
        capabilities = listOf(
            BioCapability(
                icon = LuminaIcons.Pen,
                label = "Developmental Editing",
                description = "Guiding manuscripts from first draft through pacing, plot, and age-appropriate vocabulary for readers aged 3 to 12.",
            ),
            BioCapability(
                icon = LuminaIcons.Science,
                label = "STEM Science Simplification",
                description = "Translating astronomy, microbiology, and physics into familiar analogies without compromising scientific accuracy.",
            ),
            BioCapability(
                icon = LuminaIcons.RecordVoiceOver,
                label = "Expressive Character Narration",
                description = "Rich vocal characterization, tempo control, and immersive narration for picture books, animation, and audio series.",
            ),
            BioCapability(
                icon = LuminaIcons.Mic,
                label = "Energetic Stage Hosting",
                description = "Confident hosting for large auditoriums, family literacy festivals, interactive quizzes, and educational tours.",
            ),
            BioCapability(
                icon = LuminaIcons.Forum,
                label = "Interactive Youth Panel Moderation",
                description = "Facilitating lively, meaningful dialogue between authors, illustrators, educators, and young innovators.",
            ),
            BioCapability(
                icon = LuminaIcons.Palette,
                label = "Child Narrative & Visual Language",
                description = "Working alongside illustrators to align text-image harmony and the rhythm of page turns.",
            ),
        ),
    )
}
