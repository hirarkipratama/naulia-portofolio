package id.naulia.portfolio.data.model

fun AudioTrack.formattedDuration(): String = durationSec.toDouble().toTimeString()

fun Double.toTimeString(): String {
    val totalSeconds = toInt().coerceAtLeast(0)
    val minutes = totalSeconds / SECONDS_PER_MINUTE
    val seconds = totalSeconds % SECONDS_PER_MINUTE
    return "$minutes:${seconds.toString().padStart(TIME_DIGITS, '0')}"
}

private const val SECONDS_PER_MINUTE = 60
private const val TIME_DIGITS = 2
