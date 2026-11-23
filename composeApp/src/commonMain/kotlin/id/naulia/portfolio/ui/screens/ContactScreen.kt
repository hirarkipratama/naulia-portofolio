package id.naulia.portfolio.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import id.naulia.portfolio.config.ContactConfig
import id.naulia.portfolio.data.model.ServiceType
import id.naulia.portfolio.platform.openEmail
import id.naulia.portfolio.platform.openUrl
import id.naulia.portfolio.platform.openWhatsApp
import id.naulia.portfolio.ui.components.LuminaPrimaryButton
import id.naulia.portfolio.ui.components.LuminaTextField
import id.naulia.portfolio.ui.components.PillTone
import id.naulia.portfolio.ui.components.SectionHeader
import id.naulia.portfolio.ui.components.SelectableServicePill
import id.naulia.portfolio.ui.icons.LuminaIcons
import id.naulia.portfolio.ui.theme.LuminaDimens
import id.naulia.portfolio.ui.theme.LocalLuminaColors
import id.naulia.portfolio.ui.theme.LocalLuminaTypeScale
import id.naulia.portfolio.ui.theme.rememberIsMobile
import id.naulia.portfolio.ui.theme.focusRing
import id.naulia.portfolio.viewmodel.ContactViewModel
import id.naulia.portfolio.viewmodel.SubmissionState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContactScreen(
    contactViewModel: ContactViewModel,
    contactConfig: ContactConfig,
    modifier: Modifier = Modifier,
) {
    val uiState by contactViewModel.uiState.collectAsState()
    val colors = LocalLuminaColors.current
    val isMobile = rememberIsMobile()
    val panelShape = RoundedCornerShape(LuminaDimens.cornerRadius.card)
    val panelPadding = if (isMobile) LuminaDimens.spacing.md else LuminaDimens.spacing.xl

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = LuminaDimens.margin.mobile,
                vertical = LuminaDimens.spacing.xl,
            )
            .shadow(
                elevation = LuminaDimens.elevation.level3,
                shape = panelShape,
                ambientColor = colors.shadowWarm,
                spotColor = colors.shadowWarm,
            ),
        shape = panelShape,
        color = colors.surfaceContainerLowest,
        border = BorderStroke(LuminaDimens.size.thinBorder, colors.outlineVariant),
    ) {
        val pitch: @Composable () -> Unit = { ContactPitch(contactConfig = contactConfig) }
        val form: @Composable () -> Unit = {
            InquiryForm(uiState = uiState, contactViewModel = contactViewModel)
        }
        if (isMobile) {
            Column(modifier = Modifier.padding(panelPadding)) {
                pitch()
                Spacer(Modifier.height(LuminaDimens.spacing.xl))
                form()
            }
        } else {
            Row(
                modifier = Modifier.padding(panelPadding),
                horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xl),
            ) {
                Column(modifier = Modifier.weight(CONTACT_PITCH_WEIGHT)) { pitch() }
                Column(modifier = Modifier.weight(CONTACT_FORM_WEIGHT)) { form() }
            }
        }
    }
}

@Composable
private fun ContactPitch(
    contactConfig: ContactConfig,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current

    Column(modifier = modifier) {
        SectionHeader(
            title = ContactStrings.PITCH_TITLE,
            subtitle = ContactStrings.PITCH_SUBTITLE,
            eyebrow = ContactStrings.EYEBROW,
            eyebrowIcon = LuminaIcons.Mail,
            eyebrowTone = PillTone.Secondary,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.xs))
        DirectChannel(
            icon = LuminaIcons.Chat,
            label = ContactStrings.WHATSAPP_LABEL,
            value = contactConfig.whatsappDisplay,
            onClick = {
                openWhatsApp(contactConfig.whatsappNumber, contactConfig.whatsappMessage)
            },
            iconContainerColor = colors.tertiary,
            iconContentColor = colors.onTertiary,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        DirectChannel(
            icon = LuminaIcons.Mail,
            label = ContactStrings.EMAIL_LABEL,
            value = contactConfig.emailAddress,
            onClick = { openEmail(contactConfig.emailAddress) },
            iconContainerColor = colors.primary,
            iconContentColor = colors.onPrimary,
        )
        Spacer(Modifier.height(LuminaDimens.spacing.sm))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
        ) {
            SocialChannel(
                icon = LuminaIcons.Instagram,
                label = contactConfig.instagramHandle,
                url = contactConfig.instagramUrl,
                modifier = Modifier.weight(DIRECT_CONTACT_WEIGHT),
            )
            SocialChannel(
                icon = LuminaIcons.Work,
                label = contactConfig.linkedInLabel,
                url = contactConfig.linkedInUrl,
                modifier = Modifier.weight(DIRECT_CONTACT_WEIGHT),
            )
        }
    }
}

