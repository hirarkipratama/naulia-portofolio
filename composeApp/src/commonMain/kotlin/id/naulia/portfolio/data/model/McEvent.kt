package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
data class McEvent(
    val id: String,
    val eventName: String,
    val date: String,
    val venue: String,
    val city: String,
    val estimatedAttendees: Int,
    val photoAsset: String,
    val tags: List<String>,
)
