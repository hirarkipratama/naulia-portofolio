package id.naulia.portfolio.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import id.naulia.portfolio.ui.icons.LuminaIcons

sealed class NavSection(
    val sectionId: String,
    val labelBahasa: String,
    val labelEnglish: String,
    val navLabelBahasa: String,
    val navLabelEnglish: String,
    val imageVector: ImageVector,
) {
    object Home : NavSection(
        sectionId = "home",
        labelBahasa = "Beranda",
        labelEnglish = "Home",
        navLabelBahasa = "Beranda",
        navLabelEnglish = "Home",
        imageVector = LuminaIcons.Home,
    )

    object Audio : NavSection(
        sectionId = "voice-audio-reel",
        labelBahasa = "Audio",
        labelEnglish = "Audio",
        navLabelBahasa = "Buku Suara & VO",
        navLabelEnglish = "Audiobooks & VO",
        imageVector = LuminaIcons.Mic,
    )

    object Books : NavSection(
        sectionId = "editorial-portfolio",
        labelBahasa = "Karya",
        labelEnglish = "Books",
        navLabelBahasa = "Karya Editorial",
        navLabelEnglish = "Editorial Works",
        imageVector = LuminaIcons.Book,
    )

    object Stage : NavSection(
        sectionId = "mc-live-stage",
        labelBahasa = "Acara",
        labelEnglish = "Events",
        navLabelBahasa = "MC & Panggung",
        navLabelEnglish = "MC & Stage",
        imageVector = LuminaIcons.Stage,
    )

    object About : NavSection(
        sectionId = "about-naulia",
        labelBahasa = "Profil",
        labelEnglish = "Profile",
        navLabelBahasa = "Tentang",
        navLabelEnglish = "About",
        imageVector = LuminaIcons.Person,
    )

    object Contact : NavSection(
        sectionId = "contact",
        labelBahasa = "Kontak",
        labelEnglish = "Contact",
        navLabelBahasa = "Kontak",
        navLabelEnglish = "Contact",
        imageVector = LuminaIcons.Mail,
    )

    companion object {
        val all: List<NavSection> = listOf(Home, Audio, Books, Stage, About, Contact)
        val primaryNav: List<NavSection> = listOf(Books, Audio, Stage, About, Contact)

        fun fromSectionId(sectionId: String): NavSection? =
            all.firstOrNull { it.sectionId == sectionId }
    }
}
