package id.naulia.portfolio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    audienceTone: PillTone = PillTone.Spotlight,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val photoBrush = remember(colors) {
        Brush.linearGradient(listOf(colors.surfaceContainerHigh, colors.primaryContainer))
    }
    val photoBitmap = rememberImageBitmap(event.photoAsset)

    LuminaCard(
        modifier = modifier,
        contentPadding = PaddingValues(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(PHOTO_ASPECT_RATIO)
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
                label = EventPhotoCardStrings.venue(event.venue),
                icon = LuminaIcons.Location,
                tone = PillTone.Inverse,
                showPulseDot = false,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(LuminaDimens.spacing.md),
            )
            StatusPill(
                label = EventPhotoCardStrings.attendees(event.estimatedAttendees),
                tone = audienceTone,
                showPulseDot = false,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(LuminaDimens.spacing.md),
            )
        }
        Column(
            modifier = Modifier.padding(LuminaDimens.spacing.md),
            verticalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (event.role.isNotBlank()) {
                    Text(
                        text = event.role.uppercase(),
                        style = type.labelSm,
                        color = colors.primary,
                        modifier = Modifier.weight(EVENT_METADATA_WEIGHT),
                    )
                }
                Text(
                    text = event.date,
                    style = type.labelSm,
                    color = colors.onSurfaceVariant,
                )
            }
            Text(
                text = event.eventName,
                style = type.headlineMd,
                color = colors.onSurface,
            )
            if (event.description.isNotBlank()) {
                Text(
                    text = EventPhotoCardStrings.description(event.description, event.venue, event.city),
                    style = type.bodyMd,
                    color = colors.onSurfaceVariant,
                )
            } else {
                Text(
                    text = EventPhotoCardStrings.metadata(event.venue, event.city),
                    style = type.bodyMd,
                    color = colors.onSurfaceVariant,
                )
            }
            Spacer(Modifier.height(LuminaDimens.spacing.xs))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
                verticalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
            ) {
                event.tags.forEach { tag ->
                    StatusPill(label = tag, tone = PillTone.Neutral, showPulseDot = false)
                }
            }
        }
    }
}

private const val PHOTO_ASPECT_RATIO = 16f / 9f
private const val EVENT_METADATA_WEIGHT = 1f

private object EventPhotoCardStrings {
    fun attendees(count: Int): String = "Audiens: $count+"

    fun venue(venue: String): String = venue

    fun metadata(venue: String, city: String): String = "$venue, $city"

    fun description(description: String, venue: String, city: String): String = "$description $venue, $city"

    fun photoDescription(eventName: String): String = "Foto acara: $eventName"
}
