package id.naulia.portfolio.navigation

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class Navigator {
    private val _activeSection = MutableStateFlow<NavSection>(NavSection.Home)
    val activeSection: StateFlow<NavSection> = _activeSection.asStateFlow()

    fun navigateTo(section: NavSection) {
        _activeSection.update { section }
        smoothScrollToSection(section.sectionId)
    }

    fun updateActiveSectionFromScroll(sectionId: String) {
        NavSection.all
            .firstOrNull { it.sectionId == sectionId }
            ?.let { section -> _activeSection.update { section } }
    }
}
