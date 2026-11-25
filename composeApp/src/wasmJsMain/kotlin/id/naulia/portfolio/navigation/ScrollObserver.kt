package id.naulia.portfolio.navigation

import id.naulia.portfolio.platform.prefersReducedMotion
import kotlin.JsFun
import kotlinx.browser.document
import kotlinx.browser.window

@JsFun("""(idsJson, onHashChange) => {
  const ids = JSON.parse(idsJson);
  let container = document.getElementById('__naulia_section_anchors__');
  if (!container) {
    container = document.createElement('div');
    container.id = '__naulia_section_anchors__';
    container.setAttribute('aria-hidden', 'true');
    container.style.position = 'absolute';
    container.style.top = '0';
    container.style.left = '0';
    container.style.width = '1px';
    container.style.height = '1px';
    container.style.overflow = 'hidden';
    document.body.appendChild(container);
  }
  ids.forEach((id) => {
    if (document.getElementById(id)) return;
    const anchor = document.createElement('a');
    anchor.id = id;
    anchor.href = '#' + id;
    anchor.tabIndex = -1;
    anchor.style.position = 'absolute';
    anchor.style.width = '1px';
    anchor.style.height = '1px';
    anchor.style.opacity = '0';
    anchor.style.pointerEvents = 'none';
    container.appendChild(anchor);
  });
  const readHash = () => window.location.hash.replace(/^#/, '');
  window.addEventListener('hashchange', () => {
    const id = readHash();
    if (id) onHashChange(id);
  });
  return readHash();
}""")
private external fun installSectionAnchors(
    idsJson: String,
    onHashChange: (String) -> Unit,
): String

@JsFun("(id) => { const element = document.getElementById(id); if (element) element.scrollIntoView({ behavior: 'smooth', block: 'start' }); }")
private external fun scrollIntoViewSmooth(id: String)

@JsFun("(id) => { const element = document.getElementById(id); if (element) element.scrollIntoView({ behavior: 'auto', block: 'start' }); }")
private external fun scrollIntoViewInstant(id: String)

@JsFun("(id) => { const hash = '#' + id; if (window.location.hash !== hash) history.replaceState(null, '', hash); }")
private external fun replaceSectionHash(id: String)

actual fun smoothScrollToSection(sectionId: String) {
    if (prefersReducedMotion()) {
        scrollIntoViewInstant(sectionId)
    } else {
        scrollIntoViewSmooth(sectionId)
    }
}

actual fun syncSectionHash(sectionId: String) {
    replaceSectionHash(sectionId)
}

actual fun setupScrollObserver(navigator: Navigator) {
    val idsJson = NavSection.all.joinToString(prefix = "[", postfix = "]") { "\"${it.sectionId}\"" }
    val initialSectionId = installSectionAnchors(idsJson) { sectionId ->
        NavSection.fromSectionId(sectionId)?.let { section -> navigator.navigateTo(section) }
    }
    NavSection.fromSectionId(initialSectionId)?.let { section -> navigator.navigateTo(section) }
}
