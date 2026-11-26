package id.naulia.portfolio.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun rememberWindowWidth(): Dp {
    val density = LocalDensity.current
    val widthPx = LocalWindowInfo.current.containerSize.width
    return remember(density, widthPx) { with(density) { widthPx.toDp() } }
}

@Composable
fun rememberIsMobile(): Boolean = rememberWindowWidth() < LuminaDimens.Breakpoints.tablet

@Composable
fun rememberHorizontalMargin(): Dp =
    if (rememberIsMobile()) LuminaDimens.margin.mobile else LuminaDimens.margin.desktop

@Composable
fun rememberGridGutter(): Dp =
    if (rememberIsMobile()) LuminaDimens.gutter.mobile else LuminaDimens.gutter.desktop
