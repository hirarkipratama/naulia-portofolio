package id.naulia.portfolio.ui.theme

import androidx.compose.ui.graphics.Color
import id.naulia.portfolio.data.model.Publisher

fun Publisher.badgeColor(colors: LuminaColorScheme): Color = when (this) {
    Publisher.GRAMEDIA -> colors.secondaryContainer
    Publisher.BIP -> colors.primaryContainer
    Publisher.MIZAN -> colors.tertiaryContainer
    Publisher.ERLANGGA -> colors.surfaceContainerHigh
    Publisher.OTHER -> colors.surfaceVariant
}

fun Publisher.badgeContentColor(colors: LuminaColorScheme): Color = when (this) {
    Publisher.GRAMEDIA -> colors.onSecondaryContainer
    Publisher.BIP -> colors.onPrimaryContainer
    Publisher.MIZAN -> colors.onTertiaryContainer
    Publisher.ERLANGGA -> colors.onSurface
    Publisher.OTHER -> colors.onSurfaceVariant
}
