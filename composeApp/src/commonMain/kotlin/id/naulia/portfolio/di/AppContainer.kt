package id.naulia.portfolio.di

import id.naulia.portfolio.audio.AudioPlayerContract
import id.naulia.portfolio.audio.audioPlayer as platformAudioPlayer
import id.naulia.portfolio.config.ContactConfig
import id.naulia.portfolio.config.loadContactConfig
import id.naulia.portfolio.data.repository.ContentRepository
import id.naulia.portfolio.data.repository.LocalContentRepository
import id.naulia.portfolio.viewmodel.AudioViewModel
import id.naulia.portfolio.viewmodel.CmsViewModel
import id.naulia.portfolio.viewmodel.ContactViewModel
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object AppContainer {
    val repository: ContentRepository = LocalContentRepository()
    val audioPlayer: AudioPlayerContract = platformAudioPlayer
    val contactConfig: ContactConfig = loadContactConfig()
    val httpClient: HttpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    val audioViewModel by lazy { AudioViewModel(repository, audioPlayer) }
    val contactViewModel by lazy {
        ContactViewModel(
            httpClient = httpClient,
            formEndpoint = contactConfig.formEndpoint,
            formConfigured = contactConfig.isFormEndpointConfigured,
        )
    }
    val cmsViewModel by lazy { CmsViewModel(repository) }
}
