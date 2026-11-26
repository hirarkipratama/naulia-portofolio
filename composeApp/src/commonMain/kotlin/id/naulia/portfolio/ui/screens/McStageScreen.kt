package id.naulia.portfolio.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import id.naulia.portfolio.data.model.PortfolioMetrics
import id.naulia.portfolio.ui.components.EventPhotoCard
import id.naulia.portfolio.ui.components.MetricCard
import id.naulia.portfolio.ui.components.MetricTone
import id.naulia.portfolio.ui.components.PillTone
import id.naulia.portfolio.ui.components.SectionHeader
import id.naulia.portfolio.ui.components.TrustedClient
import id.naulia.portfolio.ui.components.TrustedClientRow
import id.naulia.portfolio.ui.icons.LuminaIcons
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.rememberHorizontalMargin
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
    val horizontalMargin = rememberHorizontalMargin()
    val metrics = remember(contentState.metrics) { contentState.metrics.toDisplayMetrics() }
    val featuredEvents = remember(contentState.events) {
        contentState.events.take(FEATURED_EVENT_COUNT)
    }

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
            title = McStageStrings.TITLE,
            subtitle = McStageStrings.SUBTITLE,
            eyebrow = McStageStrings.EYEBROW,
            eyebrowIcon = LuminaIcons.Mic,
            eyebrowTone = PillTone.Primary,
            centered = true,
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
        if (isMobile) {
            for (rowIndex in metrics.indices step MOBILE_METRIC_COLUMNS) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
                ) {
                    metrics.drop(rowIndex).take(MOBILE_METRIC_COLUMNS).forEach { metric ->
                        MetricCard(
                            value = metric.value,
                            label = metric.label,
                            supportingText = metric.supportingText,
                            tone = metric.tone,
                            modifier = Modifier.weight(METRIC_WEIGHT),
                        )
                    }
                }
                Spacer(Modifier.height(LuminaDimens.spacing.sm))
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.md),
            ) {
                metrics.forEach { metric ->
                    MetricCard(
                        value = metric.value,
                        label = metric.label,
                        supportingText = metric.supportingText,
                        tone = metric.tone,
                        modifier = Modifier.weight(METRIC_WEIGHT),
                    )
                }
            }
        }

        Spacer(Modifier.height(LuminaDimens.spacing.xl))
        if (isMobile) {
            featuredEvents.forEach { event ->
                EventPhotoCard(event = event)
                Spacer(Modifier.height(LuminaDimens.spacing.md))
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(LuminaDimens.gutter.desktop),
            ) {
                featuredEvents.forEachIndexed { index, event ->
                    EventPhotoCard(
                        event = event,
                        audienceTone = FEATURED_AUDIENCE_TONES.getOrElse(index) { PillTone.Spotlight },
                        modifier = Modifier.weight(EVENT_WEIGHT),
                    )
                }
            }
        }

        Spacer(Modifier.height(LuminaDimens.spacing.xl))
        TrustedClientRow(
            label = McStageStrings.TRUSTED_LABEL,
            clients = TrustedClients,
        )
    }
}

private data class MetricDisplay(
    val value: String,
    val label: String,
    val supportingText: String,
    val tone: MetricTone,
)

private fun PortfolioMetrics.toDisplayMetrics(): List<MetricDisplay> = listOf(
    MetricDisplay(
        value = McStageStrings.totalEvents(totalEvents),
        label = McStageStrings.TOTAL_EVENTS_LABEL,
        supportingText = McStageStrings.TOTAL_EVENTS_SUPPORT,
        tone = MetricTone.Coral,
    ),
    MetricDisplay(
        value = officialPartners,
        label = McStageStrings.PARTNER_LABEL,
        supportingText = McStageStrings.PARTNER_SUPPORT,
        tone = MetricTone.Sun,
    ),
    MetricDisplay(
        value = McStageStrings.audiobooks(audiobooksProduced),
        label = McStageStrings.AUDIOBOOKS_LABEL,
        supportingText = McStageStrings.AUDIOBOOKS_SUPPORT,
        tone = MetricTone.Mint,
    ),
    MetricDisplay(
        value = McStageStrings.retention(clientRetentionPct),
        label = McStageStrings.RETENTION_LABEL,
        supportingText = McStageStrings.RETENTION_SUPPORT,
        tone = MetricTone.Neutral,
    ),
)

private val TrustedClients = listOf(
    TrustedClient(McStageStrings.CLIENT_GRAMEDIA, LuminaIcons.Library, PillTone.Primary),
    TrustedClient(McStageStrings.CLIENT_KOMPAS, LuminaIcons.School, PillTone.Secondary),
    TrustedClient(McStageStrings.CLIENT_KEMENDIKBUD, LuminaIcons.Book, PillTone.Tertiary),
    TrustedClient(McStageStrings.CLIENT_MIZAN, LuminaIcons.TheaterMasks, PillTone.Primary),
)

private val FEATURED_AUDIENCE_TONES = listOf(PillTone.Spotlight, PillTone.Tertiary)
private const val MOBILE_METRIC_COLUMNS = 2
private const val FEATURED_EVENT_COUNT = 2
private const val METRIC_WEIGHT = 1f
private const val EVENT_WEIGHT = 1f

private object McStageStrings {
    const val EYEBROW = "MC PANGGUNG & MODERATOR ACARA"
    const val TITLE = "Menghangatkan Panggung dengan Antusiasme & Energi"
    const val SUBTITLE =
        "Menjaga auditorium berisi 500+ audiens tetap antusias, ceria, dan penasaran lewat pemanduan yang interaktif dan penuh empati."
    const val TOTAL_EVENTS_LABEL = "Acara Langsung"
    const val TOTAL_EVENTS_SUPPORT = "Dipandu di Seluruh Indonesia"
    const val PARTNER_LABEL = "Mitra Resmi"
    const val PARTNER_SUPPORT = "Panggung Acara"
    const val AUDIOBOOKS_LABEL = "Judul Buku Suara"
    const val AUDIOBOOKS_SUPPORT = "Dinuturkan & Diarahkan"
    const val RETENTION_LABEL = "Retensi Klien"
    const val RETENTION_SUPPORT = "Kerja Sama Berulang"
    const val TRUSTED_LABEL = "DIPERCAYA OLEH PENERBIT & LEMBAGA EDUKASI TERNAMA"
    const val CLIENT_GRAMEDIA = "Gramedia"
    const val CLIENT_KOMPAS = "Kompas Edukasi"
    const val CLIENT_KEMENDIKBUD = "Kemendikbud Literacy"
    const val CLIENT_MIZAN = "Mizan Kids"

    fun totalEvents(count: Int): String = "$count+"

    fun audiobooks(count: Int): String = "$count+"

    fun retention(percent: Int): String = "$percent%"
}
