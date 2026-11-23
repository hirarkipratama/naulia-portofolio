package id.naulia.portfolio.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import id.naulia.portfolio.data.model.ServiceType
import id.naulia.portfolio.platform.openEmail
import id.naulia.portfolio.platform.openWhatsApp
import id.naulia.portfolio.ui.components.LuminaCard
import id.naulia.portfolio.ui.components.LuminaPrimaryButton
import id.naulia.portfolio.ui.components.LuminaSecondaryButton
import id.naulia.portfolio.ui.components.LuminaTextField
import id.naulia.portfolio.ui.components.SectionHeader
import id.naulia.portfolio.ui.components.SelectableServicePill
import id.naulia.portfolio.ui.icons.LuminaIcons
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.viewmodel.ContactViewModel
import id.naulia.portfolio.viewmodel.SubmissionState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContactScreen(
    contactViewModel: ContactViewModel,
    modifier: Modifier = Modifier,
) {
    val uiState by contactViewModel.uiState.collectAsState()
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val validation = uiState.validation

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LuminaDimens.margin.mobile,
                vertical = LuminaDimens.spacing.xl,
            ),
    ) {
        SectionHeader(
            title = ContactStrings.TITLE,
            subtitle = ContactStrings.SUBTITLE,
            eyebrow = ContactStrings.EYEBROW,
            eyebrowIcon = LuminaIcons.Mail,
            eyebrowTone = id.naulia.portfolio.ui.components.PillTone.Secondary,
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
            verticalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
        ) {
            ServiceType.entries.forEach { service ->
                SelectableServicePill(
                    service = service,
                    isSelected = service in uiState.selectedServices,
                    onClick = { contactViewModel.toggleService(service) },
                )
            }
        }
        validation.servicesError?.let { error ->
            FormError(error)
        }
        Spacer(Modifier.height(LuminaDimens.spacing.md))

        LuminaTextField(
            value = uiState.form.name,
            onValueChange = { value -> contactViewModel.updateName(value) },
            label = ContactStrings.NAME_LABEL,
            isError = validation.nameError != null,
            supportingText = validation.nameError?.let { error -> { FormError(error) } },
        )
        Spacer(Modifier.height(LuminaDimens.spacing.md))

        LuminaTextField(
            value = uiState.form.contact,
            onValueChange = { value -> contactViewModel.updateContact(value) },
            label = ContactStrings.CONTACT_LABEL,
            isError = validation.contactError != null,
            supportingText = validation.contactError?.let { error -> { FormError(error) } }
                ?: { Text(ContactStrings.CONTACT_HELPER, style = type.labelSm, color = colors.onSurfaceVariant) },
        )
        Spacer(Modifier.height(LuminaDimens.spacing.md))

        LuminaTextField(
            value = uiState.form.scope,
            onValueChange = { value -> contactViewModel.updateScope(value) },
            label = ContactStrings.SCOPE_LABEL,
            isError = validation.scopeError != null,
            singleLine = false,
            minLines = MULTILINE_MIN_LINES,
            supportingText = validation.scopeError?.let { error -> { FormError(error) } },
        )
        Spacer(Modifier.height(LuminaDimens.spacing.md))

        LuminaTextField(
            value = uiState.form.message,
            onValueChange = { value -> contactViewModel.updateMessage(value) },
            label = ContactStrings.MESSAGE_LABEL,
            singleLine = false,
            minLines = OPTIONAL_MULTILINE_MIN_LINES,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.lg))

        SubmitButton(uiState.submissionState) { contactViewModel.submit() }

        AnimatedVisibility(visible = uiState.submissionState is SubmissionState.Success) {
            SuccessMessage(onReset = { contactViewModel.resetForm() })
        }

        val errorMessage = (uiState.submissionState as? SubmissionState.Error)?.message
        if (errorMessage != null) {
            Spacer(Modifier.height(LuminaDimens.spacing.md))
            Surface(
                color = colors.errorContainer,
                shape = RoundedCornerShape(LuminaDimens.cornerRadius.button),
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = ContactStrings.ERROR_DESCRIPTION },
            ) {
                Text(
                    text = errorMessage,
                    style = type.bodySm,
                    color = colors.onErrorContainer,
                    modifier = Modifier.padding(LuminaDimens.spacing.md),
                )
            }
        }

        Spacer(Modifier.height(LuminaDimens.spacing.xl))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.md),
        ) {
            LuminaPrimaryButton(
                label = ContactStrings.WHATSAPP_LABEL,
                onClick = { openWhatsApp(ContactStrings.WHATSAPP_NUMBER, ContactStrings.WHATSAPP_MESSAGE) },
                icon = LuminaIcons.Chat,
                modifier = Modifier.weight(DIRECT_CONTACT_WEIGHT),
            )
            LuminaSecondaryButton(
                label = ContactStrings.EMAIL_LABEL,
                onClick = { openEmail(ContactStrings.EMAIL_ADDRESS) },
                icon = LuminaIcons.Mail,
                modifier = Modifier.weight(DIRECT_CONTACT_WEIGHT),
            )
        }
    }
}

