package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ContactForm(
    val name: String = "",
    val contact: String = "",
    val services: Set<ServiceType> = emptySet(),
    val scope: String = "",
    val message: String = "",
)
