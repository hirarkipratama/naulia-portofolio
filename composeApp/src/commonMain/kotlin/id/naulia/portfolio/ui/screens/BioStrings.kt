package id.naulia.portfolio.ui.screens

import androidx.compose.ui.graphics.vector.ImageVector
import id.naulia.portfolio.ui.icons.LuminaIcons

data class BioCapability(val icon: ImageVector, val label: String)

data class BioCopy(
    val missionQuote: String,
    val attribution: String,
    val philosophyHeading: String,
    val philosophyParagraphs: List<String>,
    val stageHeading: String,
    val stageParagraphs: List<String>,
    val capabilities: List<BioCapability>,
)

object BioStrings {
    const val QUOTE_MARK = "“"
    const val TOGGLE_BAHASA = "ID"
    const val TOGGLE_ENGLISH = "EN"
    const val TOGGLE_SEPARATOR = " | "
    const val TOGGLE_DESCRIPTION = "Alihkan bahasa konten antara Bahasa Indonesia dan English"
    const val EYEBROW = "FILOSOFI EDITORIAL"
    const val TITLE_BAHASA = "Filosofi & Keahlian"
    const val TITLE_ENGLISH = "Philosophy & Expertise"
    const val SUBTITLE_BAHASA = "Kata, panggung, dan suara dalam satu perjalanan kreatif"
    const val SUBTITLE_ENGLISH = "Words, stage, and voice in one creative journey"
    const val CAPABILITIES_BAHASA = "Keahlian Inti"
    const val CAPABILITIES_ENGLISH = "Core Capabilities"

    private val capabilities = listOf(
        BioCapability(LuminaIcons.Pen, "Developmental Editing"),
        BioCapability(LuminaIcons.Science, "STEM Science Simplification"),
        BioCapability(LuminaIcons.TheaterMasks, "Character Voice Acting"),
        BioCapability(LuminaIcons.Mic, "Live Stage Hosting"),
        BioCapability(LuminaIcons.Child, "Children's Age-Grade Specialist"),
        BioCapability(LuminaIcons.Globe, "Bilingual (ID/EN) Moderation"),
    )

    val bahasa = BioCopy(
        missionQuote = "Setiap kata yang saya edit, setiap acara yang saya pandu, dan setiap suara yang saya rekam adalah undangan untuk anak-anak Indonesia mencintai ilmu pengetahuan.",
        attribution = "— Naulia Zahra",
        philosophyHeading = "Filosofi Editorial",
        philosophyParagraphs = listOf(
            "Saya percaya buku anak yang baik tidak pernah meremehkan rasa ingin tahu pembaca mudanya. Setiap naskah saya dekati dengan mempertimbangkan usia, kosakata, dan cara anak memahami dunia.",
            "Untuk buku STEM, saya bekerja memastikan fakta tetap akurat tanpa kehilangan kehangatan cerita. Kompleksitas diterjemahkan menjadi kalimat yang jernih, analogi yang akrab, dan ritme yang menyenangkan.",
        ),
        stageHeading = "Panggung & Suara",
        stageParagraphs = listOf(
            "Di panggung, saya membangun energi ruangan lewat bahasa yang inklusif, transisi yang lembut, dan kemampuan membaca audiens secara real-time.",
            "Di studio, saya memperlakukan setiap karakter sebagai individu—dari ekspresi, napas, hingga warna suara—sehingga narasi tidak hanya terdengar, tetapi juga terasa.",
        ),
        capabilities = capabilities,
    )

    val english = BioCopy(
        missionQuote = "Every word I edit, every event I host, and every voice I record is an invitation for Indonesian children to fall in love with knowledge.",
        attribution = "— Naulia Zahra",
        philosophyHeading = "Editorial Philosophy",
        philosophyParagraphs = listOf(
            "I believe a good children's book never underestimates a young reader's curiosity. Each manuscript is approached with careful attention to age, vocabulary, and the way children make sense of the world.",
            "For STEM titles, I make sure facts stay accurate without losing the warmth of storytelling. Complexity becomes clear sentences, familiar analogies, and a rhythm that feels joyful.",
        ),
        stageHeading = "Stage & Voice",
        stageParagraphs = listOf(
            "On stage, I build the room's energy through inclusive language, gentle transitions, and a real-time sensitivity to the audience.",
            "In the studio, I treat every character as an individual—from expression and breath to vocal color—so a narration is not only heard, but felt.",
        ),
        capabilities = capabilities,
    )
}
