package id.naulia.portfolio.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import id.naulia.portfolio.platform.prefersReducedMotion

class Navigator(
    private val scrollCoordinator: SectionScrollCoordinator? = null,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val _activeSection = MutableStateFlow<NavSection>(NavSection.Home)
    val activeSection: StateFlow<NavSection> = _activeSection.asStateFlow()

    fun navigateTo(section: NavSection) {
        _activeSection.update { section }
        syncSectionHash(section.sectionId)
        val coordinator = scrollCoordinator
        if (coordinator != null) {
            scope.launch {
                val startOffset = coordinator.scrollState.value
                val targetOffset = coordinator.offsetFor(section.sectionId)
                if (prefersReducedMotion()) {
                    coordinator.scrollState.scrollTo(targetOffset)
                    return@launch
                }
                for (step in 1..SCROLL_STEPS) {
                    val linearProgress = step / SCROLL_STEPS.toFloat()
                    val easedProgress = linearProgress * linearProgress * (EASING_FACTOR - EASING_MULTIPLIER * linearProgress)
                    val nextOffset = startOffset + (targetOffset - startOffset) * easedProgress
                    coordinator.scrollState.scrollTo(nextOffset.toInt())
                    delay(SCROLL_STEP_DELAY_MS)
                }
            }
        } else {
            smoothScrollToSection(section.sectionId)
        }
    }

    fun updateActiveSectionFromScroll(sectionId: String) {
        NavSection.all
            .firstOrNull { it.sectionId == sectionId }
            ?.let { section -> _activeSection.update { section } }
    }

    fun onScrollPositionChanged(scrollPositionPx: Int) {
        scrollCoordinator?.activeSectionAt(scrollPositionPx)?.let { section ->
            _activeSection.update { section }
        }
    }

    fun onCleared() {
        scope.cancel()
    }

    companion object {
        private const val SCROLL_STEPS = 20
        private const val SCROLL_STEP_DELAY_MS = 16L
        private const val EASING_FACTOR = 3f
        private const val EASING_MULTIPLIER = 2f
    }
}
