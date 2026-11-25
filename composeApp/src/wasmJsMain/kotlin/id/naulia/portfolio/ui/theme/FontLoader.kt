package id.naulia.portfolio.ui.theme

import kotlinx.browser.document

private const val LUMINA_FONTS_HREF =
    "https://fonts.googleapis.com/css2?family=Hanken+Grotesk:wght@400;500;600;700&family=Outfit:wght@300;400;500;600;700&display=swap"
private const val FONT_LINK_SELECTOR =
    "link[data-naulia-font='lumina'], link[href*='family=Hanken+Grotesk']"

actual fun injectLuminaFonts() {
    if (document.querySelector(FONT_LINK_SELECTOR) != null) return

    val link = document.createElement("link")
    link.setAttribute("rel", "stylesheet")
    link.setAttribute("href", LUMINA_FONTS_HREF)
    link.setAttribute("data-naulia-font", "lumina")
    document.head?.appendChild(link) ?: document.body?.appendChild(link)
}
