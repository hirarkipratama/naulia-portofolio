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
import id.naulia.portfolio.data.model.McEvent
import id.naulia.portfolio.image.rememberImageBitmap
import id.naulia.portfolio.ui.icons.LuminaIcons
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EventPhotoCard(
    event: McEvent,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val photoBrush = remember(colors) {
        Brush.linearGradient(listOf(colors.surfaceContainerHigh, colors.primaryContainer))
    }
    val photoBitmap = rememberImageBitmap(event.photoAsset)

    LuminaCard(
        modifier = modifier,
        contentPadding = PaddingValues(LuminaDimens.spacing.sm),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(PHOTO_ASPECT_RATIO)
                .clip(RoundedCornerShape(LuminaDimens.cornerRadius.media))
                .background(photoBrush)
                .semantics { contentDescription = EventPhotoCardStrings.photoDescription(event.eventName) },
            contentAlignment = Alignment.Center,
        ) {
            if (photoBitmap != null) {
                Image(
                    bitmap = photoBitmap,
                    contentDescription = EventPhotoCardStrings.photoDescription(event.eventName),
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Text(
                    text = event.eventName,
                    style = MaterialTheme.typography.titleMedium,
                    color = colors.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(LuminaDimens.spacing.md),
                )
            }
            StatusPill(
                label = EventPhotoCardStrings.attendees(event.estimatedAttendees),
                icon = LuminaIcons.Users,
                tone = PillTone.Primary,
                showPulseDot = false,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(LuminaDimens.spacing.sm),
            )
        }
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        Column(
            modifier = Modifier.padding(horizontal = LuminaDimens.spacing.xs),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.xs),
        ) {
            Text(
                text = event.eventName,
                style = type.headlineSm,
                color = colors.onSurface,
            )
            Text(
                text = EventPhotoCardStrings.metadata(event.date, event.venue, event.city),
                style = type.bodySm,
                color = colors.onSurfaceVariant,
            )
            FlowRow(
                horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.xs),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.xs),
            ) {
                event.tags.forEach { tag ->
                    StatusPill(label = tag, tone = PillTone.Neutral, showPulseDot = false)
                }
            }
        }
    }
}

private const val PHOTO_ASPECT_RATIO = 16f / 9f

private object EventPhotoCardStrings {
    fun attendees(count: Int): String = "$count+"

    fun metadata(date: String, venue: String, city: String): String = "$date · $venue, $city"

    fun photoDescription(eventName: String): String = "Foto acara: $eventName"
}
