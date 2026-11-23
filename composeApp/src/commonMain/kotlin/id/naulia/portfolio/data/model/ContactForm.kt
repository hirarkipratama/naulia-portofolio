package id.naulia.portfolio.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ContactForm(
    val nameAndInstitution: String = "",
    val contact: String = "",
    val services: Set<ServiceType> = emptySet(),
    val projectScope: String = "",
    val message: String = "",
)
