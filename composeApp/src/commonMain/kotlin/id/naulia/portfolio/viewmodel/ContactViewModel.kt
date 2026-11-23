package id.naulia.portfolio.viewmodel

import androidx.compose.runtime.Stable
import id.naulia.portfolio.data.model.ContactForm
import id.naulia.portfolio.data.model.ServiceType
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ContactViewModel(
    private val httpClient: HttpClient,
    private val formEndpoint: String,
    private val formConfigured: Boolean,
) {
    @Stable
    data class FormUiState(
        val form: ContactForm = ContactForm(),
        val validation: FormValidation = FormValidation(null, null, null, null),
        val submissionState: SubmissionState = SubmissionState.Idle,
        val selectedServices: Set<ServiceType> = emptySet(),
    )

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val _uiState = MutableStateFlow(FormUiState())
    val uiState: StateFlow<FormUiState> = _uiState.asStateFlow()

    fun updateNameAndInstitution(value: String) {
        _uiState.update { state ->
            state.withUpdatedForm(state.form.copy(nameAndInstitution = value))
        }
    }

    fun updateContact(value: String) {
        _uiState.update { state ->
            state.withUpdatedForm(state.form.copy(contact = value))
        }
    }

    fun toggleService(service: ServiceType) {
        _uiState.update { state ->
            val services = if (service in state.selectedServices) {
                state.selectedServices - service
            } else {
                state.selectedServices + service
            }
            state.withUpdatedForm(state.form.copy(services = services))
                .copy(selectedServices = services)
        }
    }

    fun updateProjectScope(value: String) {
        _uiState.update { state ->
            state.withUpdatedForm(state.form.copy(projectScope = value))
        }
    }

    fun updateMessage(value: String) {
        _uiState.update { state ->
            state.withUpdatedForm(state.form.copy(message = value))
        }
    }

    fun submit() {
        val validation = validateContactForm(_uiState.value.form)
        _uiState.update { it.copy(validation = validation) }
        if (!validation.isValid) return

        if (!formConfigured) {
            _uiState.update { it.copy(submissionState = SubmissionState.Error(FORM_NOT_CONFIGURED_MESSAGE)) }
            return
        }

        _uiState.update { it.copy(submissionState = SubmissionState.Loading) }
        scope.launch {
            try {
                val response: HttpResponse = httpClient.post(formEndpoint) {
                    contentType(ContentType.Application.Json)
                    setBody(_uiState.value.form)
                }
                val nextState = if (response.status.value in SUCCESS_STATUS_RANGE) {
                    SubmissionState.Success
                } else {
                    SubmissionState.Error(SUBMISSION_FAILED_MESSAGE)
                }
                _uiState.update { it.copy(submissionState = nextState) }
            } catch (exception: Exception) {
                _uiState.update {
                    it.copy(
                        submissionState = SubmissionState.Error(
                            exception.message ?: SUBMISSION_ERROR_MESSAGE,
                        ),
                    )
                }
            }
        }
    }

    fun resetForm() {
        _uiState.update { FormUiState() }
    }

    private fun FormUiState.withUpdatedForm(form: ContactForm): FormUiState = copy(
        form = form,
        validation = FormValidation(null, null, null, null),
    )

    fun onCleared() {
        scope.cancel()
    }

    companion object {
        const val SUBMISSION_ERROR_MESSAGE = "Gagal mengirim pesan. Silakan coba lagi."
        const val SUBMISSION_FAILED_MESSAGE = "Pengiriman ditolak server. Silakan coba lagi."
        const val FORM_NOT_CONFIGURED_MESSAGE = "Formulir belum dikonfigurasi. Hubungi langsung via WhatsApp atau email."
        private val SUCCESS_STATUS_RANGE = 200..299
    }
}
