package id.naulia.portfolio.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale

enum class MetricTone { Neutral, Coral, Sun, Mint }

@Composable
fun MetricCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    supportingText: String? = null,
    tone: MetricTone = MetricTone.Neutral,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val containerColor = when (tone) {
        MetricTone.Neutral, MetricTone.Coral -> colors.surfaceContainerLowest
        MetricTone.Sun -> colors.secondaryContainer.copy(alpha = METRIC_CONTAINER_ALPHA)
        MetricTone.Mint -> colors.tertiaryFixed.copy(alpha = METRIC_CONTAINER_ALPHA)
    }
    val valueColor = when (tone) {
        MetricTone.Neutral -> colors.onSurface
        MetricTone.Coral -> colors.primary
        MetricTone.Sun -> colors.secondary
        MetricTone.Mint -> colors.tertiary
    }

    LuminaCard(
        modifier = modifier.semantics {
            contentDescription = MetricCardStrings.description(value, label, supportingText)
        },
        containerColor = containerColor,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xs),
        ) {
            Text(
                text = value,
                style = type.headlineLg,
                color = valueColor,
                textAlign = TextAlign.Center,
            )
            Text(
                text = label,
                style = type.labelMd,
                color = colors.onSurface,
                textAlign = TextAlign.Center,
            )
            if (supportingText != null) {
                Text(
                    text = supportingText,
                    style = type.bodySm,
                    color = colors.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

private const val METRIC_CONTAINER_ALPHA = 0.35f

private object MetricCardStrings {
    fun description(value: String, label: String, supportingText: String?): String =
        listOfNotNull(value, label, supportingText).joinToString(separator = " ")
}