@Composable
private fun SubmitButton(
    submissionState: SubmissionState,
    onSubmit: () -> Unit,
) {
    val isLoading = submissionState is SubmissionState.Loading
    val label = when (submissionState) {
        is SubmissionState.Idle -> ContactStrings.SUBMIT_LABEL
        is SubmissionState.Loading -> ContactStrings.SUBMITTING_LABEL
        is SubmissionState.Success -> ContactStrings.SUBMITTED_LABEL
        is SubmissionState.Error -> ContactStrings.RETRY_LABEL
    }
    LuminaPrimaryButton(
        label = label,
        onClick = onSubmit,
        enabled = !isLoading,
        loading = isLoading,
        icon = if (isLoading) null else LuminaIcons.Mail,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
private fun SuccessMessage(
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    Spacer(Modifier.height(LuminaDimens.spacing.lg))
    LuminaCard(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = ContactStrings.SUCCESS_DESCRIPTION },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LuminaDimens.spacing.lg),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = LuminaIcons.CheckCircle,
                contentDescription = null,
                tint = colors.primary,
                modifier = Modifier.size(LuminaDimens.spacing.xxl),
            )
            Spacer(Modifier.height(LuminaDimens.spacing.sm))
            Text(
                text = ContactStrings.SUCCESS_MESSAGE,
                style = type.bodyLg,
                color = colors.onSurface,
            )
            Spacer(Modifier.height(LuminaDimens.spacing.md))
            LuminaSecondaryButton(onClick = onReset, label = ContactStrings.RESET_LABEL)
        }
    }
}

@Composable
private fun FormError(message: String) {
    Text(
        text = message,
        style = LocalLuminaTypeScale.current.labelSm,
        color = LocalLuminaColors.current.error,
    )
}

private const val MULTILINE_MIN_LINES = 3
private const val OPTIONAL_MULTILINE_MIN_LINES = 2
private const val DIRECT_CONTACT_WEIGHT = 1f

object ContactStrings {
    const val EYEBROW = "MARI BERKOLABORASI"
    const val TITLE = "Formulir Kolaborasi & Reservasi"
    const val SUBTITLE = "Undang Naulia untuk proyek editorial, MC acara, atau rekaman VO"
    const val NAME_LABEL = "Nama Lengkap"
    const val CONTACT_LABEL = "Email / Nomor WhatsApp"
    const val CONTACT_HELPER = "Contoh: nama@email.com atau 08xxxxxxxxxx"
    const val SCOPE_LABEL = "Deskripsi Singkat Proyek / Acara"
    const val MESSAGE_LABEL = "Pesan Tambahan (Opsional)"
    const val SUBMIT_LABEL = "Kirim Permintaan Kolaborasi"
    const val SUBMITTING_LABEL = "Mengirim…"
    const val SUBMITTED_LABEL = "Terkirim"
    const val RETRY_LABEL = "Coba Lagi"
    const val RESET_LABEL = "Kirim Permintaan Lain"
    const val WHATSAPP_LABEL = "Chat via WhatsApp"
    const val EMAIL_LABEL = "Kirim Email"
    const val WHATSAPP_NUMBER = "+6281234567890"
    const val WHATSAPP_MESSAGE = "Halo Naulia, saya ingin berkolaborasi..."
    const val EMAIL_ADDRESS = "naulia@email.com"
    const val SUCCESS_MESSAGE = "Terima kasih! Naulia akan menghubungi Anda dalam 1×24 jam."
    const val SUCCESS_DESCRIPTION = "Permintaan kolaborasi berhasil dikirim"
    const val ERROR_DESCRIPTION = "Permintaan kolaborasi gagal dikirim"
}
