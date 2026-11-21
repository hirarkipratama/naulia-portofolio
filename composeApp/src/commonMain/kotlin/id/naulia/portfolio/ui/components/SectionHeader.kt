package id.naulia.portfolio.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors

@Composable
fun SectionHeader(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    Column(modifier = modifier.padding(bottom = LuminaDimens.spacing.lg)) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            color = colors.onBackground,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyLarge,
            color = colors.onSurface,
        )
    }
}
