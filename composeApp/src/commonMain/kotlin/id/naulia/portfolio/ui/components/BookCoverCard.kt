package id.naulia.portfolio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
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
import id.naulia.portfolio.ui.theme.badgeTone

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
    val role = book.roles.firstOrNull()?.displayBadge.orEmpty()

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
            StatusPill(
                label = book.category.label,
                tone = book.category.badgeTone(),
                showPulseDot = false,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(LuminaDimens.spacing.sm),
            )
            StatusPill(
                label = book.ageRange.displayName,
                tone = PillTone.Inverse,
                showPulseDot = false,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(LuminaDimens.spacing.sm),
            )
        }
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        Column(
            modifier = Modifier.padding(horizontal = LuminaDimens.spacing.xs),
            verticalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xs),
        ) {
            if (role.isNotBlank()) {
                Text(
                    text = role.uppercase(),
                    style = type.labelSm,
                    color = colors.primary,
                )
            }
            Text(
                text = book.title,
                style = type.headlineSm,
                color = colors.onSurface,
                maxLines = MAX_TITLE_LINES,
                overflow = TextOverflow.Ellipsis,
            )
            if (book.synopsis.isNotBlank()) {
                Text(
                    text = book.synopsis,
                    style = type.bodySm,
                    color = colors.onSurfaceVariant,
                    maxLines = MAX_SYNOPSIS_LINES,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(Modifier.height(LuminaDimens.spacing.xs))
            Text(
                text = BookCoverCardStrings.metadata(book.publisher.displayName, book.pageCount, book.year),
                style = type.labelSm,
                color = colors.onSurfaceVariant,
            )
        }
    }
}

private const val COVER_ASPECT_RATIO = 4f / 3f
private const val MAX_TITLE_LINES = 2
private const val MAX_SYNOPSIS_LINES = 2

private object BookCoverCardStrings {
    fun coverDescription(title: String): String = "Sampul buku: $title"

    fun metadata(publisher: String, pageCount: Int?, year: Int): String =
        if (pageCount != null) "$publisher • $pageCount Hal" else "$publisher • $year"
}
