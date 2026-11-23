package id.naulia.portfolio.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.rememberIsMobile

@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    eyebrow: String? = null,
    eyebrowIcon: ImageVector? = null,
    eyebrowTone: PillTone = PillTone.Neutral,
    centered: Boolean = false,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val isMobile = rememberIsMobile()
    val alignment = if (centered) Alignment.CenterHorizontally else Alignment.Start
    val textAlign = if (centered) TextAlign.Center else TextAlign.Start
    val titleStyle = if (isMobile) type.headlineLgMobile else type.headlineLg

    Column(
        modifier = modifier
            .then(if (centered) Modifier.fillMaxWidth() else Modifier)
            .padding(bottom = LuminaDimens.spacing.lg),
        horizontalAlignment = alignment,
        verticalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
    ) {
        if (eyebrow != null) {
            SectionEyebrow(text = eyebrow, icon = eyebrowIcon, tone = eyebrowTone)
        }
        Text(
            text = title,
            style = titleStyle,
            color = colors.onSurface,
            textAlign = textAlign,
        )
        Text(
            text = subtitle,
            style = type.bodyMd,
            color = colors.onSurfaceVariant,
            textAlign = textAlign,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.xs))
    }
}
