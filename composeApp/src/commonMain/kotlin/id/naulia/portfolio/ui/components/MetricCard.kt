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

@Composable
fun MetricCard(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current

    LuminaCard(
        modifier = modifier.semantics { contentDescription = MetricCardStrings.description(value, label) },
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xs),
        ) {
            Text(
                text = value,
                style = type.headlineLg,
                color = colors.primary,
                textAlign = TextAlign.Center,
            )
            Text(
                text = label,
                style = type.labelMd,
                color = colors.onSurface,
                textAlign = TextAlign.Center,
            )
        }
    }
}

private object MetricCardStrings {
    fun description(value: String, label: String): String = "$value $label"
}