@Composable
private fun DirectChannel(
    icon: ImageVector,
    label: String,
    value: String,
    onClick: () -> Unit,
    iconContainerColor: androidx.compose.ui.graphics.Color,
    iconContentColor: androidx.compose.ui.graphics.Color,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val shape = RoundedCornerShape(LuminaDimens.cornerRadius.media)

    Surface(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .focusRing(shape)
            .semantics {
                role = Role.Button
                contentDescription = "$label: $value"
            },
        shape = shape,
        color = colors.surfaceContainerLow,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LuminaDimens.spacing.sm),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
        ) {
            Surface(
                shape = RoundedCornerShape(LuminaDimens.cornerRadius.button),
                color = iconContainerColor,
                contentColor = iconContentColor,
                modifier = Modifier.size(LuminaDimens.size.channelIcon),
            ) {
                androidx.compose.foundation.layout.Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier.size(LuminaDimens.size.channelIconGlyph),
                    )
                }
            }
            Column {
                Text(text = label, style = type.labelSm, color = colors.onSurfaceVariant)
                Text(text = value, style = type.bodyMd, color = colors.onSurface)
            }
        }
    }
}

@Composable
private fun SocialChannel(
    icon: ImageVector,
    label: String,
    url: String,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val shape = RoundedCornerShape(LuminaDimens.cornerRadius.media)
    val targetUrl = url.ifBlank { ContactStrings.UNCONFIGURED_CHANNEL_URL }

    Surface(
        onClick = { openUrl(targetUrl) },
        modifier = modifier
            .heightIn(min = LuminaDimens.size.channelButtonHeight)
            .focusRing(shape)
            .semantics {
                role = Role.Button
                contentDescription = label
            },
        shape = shape,
        color = colors.surfaceContainerLow,
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = LuminaDimens.spacing.sm,
                vertical = LuminaDimens.spacing.xs,
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = colors.onSurface,
                modifier = Modifier.size(LuminaDimens.size.channelIconGlyph),
            )
            Spacer(Modifier.size(LuminaDimens.spacing.xs))
            Text(text = label, style = type.labelMd, color = colors.onSurface)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InquiryForm(
    uiState: ContactViewModel.FormUiState,
    contactViewModel: ContactViewModel,
    modifier: Modifier = Modifier,
) {
    val colors = LocalLuminaColors.current
    val type = LocalLuminaTypeScale.current
    val validation = uiState.validation
    val shape = RoundedCornerShape(LuminaDimens.cornerRadius.media)

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = shape,
        color = colors.surfaceContainerLow,
    ) {
        Column(
            modifier = Modifier.padding(
                if (rememberIsMobile()) LuminaDimens.spacing.md else LuminaDimens.spacing.lg,
            ),
        ) {
            Text(
                text = ContactStrings.FORM_TITLE,
                style = type.headlineSm,
                color = colors.onSurface,
            )
            Spacer(Modifier.height(LuminaDimens.spacing.md))

            Text(
                text = ContactStrings.SERVICES_LABEL,
                style = type.labelMd,
                color = colors.onSurface,
            )
            Spacer(Modifier.height(LuminaDimens.spacing.xs))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.sm),
                verticalArrangement = Arrangement.spacedBy(LuminaDimens.spacing.xs),
            ) {
                ServiceType.entries.forEach { service ->
                    SelectableServicePill(
                        service = service,
                        isSelected = service in uiState.selectedServices,
                        onClick = { contactViewModel.toggleService(service) },
                    )
                }
            }
            validation.servicesError?.let { error -> FormError(error) }
            Spacer(Modifier.height(LuminaDimens.spacing.md))

            LuminaTextField(
                value = uiState.form.nameAndInstitution,
                onValueChange = contactViewModel::updateNameAndInstitution,
                label = ContactStrings.NAME_LABEL,
                isError = validation.nameError != null,
                supportingText = validation.nameError?.let { error -> { FormError(error) } },
            )
            Spacer(Modifier.height(LuminaDimens.spacing.md))

            LuminaTextField(
                value = uiState.form.contact,
                onValueChange = contactViewModel::updateContact,
                label = ContactStrings.CONTACT_LABEL,
                isError = validation.contactError != null,
                supportingText = validation.contactError?.let { error -> { FormError(error) } }
                    ?: { Text(ContactStrings.CONTACT_HELPER, style = type.labelSm, color = colors.onSurfaceVariant) },
            )
            Spacer(Modifier.height(LuminaDimens.spacing.md))

            LuminaTextField(
                value = uiState.form.projectScope,
                onValueChange = contactViewModel::updateProjectScope,
                label = ContactStrings.SCOPE_LABEL,
                isError = validation.scopeError != null,
                singleLine = false,
                minLines = MULTILINE_MIN_LINES,
                supportingText = validation.scopeError?.let { error -> { FormError(error) } },
            )
            Spacer(Modifier.height(LuminaDimens.spacing.md))

            LuminaTextField(
                value = uiState.form.message,
                onValueChange = contactViewModel::updateMessage,
                label = ContactStrings.MESSAGE_LABEL,
                singleLine = false,
                minLines = OPTIONAL_MULTILINE_MIN_LINES,
            )
            Spacer(Modifier.height(LuminaDimens.spacing.md))

            SubmitButton(uiState.submissionState) { contactViewModel.submit() }
            Spacer(Modifier.height(LuminaDimens.spacing.sm))
            Text(
                text = ContactStrings.RESPONSE_NOTE,
                style = type.labelSm,
                color = colors.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )

            AnimatedVisibility(visible = uiState.submissionState is SubmissionState.Success) {
                SuccessMessage(onReset = contactViewModel::resetForm)
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
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .semantics { contentDescription = ContactStrings.SUCCESS_DESCRIPTION },
        shape = RoundedCornerShape(LuminaDimens.cornerRadius.media),
        color = colors.tertiaryContainer,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LuminaDimens.spacing.md),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = LuminaIcons.CheckCircle,
                contentDescription = null,
                tint = colors.onTertiaryContainer,
                modifier = Modifier.size(LuminaDimens.size.channelIcon),
            )
            Spacer(Modifier.height(LuminaDimens.spacing.sm))
            Text(
                text = ContactStrings.SUCCESS_MESSAGE,
                style = type.bodyLg,
                color = colors.onTertiaryContainer,
                textAlign = TextAlign.Center,
            )
            Spacer(Modifier.height(LuminaDimens.spacing.sm))
            LuminaPrimaryButton(
                label = ContactStrings.RESET_LABEL,
                onClick = onReset,
                modifier = Modifier.fillMaxWidth(),
            )
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

