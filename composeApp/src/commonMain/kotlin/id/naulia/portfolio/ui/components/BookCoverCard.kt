package id.naulia.portfolio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import id.naulia.portfolio.data.model.Book
import id.naulia.portfolio.image.rememberImageBitmap
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.badgeColor
import id.naulia.portfolio.ui.theme.badgeContentColor

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BookCoverCard(
    book: Book,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val coverBrush = remember(colors) {
        Brush.linearGradient(listOf(colors.secondaryContainer, colors.tertiaryContainer))
    }
    val coverBitmap = rememberImageBitmap(book.coverAsset)

    LuminaCard(
        modifier = modifier,
        contentPadding = PaddingValues(LuminaDimens.spacing.sm),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(COVER_ASPECT_RATIO)
                .clip(RoundedCornerShape(LuminaDimens.cornerRadius.media))
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
                    color = colors.onSecondaryContainer,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(LuminaDimens.spacing.md),
                )
            }
        }
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        Column(
            modifier = Modifier.padding(horizontal = LuminaDimens.spacing.xs),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.xs),
        ) {
            Surface(
                shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
                color = book.publisher.badgeColor(colors),
            ) {
                Text(
                    text = book.publisher.displayName,
                    style = type.labelSm,
                    color = book.publisher.badgeContentColor(colors),
                    modifier = Modifier.padding(
                        horizontal = LuminaDimens.spacing.sm,
                        vertical = LuminaDimens.spacing.xs,
                    ),
                )
            }
            Text(
                text = book.title,
                style = type.headlineSm,
                color = colors.onSurface,
                maxLines = MAX_TITLE_LINES,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = LuminaDimens.spacing.xs),
            )
            Text(
                text = book.ageRange.displayName,
                style = type.bodySm,
                color = colors.onSurfaceVariant,
            )
            FlowRow(
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.xs),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.xs),
            ) {
                book.roles.forEach { role ->
                    StatusPill(label = role.displayBadge, tone = PillTone.Secondary, showPulseDot = false)
                }
            }
        }
    }
}

private const val COVER_ASPECT_RATIO = 3f / 4f
private const val MAX_TITLE_LINES = 2

private object BookCoverCardStrings {
    fun coverDescription(title: String): String = "Sampul buku: $title"
}
