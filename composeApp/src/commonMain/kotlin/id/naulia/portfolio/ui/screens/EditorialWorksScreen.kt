package id.naulia.portfolio.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import id.naulia.portfolio.data.model.BookFilter
import id.naulia.portfolio.data.model.filterByCategory
import id.naulia.portfolio.ui.components.BookCoverCard
import id.naulia.portfolio.ui.components.LuminaFilterChip
import id.naulia.portfolio.ui.components.PillTone
import id.naulia.portfolio.ui.components.SectionHeader
import id.naulia.portfolio.ui.icons.LuminaIcons
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.rememberGridGutter
import id.naulia.portfolio.ui.theme.rememberHorizontalMargin
import id.naulia.portfolio.ui.theme.rememberWindowWidth
import id.naulia.portfolio.viewmodel.CmsViewModel

@Composable
fun EditorialWorksScreen(
    cmsViewModel: CmsViewModel,
    modifier: Modifier = Modifier,
) {
    val contentState by cmsViewModel.contentState.collectAsState()
    val colors = LocalLuminaColors.current
    val windowWidth = rememberWindowWidth()
    val horizontalMargin = rememberHorizontalMargin()
    val gridGutter = rememberGridGutter()
    val columnCount = remember(windowWidth) {
        when {
            windowWidth < LuminaDimens.Breakpoints.mobile -> NARROW_COLUMNS
            windowWidth < LuminaDimens.Breakpoints.desktop -> TABLET_COLUMNS
            else -> DESKTOP_COLUMNS
        }
    }
    val books = remember(contentState.books, contentState.selectedBookFilter) {
        contentState.books.filterByCategory(contentState.selectedBookFilter)
    }
    val bookRows = remember(books, columnCount) { books.chunked(columnCount) }

    Column(
        modifier = modifier
            .widthIn(max = LuminaDimens.maxContentWidth)
            .fillMaxWidth()
            .padding(
                horizontal = horizontalMargin,
                vertical = LuminaDimens.spacing.xl,
            ),
    ) {
        SectionHeader(
            title = EditorialWorksStrings.TITLE,
            subtitle = EditorialWorksStrings.SUBTITLE,
            eyebrow = EditorialWorksStrings.EYEBROW,
            eyebrowIcon = LuminaIcons.Book,
            eyebrowTone = PillTone.Secondary,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
        ) {
            BookFilter.entries.forEach { filter ->
                LuminaFilterChip(
                    label = filter.label,
                    selected = filter == contentState.selectedBookFilter,
                    onClick = { cmsViewModel.selectBookFilter(filter) },
                )
            }
        }
        Spacer(Modifier.height(LuminaDimens.spacing.md))
        if (contentState.isLoading) {
            CircularProgressIndicator(color = colors.primary)
        }
        contentState.errorMessage?.let { message ->
            Text(
                text = message,
                style = LocalLuminaTypeScale.current.bodySm,
                color = colors.onSurfaceVariant,
            )
        }
        bookRows.forEach { rowBooks ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(gridGutter),
                verticalAlignment = Alignment.Top,
            ) {
                rowBooks.forEach { book ->
                    BookCoverCard(
                        book = book,
                        modifier = Modifier.weight(BOOK_WEIGHT),
                    )
                }
                repeat(columnCount - rowBooks.size) {
                    Spacer(Modifier.weight(BOOK_WEIGHT))
                }
            }
            Spacer(Modifier.height(LuminaDimens.spacing.md))
        }
    }
}

private const val NARROW_COLUMNS = 1
private const val TABLET_COLUMNS = 2
private const val DESKTOP_COLUMNS = 3
private const val BOOK_WEIGHT = 1f

private object EditorialWorksStrings {
    const val EYEBROW = "LITERATUR ANAK & SAINS"
    const val TITLE = "Pilihan Karya Editorial & Buku"
    const val SUBTITLE =
        "Dari kurasi konsep ilmiah hingga ritme penuturan karakter—membidani buku-buku berharga bagi generasi penerus."
}