private const val CONTACT_PITCH_WEIGHT = 5f
private const val CONTACT_FORM_WEIGHT = 7f
private const val DIRECT_CONTACT_WEIGHT = 1f
private const val MULTILINE_MIN_LINES = 4
private const val OPTIONAL_MULTILINE_MIN_LINES = 2

object ContactStrings {
    const val EYEBROW = "MARI BERKOLABORASI"
    const val PITCH_TITLE = "Mari Ciptakan Keajaiban Bersama."
    const val PITCH_SUBTITLE = "Punya naskah yang ingin disempurnakan, festival yang butuh pemandu panggung, atau buku suara yang siap disuarakan? Kirimkan pesan di bawah ini."
    const val FORM_TITLE = "Rincian Kolaborasi & Reservasi"
    const val SERVICES_LABEL = "Layanan yang Anda butuhkan:"
    const val NAME_LABEL = "Nama & Nama Instansi / Penerbit"
    const val CONTACT_LABEL = "Email atau Nomor WhatsApp"
    const val CONTACT_HELPER = "Contoh: maya@penerbit.co.id atau 08xxxxxxxxxx"
    const val SCOPE_LABEL = "Cakupan Proyek & Perkiraan Tanggal"
    const val MESSAGE_LABEL = "Pesan Tambahan (Opsional)"
    const val SUBMIT_LABEL = "Kirim Pesan Kolaborasi"
    const val SUBMITTING_LABEL = "Mengirim…"
    const val SUBMITTED_LABEL = "Terkirim"
    const val RETRY_LABEL = "Coba Lagi"
    const val RESET_LABEL = "Kirim Pesan Lain"
    const val WHATSAPP_LABEL = "WhatsApp Resmi"
    const val EMAIL_LABEL = "Email Studio"
    const val RESPONSE_NOTE = "Respon dijamin dalam 24 jam hari kerja."
    const val UNCONFIGURED_CHANNEL_URL = "#"
    const val SUCCESS_MESSAGE = "Pesan Terkirim! Terima Kasih."
    const val SUCCESS_DESCRIPTION = "Pesan kolaborasi berhasil dikirim"
    const val ERROR_DESCRIPTION = "Pesan kolaborasi gagal dikirim"
}
