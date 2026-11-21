package id.naulia.portfolio.navigation

sealed class NavSection(
    val sectionId: String,
    val labelBahasa: String,
    val labelEnglish: String,
    val icon: String,
) {
    object Home : NavSection("home", "Beranda", "Home", "🏠")
    object Audio : NavSection("featured-audiobook", "Buku Suara", "Audio", "🎙️")
    object Books : NavSection("editorial-works", "Buku", "Books", "📚")
    object Stage : NavSection("mc-stage", "Acara", "Events", "🎤")
    object About : NavSection("about", "Profil", "Profile", "👤")
    object Contact : NavSection("contact", "Kontak", "Contact", "✉️")

    companion object {
        val all: List<NavSection> = listOf(Home, Audio, Books, Stage, About, Contact)
    }
}
