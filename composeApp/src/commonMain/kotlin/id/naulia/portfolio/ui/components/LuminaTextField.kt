package id.naulia.portfolio.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale

@Composable
fun LuminaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    singleLine: Boolean = true,
    minLines: Int = 1,
    supportingText: (@Composable () -> Unit)? = null,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val shape: Shape = RoundedCornerShape(LuminaDimens.cornerRadius.button)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label, style = type.labelMd) },
        isError = isError,
        singleLine = singleLine,
        minLines = minLines,
        shape = shape,
        textStyle = type.bodyMd,
        supportingText = supportingText,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colors.surfaceContainerLowest,
            unfocusedContainerColor = colors.surfaceContainerLowest,
            errorContainerColor = colors.surfaceContainerLowest,
            cursorColor = colors.primary,
            errorCursorColor = colors.error,
            focusedIndicatorColor = colors.primary,
            unfocusedIndicatorColor = colors.outlineVariant,
            errorIndicatorColor = colors.error,
            focusedLabelColor = colors.primary,
            unfocusedLabelColor = colors.onSurfaceVariant,
            errorLabelColor = colors.error,
            focusedTextColor = colors.onSurface,
            unfocusedTextColor = colors.onSurface,
            errorTextColor = colors.onSurface,
            focusedSupportingTextColor = colors.onSurfaceVariant,
            unfocusedSupportingTextColor = colors.onSurfaceVariant,
            errorSupportingTextColor = colors.error,
        ),
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = LuminaDimens.size.inputHeight),
    )
}
