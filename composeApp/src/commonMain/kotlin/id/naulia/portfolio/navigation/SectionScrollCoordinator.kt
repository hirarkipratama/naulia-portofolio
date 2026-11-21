package id.naulia.portfolio.navigation

import androidx.compose.foundation.ScrollState

class SectionScrollCoordinator {
    val scrollState = ScrollState(INITIAL_SCROLL_POSITION)

    private val sectionOffsets = mutableMapOf<String, Int>()

    fun registerSection(sectionId: String, offsetPx: Int) {
        sectionOffsets[sectionId] = offsetPx
    }

    fun registerSectionIfAbsent(sectionId: String, offsetPx: Int) {
        if (sectionId !in sectionOffsets) {
            registerSection(sectionId, offsetPx)
        }
    }

    fun offsetFor(sectionId: String): Int = sectionOffsets[sectionId] ?: INITIAL_SCROLL_POSITION

    fun activeSectionAt(scrollPositionPx: Int): NavSection? {
        var activeSection: NavSection? = null
        var highestOffset = Int.MIN_VALUE
        NavSection.all.forEach { section ->
            val offset = sectionOffsets[section.sectionId] ?: return@forEach
            if (offset <= scrollPositionPx + ACTIVE_SECTION_TOLERANCE_PX && offset > highestOffset) {
                highestOffset = offset
                activeSection = section
            }
        }
        return activeSection
    }

    companion object {
        private const val INITIAL_SCROLL_POSITION = 0
        private const val ACTIVE_SECTION_TOLERANCE_PX = 160
    }
}
