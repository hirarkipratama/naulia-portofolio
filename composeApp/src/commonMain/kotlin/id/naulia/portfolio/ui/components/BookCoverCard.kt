package id.naulia.portfolio.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import id.naulia.portfolio.data.model.Book
import id.naulia.portfolio.image.rememberImageBitmap
import id.naulia.portfolio.platform.prefersReducedMotion
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.badgeColor

@OptIn(ExperimentalFoundationApi::class, ExperimentalLayoutApi::class)
@Composable
fun BookCoverCard(
    book: Book,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val reducedMotion = remember { prefersReducedMotion() }
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()
    val hoverScale by animateFloatAsState(
        targetValue = if (isHovered && !reducedMotion) HOVER_SCALE else DEFAULT_SCALE,
    )
    val coverBrush = remember(colors) {
        Brush.linearGradient(listOf(colors.secondary, colors.tertiary))
    }
    val coverBitmap = rememberImageBitmap(book.coverAsset)

    Card(
        modifier = modifier
            .graphicsLayer {
                scaleX = hoverScale
                scaleY = hoverScale
            }
            .hoverable(interactionSource = interactionSource),
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.card),
        colors = CardDefaults.cardColors(containerColor = colors.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = LuminaDimens.size.cardElevation),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(COVER_ASPECT_RATIO)
                .background(coverBrush)
                .semantics { contentDescription = BookCoverCardStrings.coverDescription(book.title) },
            contentAlignment = Alignment.Center,
        ) {
            if (coverBitmap != null) {
                Image(
                    bitmap = coverBitmap,
                    contentDescription = BookCoverCardStrings.coverDescription(book.title),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(LuminaDimens.spacing.md),
                )
            }
        }
        Column(modifier = Modifier.padding(LuminaDimens.spacing.md)) {
            Surface(
                shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
                color = book.publisher.badgeColor(colors),
            ) {
                Text(
                    text = book.publisher.displayName,
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.onPrimary,
                    modifier = Modifier.padding(
                        horizontal = LuminaDimens.spacing.sm,
                        vertical = LuminaDimens.spacing.xs,
                    ),
                )
            }
            Spacer(Modifier.height(LuminaDimens.spacing.sm))
            Text(
                text = book.title,
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onBackground,
                maxLines = MAX_TITLE_LINES,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(LuminaDimens.spacing.sm))
            Surface(
                shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
                color = colors.surface,
            ) {
                Text(
                    text = book.ageRange.displayName,
                    style = MaterialTheme.typography.labelSmall,
                    color = colors.onSurface,
                    modifier = Modifier.padding(
                        horizontal = LuminaDimens.spacing.sm,
                        vertical = LuminaDimens.spacing.xs,
                    ),
                )
            }
            Spacer(Modifier.height(LuminaDimens.spacing.sm))
            FlowRow(
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.xs),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.xs),
            ) {
                book.roles.forEach { role ->
                    Surface(
                        shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
                        color = colors.surfaceVariant,
                    ) {
                        Text(
                            text = role.displayBadge,
                            style = MaterialTheme.typography.labelSmall,
                            color = colors.onSurface,
                            modifier = Modifier.padding(
                                horizontal = LuminaDimens.spacing.sm,
                                vertical = LuminaDimens.spacing.xs,
                            ),
                        )
                    }
                }
            }
        }
    }
}

private const val HOVER_SCALE = 1.03f
private const val DEFAULT_SCALE = 1.0f
private const val COVER_ASPECT_RATIO = 3f / 4f
private const val MAX_TITLE_LINES = 2

private object BookCoverCardStrings {
    fun coverDescription(title: String): String = "Sampul buku: $title"
}
