package id.naulia.portfolio.ui.theme

import androidx.compose.ui.unit.dp

/**
 * Curated Lumina size tokens.
 *
 * Source of truth: `reference/DESIGN.md` frontmatter (spacing, radius, elevation scale).
 * Legacy member names (`spacing`, `cornerRadius`, `size`) are kept as aliases so existing
 * screens can migrate without a flag day.
 */
object LuminaDimens {
    object spacing {
        val xs = 6.dp
        val sm = 12.dp
        val md = 20.dp
        val lg = 32.dp
        val xl = 56.dp
        val xxl = 80.dp
    }

    object gutter {
        val mobile = 16.dp
        val desktop = 24.dp
    }

    object margin {
        val mobile = 20.dp
        val desktop = 48.dp
    }

    object radius {
        val sm = 4.dp
        val base = 8.dp
        val md = 12.dp
        val lg = 16.dp
        val xl = 24.dp
        val pill = 9999.dp
    }

    object cornerRadius {
        val card = radius.xl
        val pill = radius.pill
        val button = radius.base
        val media = radius.lg
    }

    object elevation {
        val level0 = 0.dp
        val level1 = 2.dp
        val level2 = 8.dp
        val level3 = 16.dp
    }

    object motion {
        val hoverLift = 3.dp
        const val hoverScale = 1.02f
    }

    object size {
        val topBarHeight = 64.dp
        val bottomBarHeight = 64.dp
        val cardElevation = elevation.level1
        val barElevation = elevation.level2
        val heroMinHeight = 640.dp
        val portraitWidth = 320.dp
        val portraitHeight = 380.dp
        val playButton = 44.dp
        val iconButton = 44.dp
        val inputHeight = 52.dp
        val buttonHeight = 48.dp
        val buttonHorizontalPadding = 28.dp
        val buttonVerticalPadding = 14.dp
        val buttonIcon = 18.dp
        val chipDot = 7.dp
        val chipIcon = 16.dp
        val eyebrowHorizontalPadding = 14.dp
        val eyebrowVerticalPadding = 4.dp
        val iconButtonGlyph = 20.dp
        val tabIcon = 20.dp
        val tabLabelGap = 2.dp
        val cardAccent = 4.dp
        val equalizerBarWidth = 4.dp
        val equalizerBarMinHeight = 8.dp
        val equalizerBarMidHeight = 20.dp
        val equalizerBarMaxHeight = 32.dp
        val thinBorder = 1.dp
        val accentBorder = 1.5.dp
        val scrubberHeight = 20.dp
    }

    object Breakpoints {
        val mobile = 480.dp
        val tablet = 768.dp
        val desktop = 1280.dp
    }

    val maxContentWidth = 1280.dp
}
