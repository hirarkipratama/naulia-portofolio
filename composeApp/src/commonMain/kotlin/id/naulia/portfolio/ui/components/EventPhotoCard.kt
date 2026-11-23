package id.naulia.portfolio.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.unit.dp
import id.naulia.portfolio.data.model.McEvent
import id.naulia.portfolio.image.rememberImageBitmap
import id.naulia.portfolio.ui.icons.LuminaIcons
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors

@Composable
fun EventPhotoCard(
    event: McEvent,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val photoBrush = remember(colors) {
        Brush.linearGradient(listOf(colors.surface, colors.primaryVariant))
    }
    val photoBitmap = rememberImageBitmap(event.photoAsset)

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.card),
        colors = CardDefaults.cardColors(containerColor = colors.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = LuminaDimens.size.cardElevation),
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
                    color = colors.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(LuminaDimens.spacing.md),
                )
            }
            Surface(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(LuminaDimens.spacing.sm),
                shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
                color = colors.primary,
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = LuminaDimens.spacing.sm,
                        vertical = LuminaDimens.spacing.xs,
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = LuminaIcons.Users,
                        contentDescription = null,
                        tint = colors.onPrimary,
                        modifier = Modifier.size(14.dp),
                    )
                    Spacer(Modifier.width(LuminaDimens.spacing.xs))
                    Text(
                        text = EventPhotoCardStrings.attendees(event.estimatedAttendees),
                        style = MaterialTheme.typography.labelSmall,
                        color = colors.onPrimary,
                    )
                }
            }
        }
        Column(modifier = Modifier.padding(LuminaDimens.spacing.md)) {
            Text(
                text = event.eventName,
                style = MaterialTheme.typography.titleMedium,
                color = colors.onBackground,
            )
            Spacer(Modifier.height(LuminaDimens.spacing.xs))
            Text(
                text = EventPhotoCardStrings.metadata(event.date, event.venue, event.city),
                style = MaterialTheme.typography.bodyMedium,
                color = colors.onSurface,
            )
            Spacer(Modifier.height(LuminaDimens.spacing.sm))
            Row(horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.xs)) {
                event.tags.forEach { tag ->
                    Surface(
                        shape = RoundedCornerShape(LuminaDimens.cornerRadius.pill),
                        color = colors.surface,
                    ) {
                        Text(
                            text = tag,
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

private const val PHOTO_ASPECT_RATIO = 16f / 9f

private object EventPhotoCardStrings {
    fun attendees(count: Int): String = "$count+"

    fun metadata(date: String, venue: String, city: String): String = "$date · $venue, $city"

    fun photoDescription(eventName: String): String = "Foto acara: $eventName"
}
