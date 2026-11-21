package id.naulia.portfolio.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import id.naulia.portfolio.data.model.ServiceType
import id.naulia.portfolio.platform.openEmail
import id.naulia.portfolio.platform.openWhatsApp
import id.naulia.portfolio.ui.components.SectionHeader
import id.naulia.portfolio.ui.components.SelectableServicePill
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
    val validation = uiState.validation

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LuminaDimens.spacing.xl,
                vertical = LuminaDimens.spacing.xxl,
            ),
    ) {
        SectionHeader(
            title = ContactStrings.TITLE,
            subtitle = ContactStrings.SUBTITLE,
        )

        FlowRow(
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.sm),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.sm),
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

        OutlinedTextField(
            value = uiState.form.name,
            onValueChange = { value -> contactViewModel.updateName(value) },
            label = { Text(ContactStrings.NAME_LABEL) },
            isError = validation.nameError != null,
            supportingText = validation.nameError?.let { error -> { FormError(error) } },
            shape = RoundedCornerShape(LuminaDimens.cornerRadius.button),
            singleLine = true,
            colors = contactFieldColors(),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(LuminaDimens.spacing.md))

        OutlinedTextField(
            value = uiState.form.contact,
            onValueChange = { value -> contactViewModel.updateContact(value) },
            label = { Text(ContactStrings.CONTACT_LABEL) },
            isError = validation.contactError != null,
            supportingText = validation.contactError?.let { error -> { FormError(error) } }
                ?: { Text(ContactStrings.CONTACT_HELPER, style = MaterialTheme.typography.labelSmall) },
            shape = RoundedCornerShape(LuminaDimens.cornerRadius.button),
            singleLine = true,
            colors = contactFieldColors(),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(LuminaDimens.spacing.md))

        OutlinedTextField(
            value = uiState.form.scope,
            onValueChange = { value -> contactViewModel.updateScope(value) },
            label = { Text(ContactStrings.SCOPE_LABEL) },
            isError = validation.scopeError != null,
            supportingText = validation.scopeError?.let { error -> { FormError(error) } },
            shape = RoundedCornerShape(LuminaDimens.cornerRadius.button),
            minLines = MULTILINE_MIN_LINES,
            colors = contactFieldColors(),
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(Modifier.height(LuminaDimens.spacing.md))

        OutlinedTextField(
            value = uiState.form.message,
            onValueChange = { value -> contactViewModel.updateMessage(value) },
            label = { Text(ContactStrings.MESSAGE_LABEL) },
            shape = RoundedCornerShape(LuminaDimens.cornerRadius.button),
            minLines = OPTIONAL_MULTILINE_MIN_LINES,
            colors = contactFieldColors(),
            modifier = Modifier.fillMaxWidth(),
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
                color = MaterialTheme.colorScheme.errorContainer,
                shape = RoundedCornerShape(LuminaDimens.cornerRadius.button),
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics { contentDescription = ContactStrings.ERROR_DESCRIPTION },
            ) {
                Text(
                    text = errorMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    modifier = Modifier.padding(LuminaDimens.spacing.md),
                )
            }
        }

        Spacer(Modifier.height(LuminaDimens.spacing.xl))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(LuminaDimens.spacing.md),
        ) {
            Button(
                onClick = { openWhatsApp(ContactStrings.WHATSAPP_NUMBER, ContactStrings.WHATSAPP_MESSAGE) },
                modifier = Modifier.weight(DIRECT_CONTACT_WEIGHT),
            ) {
                Text(ContactStrings.WHATSAPP_LABEL, style = MaterialTheme.typography.labelLarge)
            }
            OutlinedButton(
                onClick = { openEmail(ContactStrings.EMAIL_ADDRESS) },
                modifier = Modifier.weight(DIRECT_CONTACT_WEIGHT),
            ) {
                Text(ContactStrings.EMAIL_LABEL, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
private fun contactFieldColors() = TextFieldDefaults.colors(
    focusedIndicatorColor = LocalLuminaColors.current.primary,
    focusedLabelColor = LocalLuminaColors.current.primary,
)

@Composable
private fun SubmitButton(
    submissionState: SubmissionState,
    onSubmit: () -> Unit,
) {
    val isLoading = submissionState is SubmissionState.Loading
    Button(
        onClick = onSubmit,
        enabled = !isLoading,
        modifier = Modifier.fillMaxWidth(),
    ) {
        if (isLoading) {
            CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
        } else {
            val label = when (submissionState) {
                is SubmissionState.Idle -> ContactStrings.SUBMIT_LABEL
                is SubmissionState.Loading -> ContactStrings.SUBMITTING_LABEL
                is SubmissionState.Success -> ContactStrings.SUBMITTED_LABEL
                is SubmissionState.Error -> ContactStrings.RETRY_LABEL
            }
            Text(label, style = MaterialTheme.typography.labelLarge)
        }
    }
}

@Composable
private fun SuccessMessage(
    onReset: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    Spacer(Modifier.height(LuminaDimens.spacing.lg))
    Surface(
        color = colors.surface,
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.card),
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = ContactStrings.SUCCESS_DESCRIPTION },
    ) {
        Column(
            modifier = Modifier.padding(LuminaDimens.spacing.lg),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        ) {
            Text(
                text = ContactStrings.SUCCESS_ICON,
                style = LocalLuminaTypeScale.current.displayLarge,
            )
            Spacer(Modifier.height(LuminaDimens.spacing.sm))
            Text(
                text = ContactStrings.SUCCESS_MESSAGE,
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onBackground,
            )
            Spacer(Modifier.height(LuminaDimens.spacing.md))
            OutlinedButton(onClick = onReset) {
                Text(ContactStrings.RESET_LABEL, style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
private fun FormError(message: String) {
    Text(
        text = message,
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.error,
    )
}

private const val MULTILINE_MIN_LINES = 3
private const val OPTIONAL_MULTILINE_MIN_LINES = 2
private const val DIRECT_CONTACT_WEIGHT = 1f

object ContactStrings {
    const val TITLE = "Formulir Kolaborasi & Reservasi"
    const val SUBTITLE = "Undang Naulia untuk proyek editorial, MC acara, atau rekaman VO"
    const val NAME_LABEL = "Nama Lengkap"
    const val CONTACT_LABEL = "Email / Nomor WhatsApp"
    const val CONTACT_HELPER = "Contoh: nama@email.com atau 08xxxxxxxxxx"
    const val SCOPE_LABEL = "Deskripsi Singkat Proyek / Acara"
    const val MESSAGE_LABEL = "Pesan Tambahan (Opsional)"
    const val SUBMIT_LABEL = "Kirim Permintaan Kolaborasi"
    const val SUBMITTING_LABEL = "Mengirim…"
    const val SUBMITTED_LABEL = "Terkirim ✓"
    const val RETRY_LABEL = "Coba Lagi"
    const val RESET_LABEL = "Kirim Permintaan Lain"
    const val WHATSAPP_LABEL = "💬 Chat via WhatsApp"
    const val EMAIL_LABEL = "✉️ Kirim Email"
    const val WHATSAPP_NUMBER = "+6281234567890"
    const val WHATSAPP_MESSAGE = "Halo Naulia, saya ingin berkolaborasi..."
    const val EMAIL_ADDRESS = "naulia@email.com"
    const val SUCCESS_ICON = "✅"
    const val SUCCESS_MESSAGE = "Terima kasih! Naulia akan menghubungi Anda dalam 1×24 jam."
    const val SUCCESS_DESCRIPTION = "Permintaan kolaborasi berhasil dikirim"
    const val ERROR_DESCRIPTION = "Permintaan kolaborasi gagal dikirim"
}
