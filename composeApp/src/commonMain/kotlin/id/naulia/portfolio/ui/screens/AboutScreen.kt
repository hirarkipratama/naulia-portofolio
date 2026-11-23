package id.naulia.portfolio.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.font.FontStyle
import id.naulia.portfolio.ui.components.CapabilityCard
import id.naulia.portfolio.ui.components.CapabilityTone
import id.naulia.portfolio.ui.components.PillTone
import id.naulia.portfolio.ui.components.SectionEyebrow
import id.naulia.portfolio.ui.icons.LuminaIcons
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.rememberIsMobile

@Composable
fun AboutScreen(
    modifier: Modifier = Modifier,
) {
    val isMobile = rememberIsMobile()
    var isEnglish by remember { mutableStateOf(false) }
    val copy = if (isEnglish) BioStrings.english else BioStrings.bahasa

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LuminaDimens.margin.mobile,
                vertical = LuminaDimens.spacing.xxl,
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            SectionEyebrow(
                text = BioStrings.EYEBROW,
                icon = LuminaIcons.Idea,
                tone = PillTone.Neutral,
            )
            LanguageToggle(
                isEnglish = isEnglish,
                onToggle = { isEnglish = !isEnglish },
            )
        }

        Spacer(Modifier.height(LuminaDimens.spacing.md))
        if (isMobile) {
            NarrativeColumn(copy = copy)
            Spacer(Modifier.height(LuminaDimens.spacing.xl))
            CapabilityGrid(capabilities = copy.capabilities)
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xl),
            ) {
                NarrativeColumn(
                    copy = copy,
                    modifier = Modifier.weight(NARRATIVE_WEIGHT),
                )
                CapabilityGrid(
                    capabilities = copy.capabilities,
                    modifier = Modifier.weight(CAPABILITY_WEIGHT),
                )
            }
        }
    }
}

@Composable
private fun NarrativeColumn(
    copy: BioCopy,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    Column(modifier = modifier) {
        Text(
            text = copy.title,
            style = type.headlineLg,
            color = colors.onSurface,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        copy.narrativeParagraphs.forEach { paragraph ->
            Text(
                text = paragraph,
                style = type.bodyMd,
                color = colors.onSurfaceVariant,
            )
            Spacer(Modifier.height(LuminaDimens.spacing.sm))
        }
        Spacer(Modifier.height(LuminaDimens.spacing.xs))
        QuoteBlock(quote = copy.quote, attribution = copy.attribution)
    }
}

@Composable
private fun QuoteBlock(
    quote: String,
    attribution: String,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(LuminaDimens.radius.lg),
        color = colors.secondaryContainer.copy(alpha = QUOTE_CONTAINER_ALPHA),
    ) {
        Row(
            modifier = Modifier
                .padding(LuminaDimens.spacing.md)
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.md),
        ) {
            Box(
                modifier = Modifier
                    .width(LuminaDimens.size.cardAccent)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(LuminaDimens.radius.sm))
                    .background(colors.secondary),
            )
            Column {
                Text(
                    text = "$QUOTE_MARK$quote",
                    style = type.bodyMd.copy(fontStyle = FontStyle.Italic),
                    color = colors.onSurface,
                )
                Spacer(Modifier.height(LuminaDimens.spacing.xs))
                Text(
                    text = attribution,
                    style = type.labelSm,
                    color = colors.secondary,
                )
            }
        }
    }
}

@Composable
private fun CapabilityGrid(
    capabilities: List<BioCapability>,
    modifier: Modifier = Modifier,
) {
    val isMobile = rememberIsMobile()
    Column(modifier = modifier) {
        if (isMobile) {
            capabilities.forEachIndexed { index, capability ->
                CapabilityCard(
                    icon = capability.icon,
                    title = capability.label,
                    description = capability.description,
                    tone = CapabilityCardStrings.tone(index),
                )
                Spacer(Modifier.height(LuminaDimens.spacing.sm))
            }
        } else {
            capabilities.chunked(CAPABILITY_COLUMNS).forEachIndexed { rowIndex, row ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
                ) {
                    row.forEachIndexed { columnIndex, capability ->
                        CapabilityCard(
                            icon = capability.icon,
                            title = capability.label,
                            description = capability.description,
                            tone = CapabilityCardStrings.tone(rowIndex * CAPABILITY_COLUMNS + columnIndex),
                            modifier = Modifier.weight(CAPABILITY_WEIGHT),
                        )
                    }
                }
                Spacer(Modifier.height(LuminaDimens.spacing.sm))
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
    val type = LocalLuminaTypeScale.current
    Surface(
        modifier = modifier
            .clickable { onToggle() }
            .semantics {
                role = Role.Button
                contentDescription = BioStrings.TOGGLE_DESCRIPTION
                stateDescription = if (isEnglish) {
                    BioStrings.TOGGLE_STATE_ENGLISH
                } else {
                    BioStrings.TOGGLE_STATE_BAHASA
                }
            },
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
        color = colors.surfaceContainerLow,
        border = BorderStroke(LuminaDimens.size.thinBorder, colors.outlineVariant),
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = LuminaDimens.spacing.sm,
                vertical = LuminaDimens.spacing.xs,
            ),
        ) {
            Text(
                text = BioStrings.TOGGLE_BAHASA,
                style = type.labelSm,
                color = if (isEnglish) colors.onSurfaceVariant else colors.primary,
            )
            Text(
                text = BioStrings.TOGGLE_SEPARATOR,
                style = type.labelSm,
                color = colors.onSurfaceVariant,
            )
            Text(
                text = BioStrings.TOGGLE_ENGLISH,
                style = type.labelSm,
                color = if (isEnglish) colors.primary else colors.onSurfaceVariant,
            )
        }
    }
}

private const val QUOTE_MARK = "“"
private const val QUOTE_CONTAINER_ALPHA = 0.2f
private const val NARRATIVE_WEIGHT = 5f
private const val CAPABILITY_WEIGHT = 7f
private const val CAPABILITY_COLUMNS = 2
private val CAPABILITY_TONES = listOf(
    CapabilityTone.Primary,
    CapabilityTone.Secondary,
    CapabilityTone.Tertiary,
)

private object CapabilityCardStrings {
    fun tone(index: Int): CapabilityTone = CAPABILITY_TONES[index % CAPABILITY_TONES.size]
}
