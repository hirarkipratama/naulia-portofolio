package id.naulia.portfolio.ui.theme

import kotlinx.browser.document

private const val OUTFIT_FONT_HREF =
    "https://fonts.googleapis.com/css2?family=Outfit:wght@300;400;500;600;700&display=swap"
private const val FONT_LINK_SELECTOR = "link[data-naulia-font='outfit']"

actual fun injectOutfitFont() {
    if (document.querySelector(FONT_LINK_SELECTOR) != null) return

    val link = document.createElement("link")
    link.setAttribute("rel", "stylesheet")
    link.setAttribute("href", OUTFIT_FONT_HREF)
    link.setAttribute("data-naulia-font", "outfit")
    document.head?.appendChild(link) ?: document.body?.appendChild(link)
}
