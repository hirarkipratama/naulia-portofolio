package id.naulia.portfolio.viewmodel

import androidx.compose.runtime.Stable
import id.naulia.portfolio.data.model.ContactForm

private val EMAIL_PATTERN = Regex("^[A-Za-z0-9+._%\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$")

enum class ContactChannel { EMAIL, WHATSAPP }

@Stable
data class FormValidation(
    val nameError: String?,
    val contactError: String?,
    val servicesError: String?,
    val scopeError: String?,
) {
    val isValid: Boolean
        get() = nameError == null && contactError == null && servicesError == null && scopeError == null

    companion object {
        const val NAME_REQUIRED = "Nama & instansi tidak boleh kosong"
        const val CONTACT_REQUIRED = "Masukkan email atau nomor WhatsApp"
        const val CONTACT_INVALID = "Gunakan email atau nomor WhatsApp (08… / +62…)"
        const val SERVICES_REQUIRED = "Pilih minimal satu layanan"
        const val SCOPE_TOO_SHORT = "Cakupan proyek minimal 10 karakter"
        const val MIN_SCOPE_LENGTH = 10
    }
}

fun validateContactForm(form: ContactForm): FormValidation {
    val contactValue = form.contact.trim()
    return FormValidation(
        nameError = if (form.nameAndInstitution.isBlank()) FormValidation.NAME_REQUIRED else null,
        contactError = when {
            contactValue.isEmpty() -> FormValidation.CONTACT_REQUIRED
            contactValue.detectedContactChannel() == null -> FormValidation.CONTACT_INVALID
            else -> null
        },
        servicesError = if (form.services.isEmpty()) FormValidation.SERVICES_REQUIRED else null,
        scopeError = if (form.projectScope.trim().length < FormValidation.MIN_SCOPE_LENGTH) {
            FormValidation.SCOPE_TOO_SHORT
        } else {
            null
        },
    )
}

fun String.detectedContactChannel(): ContactChannel? = when {
    EMAIL_PATTERN.matches(this) -> ContactChannel.EMAIL
    isValidWhatsApp() -> ContactChannel.WHATSAPP
    else -> null
}

private fun String.isValidWhatsApp(): Boolean {
    val digits = removePrefix("+")
    return when {
        startsWith("08") -> digits.length >= 10 && digits.all { it.isDigit() }
        startsWith("+62") -> digits.length >= 11 && digits.all { it.isDigit() }
        else -> false
    }
}
