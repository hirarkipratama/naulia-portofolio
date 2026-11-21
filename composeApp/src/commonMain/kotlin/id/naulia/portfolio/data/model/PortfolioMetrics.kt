package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PortfolioMetrics(
    val totalEvents: Int,
    val officialPartners: String,
    val audiobooksProduced: Int,
    val clientRetentionPct: Int,
    val titlesEdited: Int,
)
