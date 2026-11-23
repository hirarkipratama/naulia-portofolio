package id.naulia.portfolio.ui.theme

import androidx.compose.foundation.border
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape

@Composable
fun Modifier.focusRing(shape: Shape): Modifier {
    val colors = LocalLuminaColors.current
    var isFocused by remember { mutableStateOf(false) }
    return this
        .onFocusEvent { focusState -> isFocused = focusState.isFocused }
        .border(
            width = if (isFocused) LuminaDimens.size.focusRingWidth else LuminaDimens.size.noBorder,
            color = if (isFocused) colors.primary else Color.Transparent,
            shape = shape,
        )
}
