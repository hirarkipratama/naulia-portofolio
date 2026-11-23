package id.naulia.portfolio.viewmodel

import id.naulia.portfolio.data.model.BookFilter
import id.naulia.portfolio.data.repository.ContentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CmsViewModel(
    private val repository: ContentRepository,
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val _contentState = MutableStateFlow(ContentState())
    val contentState: StateFlow<ContentState> = _contentState.asStateFlow()

    init {
        loadContent()
    }

    fun refresh() {
        loadContent()
    }

    fun selectBookFilter(filter: BookFilter) {
        _contentState.update { it.copy(selectedBookFilter = filter) }
    }

    private fun loadContent() {
        _contentState.update { it.copy(isLoading = true, errorMessage = null) }
        scope.launch(Dispatchers.Default) {
            try {
                val tracks = repository.getAudioTracks()
                val books = repository.getBooks()
                val events = repository.getMcEvents()
                val metrics = repository.getMetrics()
                _contentState.update { state ->
                    state.copy(
                        tracks = tracks,
                        books = books,
                        events = events,
                        metrics = metrics,
                        isLoading = false,
                        errorMessage = null,
                    )
                }
            } catch (exception: Exception) {
                _contentState.update { state ->
                    state.copy(
                        isLoading = false,
                        errorMessage = exception.message ?: ContentState.LOAD_ERROR_MESSAGE,
                    )
                }
            }
        }
    }

    fun onCleared() {
        scope.cancel()
    }
}
