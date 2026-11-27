package id.naulia.portfolio.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp

@Composable
actual fun rememberWindowWidth(): Dp {
    val density = LocalDensity.current
    val widthPx = LocalView.current.width
    return remember(density, widthPx) { with(density) { widthPx.toDp() } }
}
