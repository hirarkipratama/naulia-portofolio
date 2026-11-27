package id.naulia.portfolio.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

@Composable
expect fun rememberWindowWidth(): Dp

@Composable
fun rememberIsMobile(): Boolean = rememberWindowWidth() < LuminaDimens.Breakpoints.tablet

@Composable
fun rememberHorizontalMargin(): Dp =
    if (rememberIsMobile()) LuminaDimens.margin.mobile else LuminaDimens.margin.desktop

@Composable
fun rememberGridGutter(): Dp =
    if (rememberIsMobile()) LuminaDimens.gutter.mobile else LuminaDimens.gutter.desktop
