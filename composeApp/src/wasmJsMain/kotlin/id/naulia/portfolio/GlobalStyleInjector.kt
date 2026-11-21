package id.naulia.portfolio

import kotlinx.browser.document

private const val GLOBAL_STYLE_SELECTOR = "style[data-naulia-global-styles]"

private val GLOBAL_STYLES = """
* { box-sizing: border-box; margin: 0; }
html { scroll-behavior: smooth; font-family: 'Outfit', sans-serif; }
:focus-visible { outline: 2px solid #FF8E72; outline-offset: 2px; }
@media (prefers-reduced-motion: reduce) {
  *, *::before, *::after { animation-duration: 0.001ms !important; transition-duration: 0.001ms !important; }
}
::-webkit-scrollbar { width: 6px; }
::-webkit-scrollbar-track { background: #FFF8F3; }
::-webkit-scrollbar-thumb { background: #FF8E72; border-radius: 3px; }
""".trimIndent()

fun injectGlobalStyles() {
    if (document.querySelector(GLOBAL_STYLE_SELECTOR) != null) return

    val styleElement = document.createElement("style")
    styleElement.setAttribute("data-naulia-global-styles", "true")
    styleElement.innerHTML = GLOBAL_STYLES
    document.head?.appendChild(styleElement) ?: document.body?.appendChild(styleElement)
}
