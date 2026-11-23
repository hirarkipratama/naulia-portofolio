package id.naulia.portfolio

import kotlinx.browser.document

private const val GLOBAL_STYLE_SELECTOR = "style[data-naulia-global-styles]"

private val GLOBAL_STYLES = """
* { box-sizing: border-box; margin: 0; }
html { scroll-behavior: smooth; font-family: 'Hanken Grotesk', 'Outfit', sans-serif; scrollbar-width: none; -ms-overflow-style: none; }
html, body { overflow: hidden; }
:focus-visible { outline: 2px solid #9D422C; outline-offset: 2px; }
@media (prefers-reduced-motion: reduce) {
  *, *::before, *::after { animation-duration: 0.001ms !important; transition-duration: 0.001ms !important; }
}
::-webkit-scrollbar { display: none; width: 0; height: 0; }
""".trimIndent()

fun injectGlobalStyles() {
    if (document.querySelector(GLOBAL_STYLE_SELECTOR) != null) return

    val styleElement = document.createElement("style")
    styleElement.setAttribute("data-naulia-global-styles", "true")
    styleElement.innerHTML = GLOBAL_STYLES
    document.head?.appendChild(styleElement) ?: document.body?.appendChild(styleElement)
}
