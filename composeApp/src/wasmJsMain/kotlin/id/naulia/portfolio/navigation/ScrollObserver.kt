package id.naulia.portfolio.navigation

import kotlin.JsFun
import kotlinx.browser.document
import org.w3c.dom.Element

private external interface JsIntersectionObserver {
    fun observe(target: Element)
}

@JsFun("(handler) => new IntersectionObserver((entries) => handler(entries[0].isIntersecting, entries[0].target.id), { threshold: 0.4 })")
private external fun createIntersectionObserver(
    handler: (isIntersecting: Boolean, sectionId: String) -> Unit,
): JsIntersectionObserver

@JsFun("(id) => { const element = document.getElementById(id); if (element) element.scrollIntoView({ behavior: 'smooth', block: 'start' }); }")
private external fun scrollIntoViewSmooth(id: String)

actual fun smoothScrollToSection(sectionId: String) {
    scrollIntoViewSmooth(sectionId)
}

actual fun setupScrollObserver(navigator: Navigator) {
    val observer = createIntersectionObserver { isIntersecting, sectionId ->
        if (isIntersecting) {
            navigator.updateActiveSectionFromScroll(sectionId)
        }
    }

    NavSection.all.forEach { section ->
        document.getElementById(section.sectionId)?.let { element -> observer.observe(element) }
    }
}
