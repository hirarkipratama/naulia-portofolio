package id.naulia.portfolio.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
expect fun rememberImageBitmap(url: String): ImageBitmap?
