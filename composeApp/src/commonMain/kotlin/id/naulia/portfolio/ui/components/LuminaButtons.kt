package id.naulia.portfolio.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import id.naulia.portfolio.platform.prefersReducedMotion
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.focusRing

@Composable
fun LuminaPrimaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val reducedMotion = remember { prefersReducedMotion() }
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill)
    val containerColor by animateColorAsState(
        targetValue = when {
            !enabled -> colors.onSurface.copy(alpha = DISABLED_CONTAINER_ALPHA)
            isHovered -> colors.primaryContainer
            else -> colors.primary
        },
        animationSpec = if (reducedMotion) snap() else spring(),
    )
    val contentColor = when {
        !enabled -> colors.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA)
        isHovered -> colors.onPrimaryContainer
        else -> colors.onPrimary
    }
    val scale by animateFloatAsState(
        targetValue = if (isHovered && enabled && !reducedMotion) LuminaDimens.motion.hoverScale else NO_SCALE,
    )
    val shadowElevation by animateDpAsState(
        targetValue = if (isHovered && enabled && !reducedMotion) {
            LuminaDimens.elevation.level2
        } else {
            LuminaDimens.elevation.level1
        },
    )

    Surface(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .shadow(
                elevation = shadowElevation,
                shape = shape,
                ambientColor = colors.shadowCoral,
                spotColor = colors.shadowCoral,
            )
            .hoverable(interactionSource = interactionSource)
            .focusRing(shape)
            .semantics { role = Role.Button },
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        interactionSource = interactionSource,
    ) {
        Row(
            modifier = Modifier
                .heightIn(min = LuminaDimens.size.buttonHeight)
                .padding(
                    horizontal = LuminaDimens.size.buttonHorizontalPadding,
                    vertical = LuminaDimens.size.buttonVerticalPadding,
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (loading) {
                CircularProgressIndicator(
                    color = contentColor,
                    strokeWidth = LuminaDimens.size.thinBorder,
                    modifier = Modifier.size(LuminaDimens.size.buttonIcon),
                )
            } else if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(LuminaDimens.size.buttonIcon),
                )
            }
            Text(text = label, style = type.buttonLabel, color = contentColor)
        }
    }
}

@Composable
fun LuminaSecondaryButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val reducedMotion = remember { prefersReducedMotion() }
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill)
    val containerColor by animateColorAsState(
        targetValue = if (isHovered && enabled) colors.surfaceContainerLow else colors.surfaceContainerLowest,
        animationSpec = if (reducedMotion) snap() else spring(),
    )
    val borderColor by animateColorAsState(
        targetValue = if (isHovered && enabled) colors.primary else colors.outlineVariant,
        animationSpec = if (reducedMotion) snap() else spring(),
    )
    val contentColor = if (enabled) colors.onSurface else colors.onSurface.copy(alpha = DISABLED_CONTENT_ALPHA)
    val scale by animateFloatAsState(
        targetValue = if (isHovered && enabled && !reducedMotion) LuminaDimens.motion.hoverScale else NO_SCALE,
    )

    Surface(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .hoverable(interactionSource = interactionSource)
            .focusRing(shape)
            .semantics { role = Role.Button },
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        border = BorderStroke(LuminaDimens.size.thinBorder, borderColor),
        interactionSource = interactionSource,
    ) {
        Row(
            modifier = Modifier
                .heightIn(min = LuminaDimens.size.buttonHeight)
                .padding(
                    horizontal = LuminaDimens.size.buttonHorizontalPadding,
                    vertical = LuminaDimens.size.buttonVerticalPadding,
                ),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.size(LuminaDimens.size.buttonIcon),
                )
            }
            Text(text = label, style = type.buttonLabel, color = contentColor)
        }
    }
}

@Composable
fun LuminaIconButton(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color? = null,
    contentColor: Color? = null,
) {
    val colors = LocalLuminaColors.current
    val reducedMotion = remember { prefersReducedMotion() }
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val background by animateColorAsState(
        targetValue = containerColor ?: if (isHovered) {
            colors.surfaceContainerHigh
        } else {
            colors.surfaceContainerLow
        },
        animationSpec = if (reducedMotion) snap() else spring(),
    )
    val tint = contentColor ?: colors.onSurface

    Surface(
        onClick = onClick,
        modifier = modifier
            .size(LuminaDimens.size.iconButton)
            .hoverable(interactionSource = interactionSource)
            .focusRing(CircleShape)
            .semantics { role = Role.Button },
        shape = CircleShape,
        color = background,
        contentColor = tint,
        interactionSource = interactionSource,
    ) {
        androidx.compose.foundation.layout.Box(
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = tint,
                modifier = Modifier.size(LuminaDimens.size.iconButtonGlyph),
            )
        }
    }
}

private const val NO_SCALE = 1.0f
private const val DISABLED_CONTAINER_ALPHA = 0.12f
private const val DISABLED_CONTENT_ALPHA = 0.38f
