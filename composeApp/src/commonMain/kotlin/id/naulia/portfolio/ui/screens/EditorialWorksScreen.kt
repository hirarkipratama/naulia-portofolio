package id.naulia.portfolio.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import id.naulia.portfolio.ui.components.BookCoverCard
import id.naulia.portfolio.ui.components.PillTone
import id.naulia.portfolio.ui.components.SectionHeader
import id.naulia.portfolio.ui.icons.LuminaIcons
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
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
    val columnCount = remember(windowWidth) {
        if (windowWidth < LuminaDimens.Breakpoints.tablet) MOBILE_COLUMNS else DESKTOP_COLUMNS
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LuminaDimens.margin.mobile,
                vertical = LuminaDimens.spacing.xxl,
            ),
    ) {
        SectionHeader(
            title = EditorialWorksStrings.TITLE,
            subtitle = EditorialWorksStrings.SUBTITLE,
            eyebrow = EditorialWorksStrings.EYEBROW,
            eyebrowIcon = LuminaIcons.Book,
            eyebrowTone = PillTone.Secondary,
        )
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

        val books = contentState.books
        for (startIndex in books.indices step columnCount) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.md),
                verticalAlignment = Alignment.Top,
            ) {
                books.drop(startIndex).take(columnCount).forEach { book ->
                    BookCoverCard(
                        book = book,
                        modifier = Modifier.weight(BOOK_WEIGHT),
                    )
                }
            }
            Spacer(Modifier.height(LuminaDimens.spacing.md))
        }

        Spacer(Modifier.height(LuminaDimens.spacing.lg))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceEvenly,
        ) {
            EditorialStats.forEach { stat ->
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stat.value,
                        style = LocalLuminaTypeScale.current.headlineLg,
                        color = colors.primary,
                    )
                    Text(
                        text = stat.label,
                        style = LocalLuminaTypeScale.current.labelMd,
                        color = colors.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

private data class EditorialStat(val value: String, val label: String)

private val EditorialStats = listOf(
    EditorialStat("15+", "Judul Buku"),
    EditorialStat("4", "Penerbit"),
    EditorialStat("2022–2024", "Tahun Terbit"),
)

private const val MOBILE_COLUMNS = 2
private const val DESKTOP_COLUMNS = 3
private const val BOOK_WEIGHT = 1f

private object EditorialWorksStrings {
    const val EYEBROW = "LITERATUR ANAK & SAINS"
    const val TITLE = "Pilihan Karya Editorial & Buku"
    const val SUBTITLE = "Buku Anak & STEM yang Pernah Diedit"
}
