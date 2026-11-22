package id.naulia.portfolio.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.width
import id.naulia.portfolio.data.model.PortfolioMetrics
import id.naulia.portfolio.ui.components.EventPhotoCard
import id.naulia.portfolio.ui.components.MetricCard
import id.naulia.portfolio.ui.components.SectionHeader
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.rememberIsMobile
import id.naulia.portfolio.viewmodel.CmsViewModel

@Composable
fun McStageScreen(
    cmsViewModel: CmsViewModel,
    modifier: Modifier = Modifier,
) {
    val contentState by cmsViewModel.contentState.collectAsState()
    val colors = LocalLuminaColors.current
    val isMobile = rememberIsMobile()
    val metrics = remember(contentState.metrics) { contentState.metrics.toDisplayMetrics() }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LuminaDimens.spacing.xl,
                vertical = LuminaDimens.spacing.xxl,
            ),
    ) {
        SectionHeader(
            title = McStageStrings.TITLE,
            subtitle = McStageStrings.SUBTITLE,
        )
        if (isMobile) {
            for (rowIndex in metrics.indices step MOBILE_METRIC_COLUMNS) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.sm),
                ) {
                    metrics.drop(rowIndex).take(MOBILE_METRIC_COLUMNS).forEach { metric ->
                        MetricCard(
                            value = metric.first,
                            label = metric.second,
                            modifier = Modifier.weight(METRIC_WEIGHT),
                        )
                    }
                }
                Spacer(Modifier.height(LuminaDimens.spacing.sm))
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.md),
            ) {
                metrics.forEach { metric ->
                    MetricCard(
                        value = metric.first,
                        label = metric.second,
                        modifier = Modifier.weight(METRIC_WEIGHT),
                    )
                }
            }
        }

        Spacer(Modifier.height(LuminaDimens.spacing.xl))
        val events = contentState.events
        if (isMobile) {
            LazyRow(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.md)) {
                items(items = events, key = { it.id }) { event ->
                    EventPhotoCard(
                        event = event,
                        modifier = Modifier.width(EVENT_CARD_WIDTH),
                    )
                }
            }
        } else {
            for (startIndex in events.indices step DESKTOP_EVENT_COLUMNS) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.md),
                ) {
                    events.drop(startIndex).take(DESKTOP_EVENT_COLUMNS).forEach { event ->
                        EventPhotoCard(
                            event = event,
                            modifier = Modifier.weight(EVENT_WEIGHT),
                        )
                    }
                }
                Spacer(Modifier.height(LuminaDimens.spacing.md))
            }
        }

        Spacer(Modifier.height(LuminaDimens.spacing.xl))
        Text(
            text = McStageStrings.CREDIBILITY,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onSurface,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
        )
    }
}

private fun PortfolioMetrics.toDisplayMetrics(): List<Pair<String, String>> = listOf(
    McStageStrings.totalEvents(totalEvents) to McStageStrings.TOTAL_EVENTS_LABEL,
    McStageStrings.PARTNER_VALUE to McStageStrings.PARTNER_LABEL,
    McStageStrings.audiobooks(audiobooksProduced) to McStageStrings.AUDIOBOOKS_LABEL,
    McStageStrings.retention(clientRetentionPct) to McStageStrings.RETENTION_LABEL,
)

private const val MOBILE_METRIC_COLUMNS = 2
private const val DESKTOP_EVENT_COLUMNS = 3
private const val METRIC_WEIGHT = 1f
private const val EVENT_WEIGHT = 1f
private val EVENT_CARD_WIDTH = LuminaDimens.size.portraitWidth

private object McStageStrings {
    const val TITLE = "MC Panggung & Moderator Acara"
    const val SUBTITLE = "Pengalaman memandu acara buku, festival, dan expo sains dengan audiens hingga ratusan peserta"
    const val TOTAL_EVENTS_LABEL = "Total Acara"
    const val PARTNER_VALUE = "Mitra Resmi"
    const val PARTNER_LABEL = "Gramedia Official"
    const val AUDIOBOOKS_LABEL = "Audiobook Diproduksi"
    const val RETENTION_LABEL = "Retensi Klien"
    const val CREDIBILITY = "Gramedia · Kemendikbud · BookFest Jakarta · Science Expo · Pesta Buku"

    fun totalEvents(count: Int): String = "$count+"

    fun audiobooks(count: Int): String = "$count+"

    fun retention(percent: Int): String = "$percent%"
}
