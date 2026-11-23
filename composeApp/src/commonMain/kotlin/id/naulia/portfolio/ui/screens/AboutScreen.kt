package id.naulia.portfolio.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import id.naulia.portfolio.ui.components.SectionHeader
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.rememberIsMobile

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val isMobile = rememberIsMobile()
    var isEnglish by remember { mutableStateOf(false) }
    val copy = if (isEnglish) BioStrings.english else BioStrings.bahasa

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LuminaDimens.spacing.xl,
                vertical = LuminaDimens.spacing.xxl,
            ),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            SectionHeader(
                title = if (isEnglish) BioStrings.TITLE_ENGLISH else BioStrings.TITLE_BAHASA,
                subtitle = if (isEnglish) BioStrings.SUBTITLE_ENGLISH else BioStrings.SUBTITLE_BAHASA,
                modifier = Modifier.weight(LANGUAGE_HEADER_WEIGHT),
            )
            LanguageToggle(
                isEnglish = isEnglish,
                onToggle = { isEnglish = !isEnglish },
            )
        }

        Surface(
            shape = RoundedCornerShape(LuminaDimens.cornerRadius.card),
            color = colors.surface,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(modifier = Modifier.padding(LuminaDimens.spacing.lg)) {
                Text(
                    text = BioStrings.QUOTE_MARK,
                    style = LocalLuminaTypeScale.current.quoteMark.copy(fontStyle = FontStyle.Italic),
                    color = colors.primary,
                )
                Text(
                    text = copy.missionQuote,
                    style = MaterialTheme.typography.headlineMedium,
                    color = colors.onBackground,
                )
                Spacer(Modifier.height(LuminaDimens.spacing.sm))
                Text(
                    text = copy.attribution,
                    style = MaterialTheme.typography.bodyLarge.copy(fontStyle = FontStyle.Italic),
                    color = colors.primary,
                )
            }
        }

        Spacer(Modifier.height(LuminaDimens.spacing.xl))
        if (isMobile) {
            PhilosophyColumn(copy.philosophyHeading, copy.philosophyParagraphs)
            Spacer(Modifier.height(LuminaDimens.spacing.lg))
            PhilosophyColumn(copy.stageHeading, copy.stageParagraphs)
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.xl),
            ) {
                PhilosophyColumn(
                    heading = copy.philosophyHeading,
                    paragraphs = copy.philosophyParagraphs,
                    modifier = Modifier.weight(NARRATIVE_WEIGHT),
                )
                PhilosophyColumn(
                    heading = copy.stageHeading,
                    paragraphs = copy.stageParagraphs,
                    modifier = Modifier.weight(NARRATIVE_WEIGHT),
                )
            }
        }

        Spacer(Modifier.height(LuminaDimens.spacing.xl))
        Text(
            text = if (isEnglish) BioStrings.CAPABILITIES_ENGLISH else BioStrings.CAPABILITIES_BAHASA,
            style = MaterialTheme.typography.headlineMedium,
            color = colors.onBackground,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.md))
        FlowRow(
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.sm),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.sm),
        ) {
            copy.capabilities.forEachIndexed { index, capability ->
                val pillColor = when (index % CAPABILITY_COLOR_COUNT) {
                    0 -> colors.primary
                    1 -> colors.secondary
                    else -> colors.tertiary
                }
                Surface(
                    shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
                    color = pillColor,
                    modifier = Modifier.semantics { contentDescription = capability.label },
                ) {
                    Row(
                        modifier = Modifier.padding(
                            horizontal = LuminaDimens.spacing.md,
                            vertical = LuminaDimens.spacing.sm,
                        ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = capability.icon,
                            contentDescription = null,
                            tint = colors.onPrimary,
                            modifier = Modifier.size(18.dp),
                        )
                        Spacer(Modifier.width(LuminaDimens.spacing.xs))
                        Text(
                            text = capability.label,
                            style = MaterialTheme.typography.labelLarge,
                            color = colors.onPrimary,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun LanguageToggle(
    isEnglish: Boolean,
    onToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    Surface(
        modifier = modifier
            .clickable { onToggle() }
            .semantics {
                role = Role.Button
                contentDescription = BioStrings.TOGGLE_DESCRIPTION
            },
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
        color = colors.surface,
        border = androidx.compose.foundation.BorderStroke(LuminaDimens.size.thinBorder, colors.outline),
    ) {
        Row(modifier = Modifier.padding(horizontal = LuminaDimens.spacing.sm, vertical = LuminaDimens.spacing.xs)) {
            Text(
                text = BioStrings.TOGGLE_BAHASA,
                style = MaterialTheme.typography.labelSmall,
                color = if (isEnglish) colors.onSurface else colors.primary,
            )
            Text(
                text = BioStrings.TOGGLE_SEPARATOR,
                style = MaterialTheme.typography.labelSmall,
                color = colors.onSurface,
            )
            Text(
                text = BioStrings.TOGGLE_ENGLISH,
                style = MaterialTheme.typography.labelSmall,
                color = if (isEnglish) colors.primary else colors.onSurface,
            )
        }
    }
}

@Composable
private fun PhilosophyColumn(
    heading: String,
    paragraphs: List<String>,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    Column(modifier = modifier) {
        Text(
            text = heading,
            style = MaterialTheme.typography.headlineMedium,
            color = colors.primary,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.md))
        paragraphs.forEach { paragraph ->
            Text(
                text = paragraph,
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onSurface,
            )
            Spacer(Modifier.height(LuminaDimens.spacing.sm))
        }
    }
}

private const val NARRATIVE_WEIGHT = 1f
private const val LANGUAGE_HEADER_WEIGHT = 1f
private const val CAPABILITY_COLOR_COUNT = 3
