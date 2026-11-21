package id.naulia.portfolio.viewmodel

import androidx.compose.runtime.Stable
import id.naulia.portfolio.data.model.ContactForm

private val EMAIL_PATTERN = Regex("^[A-Za-z0-9+._%\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,}$")

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
        const val NAME_REQUIRED = "Nama tidak boleh kosong"
        const val CONTACT_REQUIRED = "Masukkan email atau nomor WhatsApp yang valid"
        const val SERVICES_REQUIRED = "Pilih minimal satu layanan"
        const val SCOPE_TOO_SHORT = "Deskripsi proyek minimal 10 karakter"
        const val MIN_SCOPE_LENGTH = 10
    }
}

fun validateContactForm(form: ContactForm): FormValidation = FormValidation(
    nameError = if (form.name.isBlank()) FormValidation.NAME_REQUIRED else null,
    contactError = if (!form.contact.isValidContact()) FormValidation.CONTACT_REQUIRED else null,
    servicesError = if (form.services.isEmpty()) FormValidation.SERVICES_REQUIRED else null,
    scopeError = if (form.scope.trim().length < FormValidation.MIN_SCOPE_LENGTH) {
        FormValidation.SCOPE_TOO_SHORT
    } else {
        null
    },
)

private fun String.isValidContact(): Boolean {
    val value = trim()
    if (value.isEmpty()) return false
    if (EMAIL_PATTERN.matches(value)) return true
    return value.startsWith("08") && value.length >= 10 ||
        value.startsWith("+62") && value.length >= 12
}
