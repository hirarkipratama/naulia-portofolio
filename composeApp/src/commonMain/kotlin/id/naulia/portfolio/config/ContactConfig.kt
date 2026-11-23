package id.naulia.portfolio.config

import kotlinx.serialization.Serializable

@Serializable
data class ContactConfig(
    val formEndpoint: String = PLACEHOLDER_FORM_ENDPOINT,
    val whatsappNumber: String = "6281234567890",
    val whatsappDisplay: String = "+62 812-3456-7890",
    val whatsappMessage: String = "Halo Naulia, saya ingin berkolaborasi...",
    val emailAddress: String = "nauliazahra.studio@gmail.com",
    val instagramHandle: String = "@nauliazahra",
    val instagramUrl: String = "",
    val linkedInLabel: String = "LinkedIn / Naulia",
    val linkedInUrl: String = "",
) {
    val isFormEndpointConfigured: Boolean
        get() = formEndpoint.isNotBlank() &&
            formEndpoint != PLACEHOLDER_FORM_ENDPOINT &&
            !formEndpoint.contains(UNCONFIGURED_MARKER)

    companion object {
        const val PLACEHOLDER_FORM_ENDPOINT = "https://formspree.io/f/YOUR_FORM_ID"
        private const val UNCONFIGURED_MARKER = "YOUR_"
        val Default = ContactConfig()
    }
}
