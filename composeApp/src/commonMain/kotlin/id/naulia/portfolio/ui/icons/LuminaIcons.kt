package id.naulia.portfolio.ui.icons

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

object LuminaIcons {
    val Home: ImageVector = ImageVector.Builder(
        name = "Home",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(10f, 20f)
            verticalLineTo(14f)
            horizontalLineTo(14f)
            verticalLineTo(20f)
            horizontalLineTo(19f)
            verticalLineTo(12f)
            horizontalLineTo(22f)
            lineTo(12f, 3f)
            lineTo(2f, 12f)
            horizontalLineTo(5f)
            verticalLineTo(20f)
            close()
        }
    }.build()

    val Mic: ImageVector = ImageVector.Builder(
        name = "Mic",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(12f, 14f)
            curveTo(13.66f, 14f, 15f, 12.66f, 15f, 11f)
            verticalLineTo(5f)
            curveTo(15f, 3.34f, 13.66f, 2f, 12f, 2f)
            curveTo(10.34f, 2f, 9f, 3.34f, 9f, 5f)
            verticalLineTo(11f)
            curveTo(9f, 12.66f, 10.34f, 14f, 12f, 14f)
            close()
            moveTo(17f, 11f)
            curveTo(17f, 13.76f, 14.76f, 16f, 12f, 16f)
            curveTo(9.24f, 16f, 7f, 13.76f, 7f, 11f)
            horizontalLineTo(5f)
            curveTo(5f, 14.53f, 7.72f, 17.4f, 11f, 17.9f)
            verticalLineTo(21f)
            horizontalLineTo(13f)
            verticalLineTo(17.9f)
            curveTo(16.28f, 17.4f, 19f, 14.53f, 19f, 11f)
            horizontalLineTo(17f)
            close()
        }
    }.build()

    val Book: ImageVector = ImageVector.Builder(
        name = "Book",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(18f, 2f)
            horizontalLineTo(6f)
            curveTo(4.9f, 2f, 4f, 2.9f, 4f, 4f)
            verticalLineTo(20f)
            curveTo(4f, 21.1f, 4.9f, 22f, 6f, 22f)
            horizontalLineTo(18f)
            curveTo(19.1f, 22f, 20f, 21.1f, 20f, 20f)
            verticalLineTo(4f)
            curveTo(20f, 2.9f, 19.1f, 2f, 18f, 2f)
            close()
            moveTo(6f, 4f)
            horizontalLineTo(11f)
            verticalLineTo(12f)
            lineTo(8.5f, 10.5f)
            lineTo(6f, 12f)
            verticalLineTo(4f)
            close()
        }
    }.build()

    val Stage: ImageVector = ImageVector.Builder(
        name = "Stage",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(19f, 3f)
            horizontalLineTo(5f)
            curveTo(3.9f, 3f, 3f, 3.9f, 3f, 5f)
            verticalLineTo(19f)
            curveTo(3f, 20.1f, 3.9f, 21f, 5f, 21f)
            horizontalLineTo(19f)
            curveTo(20.1f, 21f, 21f, 20.1f, 21f, 19f)
            verticalLineTo(5f)
            curveTo(21f, 3.9f, 20.1f, 3f, 19f, 3f)
            close()
            moveTo(12f, 6f)
            curveTo(13.66f, 6f, 15f, 7.34f, 15f, 9f)
            curveTo(15f, 10.66f, 13.66f, 12f, 12f, 12f)
            curveTo(10.34f, 12f, 9f, 10.66f, 9f, 9f)
            curveTo(9f, 7.34f, 10.34f, 6f, 12f, 6f)
            close()
            moveTo(18f, 17f)
            horizontalLineTo(6f)
            verticalLineTo(16f)
            curveTo(6f, 14f, 10f, 13f, 12f, 13f)
            curveTo(14f, 13f, 18f, 14f, 18f, 16f)
            verticalLineTo(17f)
            close()
        }
    }.build()

    val Person: ImageVector = ImageVector.Builder(
        name = "Person",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(12f, 12f)
            curveTo(14.21f, 12f, 16f, 10.21f, 16f, 8f)
            curveTo(16f, 5.79f, 14.21f, 4f, 12f, 4f)
            curveTo(9.79f, 4f, 8f, 5.79f, 8f, 8f)
            curveTo(8f, 10.21f, 9.79f, 12f, 12f, 12f)
            close()
            moveTo(12f, 14f)
            curveTo(9.33f, 14f, 4f, 15.34f, 4f, 18f)
            verticalLineTo(20f)
            horizontalLineTo(20f)
            verticalLineTo(18f)
            curveTo(20f, 15.34f, 14.67f, 14f, 12f, 14f)
            close()
        }
    }.build()

    val Mail: ImageVector = ImageVector.Builder(
        name = "Mail",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(20f, 4f)
            horizontalLineTo(4f)
            curveTo(2.9f, 4f, 2.01f, 4.9f, 2.01f, 6f)
            lineTo(2f, 18f)
            curveTo(2f, 19.1f, 2.9f, 20f, 4f, 20f)
            horizontalLineTo(20f)
            curveTo(21.1f, 20f, 22f, 19.1f, 22f, 18f)
            verticalLineTo(6f)
            curveTo(22f, 4.9f, 21.1f, 4f, 20f, 4f)
            close()
            moveTo(20f, 8f)
            lineTo(12f, 13f)
            lineTo(4f, 8f)
            verticalLineTo(6f)
            lineTo(12f, 11f)
            lineTo(20f, 6f)
            verticalLineTo(8f)
            close()
        }
    }.build()

    val Pen: ImageVector = ImageVector.Builder(
        name = "Pen",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(3f, 17.25f)
            verticalLineTo(21f)
            horizontalLineTo(6.75f)
            lineTo(17.81f, 9.94f)
            lineTo(14.06f, 6.19f)
            lineTo(3f, 17.25f)
            close()
            moveTo(20.71f, 7.04f)
            curveTo(21.1f, 6.65f, 21.1f, 6.02f, 20.71f, 5.63f)
            lineTo(18.37f, 3.29f)
            curveTo(17.98f, 2.9f, 17.35f, 2.9f, 16.96f, 3.29f)
            lineTo(15.13f, 5.12f)
            lineTo(18.88f, 8.87f)
            lineTo(20.71f, 7.04f)
            close()
        }
    }.build()

    val Science: ImageVector = ImageVector.Builder(
        name = "Science",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(19.8f, 18.4f)
            lineTo(14f, 10.66f)
            verticalLineTo(6f)
            horizontalLineTo(15.5f)
            verticalLineTo(4.5f)
            horizontalLineTo(8.5f)
            verticalLineTo(6f)
            horizontalLineTo(10f)
            verticalLineTo(10.66f)
            lineTo(4.2f, 18.4f)
            curveTo(3.84f, 18.88f, 4.19f, 19.56f, 4.8f, 19.56f)
            horizontalLineTo(19.2f)
            curveTo(19.81f, 19.56f, 20.16f, 18.88f, 19.8f, 18.4f)
            close()
            moveTo(12f, 15f)
            curveTo(11.45f, 15f, 11f, 14.55f, 11f, 14f)
            curveTo(11f, 13.45f, 11.45f, 13f, 12f, 13f)
            curveTo(12.55f, 13f, 13f, 13.45f, 13f, 14f)
            curveTo(13f, 14.55f, 12.55f, 15f, 12f, 15f)
            close()
        }
    }.build()

    val TheaterMasks: ImageVector = ImageVector.Builder(
        name = "TheaterMasks",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = null, stroke = SolidColor(Color.White), strokeLineWidth = 1.8f) {
            moveTo(5f, 4f)
            curveTo(3.9f, 4f, 3f, 5.3f, 3f, 7f)
            curveTo(3f, 13f, 6.7f, 19f, 12f, 21f)
            curveTo(17.3f, 19f, 21f, 13f, 21f, 7f)
            curveTo(21f, 5.3f, 20.1f, 4f, 19f, 4f)
            horizontalLineTo(5f)
            close()
            moveTo(8.5f, 9f)
            curveTo(8.5f, 8.17f, 9.17f, 7.5f, 10f, 7.5f)
            curveTo(10.83f, 7.5f, 11.5f, 8.17f, 11.5f, 9f)
            curveTo(11.5f, 9.83f, 10.83f, 10.5f, 10f, 10.5f)
            curveTo(9.17f, 10.5f, 8.5f, 9.83f, 8.5f, 9f)
            close()
            moveTo(12.5f, 9f)
            curveTo(12.5f, 8.17f, 13.17f, 7.5f, 14f, 7.5f)
            curveTo(14.83f, 7.5f, 15.5f, 8.17f, 15.5f, 9f)
            curveTo(15.5f, 9.83f, 14.83f, 10.5f, 14f, 10.5f)
            curveTo(13.17f, 10.5f, 12.5f, 9.83f, 12.5f, 9f)
            close()
            moveTo(8.5f, 14f)
            curveTo(10f, 16.5f, 14f, 16.5f, 15.5f, 14f)
        }
    }.build()

    val Child: ImageVector = ImageVector.Builder(
        name = "Child",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(12f, 3f)
            curveTo(8.69f, 3f, 6f, 5.69f, 6f, 9f)
            curveTo(6f, 11.3f, 7.2f, 13.3f, 9f, 14.5f)
            verticalLineTo(16f)
            curveTo(6.79f, 16f, 5f, 17.79f, 5f, 20f)
            horizontalLineTo(19f)
            curveTo(19f, 17.79f, 17.21f, 16f, 15f, 16f)
            verticalLineTo(14.5f)
            curveTo(16.8f, 13.3f, 18f, 11.3f, 18f, 9f)
            curveTo(18f, 5.69f, 15.31f, 3f, 12f, 3f)
            close()
            moveTo(13.5f, 4f)
            curveTo(14f, 2.6f, 15.2f, 2f, 16.5f, 2.4f)
            curveTo(15.4f, 2.9f, 14.8f, 3.6f, 14.5f, 4.4f)
            lineTo(13.5f, 4f)
            close()
        }
    }.build()

    val Globe: ImageVector = ImageVector.Builder(
        name = "Globe",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = null, stroke = SolidColor(Color.White), strokeLineWidth = 1.6f) {
            moveTo(12f, 3f)
            curveTo(16.97f, 3f, 21f, 7.03f, 21f, 12f)
            curveTo(21f, 16.97f, 16.97f, 21f, 12f, 21f)
            curveTo(7.03f, 21f, 3f, 16.97f, 3f, 12f)
            curveTo(3f, 7.03f, 7.03f, 3f, 12f, 3f)
            close()
            moveTo(12f, 3f)
            curveTo(14.5f, 6f, 15.5f, 9f, 15.5f, 12f)
            curveTo(15.5f, 15f, 14.5f, 18f, 12f, 21f)
            moveTo(12f, 3f)
            curveTo(9.5f, 6f, 8.5f, 9f, 8.5f, 12f)
            curveTo(8.5f, 15f, 9.5f, 18f, 12f, 21f)
            moveTo(3.5f, 12f)
            horizontalLineTo(20.5f)
            moveTo(5f, 7.5f)
            curveTo(9f, 9.5f, 15f, 9.5f, 19f, 7.5f)
            moveTo(5f, 16.5f)
            curveTo(9f, 14.5f, 15f, 14.5f, 19f, 16.5f)
        }
    }.build()

    val Star: ImageVector = ImageVector.Builder(
        name = "Star",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(12f, 17.27f)
            lineTo(18.18f, 21f)
            lineTo(16.54f, 13.97f)
            lineTo(22f, 9.24f)
            lineTo(14.81f, 8.63f)
            lineTo(12f, 2f)
            lineTo(9.19f, 8.63f)
            lineTo(2f, 9.24f)
            lineTo(7.46f, 13.97f)
            lineTo(5.82f, 21f)
            lineTo(12f, 17.27f)
            close()
        }
    }.build()

    val Chat: ImageVector = ImageVector.Builder(
        name = "Chat",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(20f, 2f)
            horizontalLineTo(4f)
            curveTo(2.9f, 2f, 2.01f, 2.9f, 2.01f, 4f)
            lineTo(2f, 22f)
            lineTo(6f, 18f)
            horizontalLineTo(20f)
            curveTo(21.1f, 18f, 22f, 17.1f, 22f, 16f)
            verticalLineTo(4f)
            curveTo(22f, 2.9f, 21.1f, 2f, 20f, 2f)
            close()
        }
    }.build()

    val CheckCircle: ImageVector = ImageVector.Builder(
        name = "CheckCircle",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
            curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
            curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
            close()
            moveTo(10f, 17f)
            lineTo(5f, 12f)
            lineTo(6.41f, 10.59f)
            lineTo(10f, 14.17f)
            lineTo(17.59f, 6.58f)
            lineTo(19f, 8f)
            lineTo(10f, 17f)
            close()
        }
    }.build()

    val Users: ImageVector = ImageVector.Builder(
        name = "Users",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(16f, 11f)
            curveTo(17.66f, 11f, 18.99f, 9.66f, 18.99f, 8f)
            curveTo(18.99f, 6.34f, 17.66f, 5f, 16f, 5f)
            curveTo(14.34f, 5f, 13f, 6.34f, 13f, 8f)
            curveTo(13f, 9.66f, 14.34f, 11f, 16f, 11f)
            close()
            moveTo(8f, 11f)
            curveTo(9.66f, 11f, 10.99f, 9.66f, 10.99f, 8f)
            curveTo(10.99f, 6.34f, 9.66f, 5f, 8f, 5f)
            curveTo(6.34f, 5f, 5f, 6.34f, 5f, 8f)
            curveTo(5f, 9.66f, 6.34f, 11f, 8f, 11f)
            close()
            moveTo(8f, 13f)
            curveTo(5.67f, 13f, 1f, 14.17f, 1f, 16.5f)
            verticalLineTo(19f)
            horizontalLineTo(15f)
            verticalLineTo(16.5f)
            curveTo(15f, 14.17f, 10.33f, 13f, 8f, 13f)
            close()
            moveTo(16f, 13f)
            curveTo(15.71f, 13f, 15.38f, 13.02f, 15.03f, 13.05f)
            curveTo(16.19f, 13.89f, 17f, 15.02f, 17f, 16.5f)
            verticalLineTo(19f)
            horizontalLineTo(23f)
            verticalLineTo(16.5f)
            curveTo(23f, 14.17f, 18.33f, 13f, 16f, 13f)
            close()
        }
    }.build()

    val Play: ImageVector = ImageVector.Builder(
        name = "Play",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(8f, 5f)
            verticalLineTo(19f)
            lineTo(19f, 12f)
            lineTo(8f, 5f)
            close()
        }
    }.build()

    val Pause: ImageVector = ImageVector.Builder(
        name = "Pause",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(6f, 19f)
            horizontalLineTo(10f)
            verticalLineTo(5f)
            horizontalLineTo(6f)
            verticalLineTo(19f)
            close()
            moveTo(14f, 5f)
            verticalLineTo(19f)
            horizontalLineTo(18f)
            verticalLineTo(5f)
            horizontalLineTo(14f)
            close()
        }
    }.build()

    val ChevronDown: ImageVector = ImageVector.Builder(
        name = "ChevronDown",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(7.41f, 8.59f)
            lineTo(12f, 13.17f)
            lineTo(16.59f, 8.58f)
            lineTo(18f, 10f)
            lineTo(12f, 16f)
            lineTo(6f, 10f)
            lineTo(7.41f, 8.59f)
            close()
        }
    }.build()

    val GraphicEq: ImageVector = ImageVector.Builder(
        name = "GraphicEq",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(2f, 9f)
            horizontalLineTo(4f)
            verticalLineTo(15f)
            horizontalLineTo(2f)
            close()
            moveTo(6.5f, 5f)
            horizontalLineTo(8.5f)
            verticalLineTo(19f)
            horizontalLineTo(6.5f)
            close()
            moveTo(11f, 8f)
            horizontalLineTo(13f)
            verticalLineTo(16f)
            horizontalLineTo(11f)
            close()
            moveTo(15.5f, 3f)
            horizontalLineTo(17.5f)
            verticalLineTo(21f)
            horizontalLineTo(15.5f)
            close()
            moveTo(20f, 10f)
            horizontalLineTo(22f)
            verticalLineTo(14f)
            horizontalLineTo(20f)
            close()
        }
    }.build()

    val Idea: ImageVector = ImageVector.Builder(
        name = "Idea",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(12f, 2f)
            curveTo(8.13f, 2f, 5f, 5.13f, 5f, 9f)
            curveTo(5f, 11.38f, 6.19f, 13.46f, 8f, 14.83f)
            verticalLineTo(17f)
            horizontalLineTo(16f)
            verticalLineTo(14.83f)
            curveTo(17.81f, 13.46f, 19f, 11.38f, 19f, 9f)
            curveTo(19f, 5.13f, 15.87f, 2f, 12f, 2f)
            close()
            moveTo(9f, 20f)
            horizontalLineTo(15f)
            verticalLineTo(22f)
            horizontalLineTo(9f)
            close()
        }
    }.build()

    val RecordVoiceOver: ImageVector = ImageVector.Builder(
        name = "RecordVoiceOver",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(12f, 14f)
            curveTo(13.66f, 14f, 15f, 12.66f, 15f, 11f)
            verticalLineTo(6f)
            curveTo(15f, 4.34f, 13.66f, 3f, 12f, 3f)
            curveTo(10.34f, 3f, 9f, 4.34f, 9f, 6f)
            verticalLineTo(11f)
            curveTo(9f, 12.66f, 10.34f, 14f, 12f, 14f)
            close()
            moveTo(7f, 11f)
            horizontalLineTo(5f)
            curveTo(5f, 14.87f, 8.13f, 18f, 12f, 18f)
            curveTo(15.87f, 18f, 19f, 14.87f, 19f, 11f)
            horizontalLineTo(17f)
            curveTo(17f, 13.76f, 14.76f, 16f, 12f, 16f)
            curveTo(9.24f, 16f, 7f, 13.76f, 7f, 11f)
            close()
            moveTo(11f, 19f)
            horizontalLineTo(13f)
            verticalLineTo(22f)
            horizontalLineTo(11f)
            close()
        }
    }.build()

    val Forum: ImageVector = ImageVector.Builder(
        name = "Forum",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(4f, 4f)
            horizontalLineTo(16f)
            curveTo(17.1f, 4f, 18f, 4.9f, 18f, 6f)
            verticalLineTo(13f)
            curveTo(18f, 14.1f, 17.1f, 15f, 16f, 15f)
            horizontalLineTo(8f)
            lineTo(4f, 18f)
            verticalLineTo(4f)
            close()
            moveTo(20f, 8f)
            horizontalLineTo(22f)
            verticalLineTo(17f)
            lineTo(18f, 17f)
            lineTo(18f, 20f)
            lineTo(14f, 17f)
            horizontalLineTo(10f)
            verticalLineTo(15f)
            horizontalLineTo(16f)
            curveTo(18.21f, 15f, 20f, 13.21f, 20f, 11f)
            close()
        }
    }.build()

    val Palette: ImageVector = ImageVector.Builder(
        name = "Palette",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(12f, 3f)
            curveTo(7.03f, 3f, 3f, 7.03f, 3f, 12f)
            curveTo(3f, 16.97f, 7.03f, 21f, 12f, 21f)
            curveTo(13.1f, 21f, 14f, 20.1f, 14f, 19f)
            curveTo(14f, 18.45f, 13.78f, 17.95f, 13.4f, 17.6f)
            curveTo(13.14f, 17.35f, 13f, 17f, 13f, 16.6f)
            curveTo(13f, 15.72f, 13.72f, 15f, 14.6f, 15f)
            horizontalLineTo(17f)
            curveTo(19.21f, 15f, 21f, 13.21f, 21f, 11f)
            curveTo(21f, 6.58f, 16.97f, 3f, 12f, 3f)
            close()
        }
    }.build()

    val Location: ImageVector = ImageVector.Builder(
        name = "Location",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(12f, 2f)
            curveTo(8.13f, 2f, 5f, 5.13f, 5f, 9f)
            curveTo(5f, 14.25f, 12f, 22f, 12f, 22f)
            curveTo(12f, 22f, 19f, 14.25f, 19f, 9f)
            curveTo(19f, 5.13f, 15.87f, 2f, 12f, 2f)
            close()
            moveTo(12f, 11.5f)
            curveTo(10.62f, 11.5f, 9.5f, 10.38f, 9.5f, 9f)
            curveTo(9.5f, 7.62f, 10.62f, 6.5f, 12f, 6.5f)
            curveTo(13.38f, 6.5f, 14.5f, 7.62f, 14.5f, 9f)
            curveTo(14.5f, 10.38f, 13.38f, 11.5f, 12f, 11.5f)
            close()
        }
    }.build()

    val Library: ImageVector = ImageVector.Builder(
        name = "Library",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(3f, 4f)
            horizontalLineTo(5f)
            verticalLineTo(18f)
            horizontalLineTo(3f)
            close()
            moveTo(7f, 4f)
            horizontalLineTo(9f)
            verticalLineTo(18f)
            horizontalLineTo(7f)
            close()
            moveTo(11f, 5f)
            lineTo(13f, 4f)
            lineTo(16f, 18f)
            lineTo(14f, 19f)
            close()
            moveTo(2f, 20f)
            horizontalLineTo(18f)
            verticalLineTo(22f)
            horizontalLineTo(2f)
            close()
        }
    }.build()

    val School: ImageVector = ImageVector.Builder(
        name = "School",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(12f, 3f)
            lineTo(1f, 9f)
            lineTo(12f, 15f)
            lineTo(23f, 9f)
            close()
            moveTo(5f, 13f)
            verticalLineTo(17f)
            curveTo(5f, 18.66f, 8.13f, 20f, 12f, 20f)
            curveTo(15.87f, 20f, 19f, 18.66f, 19f, 17f)
            verticalLineTo(13f)
            lineTo(12f, 16.5f)
            close()
        }
    }.build()

    val Instagram: ImageVector = ImageVector.Builder(
        name = "Instagram",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = null, stroke = SolidColor(Color.White), strokeLineWidth = 1.8f) {
            moveTo(7f, 2f)
            curveTo(4.24f, 2f, 2f, 4.24f, 2f, 7f)
            verticalLineTo(17f)
            curveTo(2f, 19.76f, 4.24f, 22f, 7f, 22f)
            horizontalLineTo(17f)
            curveTo(19.76f, 22f, 22f, 19.76f, 22f, 17f)
            verticalLineTo(7f)
            curveTo(22f, 4.24f, 19.76f, 2f, 17f, 2f)
            close()
            moveTo(12f, 7f)
            curveTo(9.24f, 7f, 7f, 9.24f, 7f, 12f)
            curveTo(7f, 14.76f, 9.24f, 17f, 12f, 17f)
            curveTo(14.76f, 17f, 17f, 14.76f, 17f, 12f)
            curveTo(17f, 9.24f, 14.76f, 7f, 12f, 7f)
            close()
        }
        path(fill = SolidColor(Color.White)) {
            moveTo(17.5f, 5.5f)
            curveTo(18.33f, 5.5f, 19f, 6.17f, 19f, 7f)
            curveTo(19f, 7.83f, 18.33f, 8.5f, 17.5f, 8.5f)
            curveTo(16.67f, 8.5f, 16f, 7.83f, 16f, 7f)
            curveTo(16f, 6.17f, 16.67f, 5.5f, 17.5f, 5.5f)
            close()
        }
    }.build()

    val Work: ImageVector = ImageVector.Builder(
        name = "Work",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = SolidColor(Color.White)) {
            moveTo(20f, 6f)
            horizontalLineTo(16f)
            verticalLineTo(4.5f)
            curveTo(16f, 3.67f, 15.33f, 3f, 14.5f, 3f)
            horizontalLineTo(9.5f)
            curveTo(8.67f, 3f, 8f, 3.67f, 8f, 4.5f)
            verticalLineTo(6f)
            horizontalLineTo(4f)
            curveTo(3f, 6f, 2.1f, 6.9f, 2.1f, 8f)
            lineTo(2f, 19f)
            curveTo(2f, 20.1f, 2.9f, 21f, 4f, 21f)
            horizontalLineTo(20f)
            curveTo(21.1f, 21f, 22f, 20.1f, 22f, 19f)
            verticalLineTo(8f)
            curveTo(22f, 6.9f, 21.1f, 6f, 20f, 6f)
            close()
            moveTo(14f, 6f)
            horizontalLineTo(10f)
            verticalLineTo(4.5f)
            horizontalLineTo(14f)
            verticalLineTo(6f)
            close()
        }
    }.build()

    val Schedule: ImageVector = ImageVector.Builder(
        name = "Schedule",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f,
    ).apply {
        path(fill = null, stroke = SolidColor(Color.White), strokeLineWidth = 1.8f) {
            moveTo(12f, 3f)
            curveTo(16.97f, 3f, 21f, 7.03f, 21f, 12f)
            curveTo(21f, 16.97f, 16.97f, 21f, 12f, 21f)
            curveTo(7.03f, 21f, 3f, 16.97f, 3f, 12f)
            curveTo(3f, 7.03f, 7.03f, 3f, 12f, 3f)
            close()
            moveTo(12f, 7f)
            verticalLineTo(12f)
            lineTo(15.5f, 14f)
        }
    }.build()
}
