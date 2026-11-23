package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PortfolioMetrics(
    val totalEvents: Int,
    val officialPartners: String,
    val audiobooksProduced: Int,
    val clientRetentionPct: Int,
    val titlesEdited: Int,
) {
    companion object {
        val DEFAULT = PortfolioMetrics(
            totalEvents = 50,
            officialPartners = "Gramedia",
            audiobooksProduced = 10,
            clientRetentionPct = 98,
            titlesEdited = 15,
        )
    }
}
