package id.naulia.portfolio.viewmodel

import androidx.compose.runtime.Stable
import id.naulia.portfolio.audio.AudioPlayerContract
import id.naulia.portfolio.data.model.AudioCategory
import id.naulia.portfolio.data.model.AudioTrack
import id.naulia.portfolio.data.repository.ContentRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class AudioViewModel(
    private val repository: ContentRepository,
    private val player: AudioPlayerContract,
) {
    @Stable
    data class AudioUiState(
        val tracks: List<AudioTrack> = emptyList(),
        val filteredTracks: List<AudioTrack> = emptyList(),
        val activeTrackId: String? = null,
        val isPlaying: Boolean = false,
        val progressSec: Double = 0.0,
        val durationSec: Double = 0.0,
        val selectedCategory: AudioCategory = AudioCategory.ALL,
        val isLoading: Boolean = false,
    )

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val _uiState = MutableStateFlow(AudioUiState())
    val uiState: StateFlow<AudioUiState> = _uiState.asStateFlow()

    private var pollingJob: Job? = null

    init {
        val tracks = repository.getAudioTracks()
        _uiState.update { it.copy(tracks = tracks, filteredTracks = tracks) }
    }

    fun selectCategory(category: AudioCategory) {
        stopPlayback()
        val filteredTracks = repository.getTracksByCategory(category)
        _uiState.update { state ->
            state.copy(
                filteredTracks = filteredTracks,
                selectedCategory = category,
                activeTrackId = null,
                isPlaying = false,
                progressSec = 0.0,
                durationSec = 0.0,
            )
        }
    }

    fun playTrack(track: AudioTrack) {
        stopPlayback()
        player.load(track.assetUrl)
        val duration = player.getDuration().takeIf { it.isFinite() && it > 0.0 }
            ?: track.durationSec.toDouble()

        _uiState.update { state ->
            state.copy(
                activeTrackId = track.id,
                isPlaying = true,
                progressSec = 0.0,
                durationSec = duration,
            )
        }

        player.setOnTimeUpdate { seconds ->
            _uiState.update { state ->
                state.copy(
                    progressSec = seconds,
                    durationSec = state.durationSec.takeIf { it > 0.0 }
                        ?: player.getDuration().takeIf { it.isFinite() && it > 0.0 }
                        ?: state.durationSec,
                )
            }
        }
        player.setOnEnded {
            pollingJob?.cancel()
            pollingJob = null
            _uiState.update { state ->
                state.copy(
                    activeTrackId = null,
                    isPlaying = false,
                    progressSec = 0.0,
                    durationSec = 0.0,
                )
            }
        }
        player.play()
        startProgressPolling()
    }

    fun pauseCurrentTrack() {
        player.pause()
        pollingJob?.cancel()
        pollingJob = null
        _uiState.update { it.copy(isPlaying = false, progressSec = player.getCurrentTime()) }
    }

    fun resumeCurrentTrack() {
        if (_uiState.value.activeTrackId == null) return
        player.play()
        _uiState.update { it.copy(isPlaying = true) }
        startProgressPolling()
    }

    fun seekTo(seconds: Double) {
        val duration = _uiState.value.durationSec
        val target = seconds.coerceIn(0.0, duration.takeIf { it > 0.0 } ?: seconds)
        player.seekTo(target)
        _uiState.update { it.copy(progressSec = target) }
    }

    fun togglePlay(track: AudioTrack) {
        val state = _uiState.value
        when {
            state.activeTrackId != track.id -> playTrack(track)
            state.isPlaying -> pauseCurrentTrack()
            else -> resumeCurrentTrack()
        }
    }

    private fun startProgressPolling() {
        pollingJob?.cancel()
        pollingJob = scope.launch {
            while (isActive) {
                delay(PROGRESS_POLL_INTERVAL_MS)
                try {
                    val elapsed = player.getCurrentTime()
                    val duration = player.getDuration().takeIf { it.isFinite() && it > 0.0 }
                    _uiState.update { state ->
                        state.copy(
                            progressSec = elapsed,
                            durationSec = duration ?: state.durationSec,
                        )
                    }
                } catch (_: Exception) {
                    cancel()
                }
            }
        }
    }

    private fun stopPlayback() {
        player.pause()
        pollingJob?.cancel()
        pollingJob = null
    }

    fun onCleared() {
        stopPlayback()
        player.release()
        scope.cancel()
    }

    companion object {
        private const val PROGRESS_POLL_INTERVAL_MS = 250L
    }
}
