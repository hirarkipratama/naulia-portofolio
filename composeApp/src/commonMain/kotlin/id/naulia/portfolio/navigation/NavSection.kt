package id.naulia.portfolio.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import id.naulia.portfolio.ui.icons.LuminaIcons

sealed class NavSection(
    val sectionId: String,
    val labelBahasa: String,
    val labelEnglish: String,
    val imageVector: ImageVector,
) {
    object Home : NavSection("home", "Beranda", "Home", LuminaIcons.Home)
    object Audio : NavSection("featured-audiobook", "Buku Suara", "Audio", LuminaIcons.Mic)
    object Books : NavSection("editorial-works", "Buku", "Books", LuminaIcons.Book)
    object Stage : NavSection("mc-stage", "Acara", "Events", LuminaIcons.Stage)
    object About : NavSection("about", "Profil", "Profile", LuminaIcons.Person)
    object Contact : NavSection("contact", "Kontak", "Contact", LuminaIcons.Mail)

    companion object {
        val all: List<NavSection> = listOf(Home, Audio, Books, Stage, About, Contact)
    }
}
