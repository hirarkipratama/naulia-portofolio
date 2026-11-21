package id.naulia.portfolio.ui.theme

import androidx.compose.ui.graphics.Color
import id.naulia.portfolio.data.model.Publisher

fun Publisher.badgeColor(colors: LuminaColorScheme): Color = when (this) {
    Publisher.GRAMEDIA -> colors.primary
    Publisher.BIP -> colors.primaryVariant
    Publisher.MIZAN -> colors.secondary
    Publisher.ERLANGGA -> colors.tertiary
    Publisher.OTHER -> colors.onSurface
}
