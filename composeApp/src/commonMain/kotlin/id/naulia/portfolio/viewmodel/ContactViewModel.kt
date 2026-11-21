package id.naulia.portfolio.viewmodel

import androidx.compose.runtime.Stable
import id.naulia.portfolio.data.model.ContactForm
import id.naulia.portfolio.data.model.ServiceType
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
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

    fun updateName(name: String) {
        _uiState.update { state ->
            state.withUpdatedForm(state.form.copy(name = name))
        }
    }

    fun updateContact(contact: String) {
        _uiState.update { state ->
            state.withUpdatedForm(state.form.copy(contact = contact))
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

    fun updateScope(scope: String) {
        _uiState.update { state ->
            state.withUpdatedForm(state.form.copy(scope = scope))
        }
    }

    fun updateMessage(message: String) {
        _uiState.update { state ->
            state.withUpdatedForm(state.form.copy(message = message))
        }
    }

    fun submit() {
        val validation = validate()
        _uiState.update { it.copy(validation = validation) }
        if (!validation.isValid) return

        _uiState.update { it.copy(submissionState = SubmissionState.Loading) }
        scope.launch {
            try {
                httpClient.post(formEndpoint) {
                    contentType(ContentType.Application.Json)
                    setBody(_uiState.value.form)
                }
                _uiState.update { it.copy(submissionState = SubmissionState.Success) }
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

    private fun validate(): FormValidation = validateContactForm(_uiState.value.form)

    private fun FormUiState.withUpdatedForm(form: ContactForm): FormUiState = copy(
        form = form,
        validation = FormValidation(null, null, null, null),
    )

    fun onCleared() {
        scope.cancel()
    }

    companion object {
        const val SUBMISSION_ERROR_MESSAGE = "Gagal mengirim permintaan. Silakan coba lagi."
    }
}
