package id.naulia.portfolio.audio

import kotlin.JsFun

private external interface AudioElement

@JsFun("() => new Audio()")
private external fun createAudioElement(): AudioElement

@JsFun("(element, url) => { element.src = url; element.load(); }")
private external fun loadAudioSource(element: AudioElement, url: String)

@JsFun("(element) => { const result = element.play(); if (result && typeof result.catch === 'function') result.catch(() => {}); }")
private external fun playAudio(element: AudioElement)

@JsFun("(element) => { element.pause(); }")
private external fun pauseAudio(element: AudioElement)

@JsFun("(element, seconds) => { element.currentTime = seconds; }")
private external fun seekAudio(element: AudioElement, seconds: Double)

@JsFun("(element) => element.currentTime")
private external fun getAudioCurrentTime(element: AudioElement): Double

@JsFun("(element) => Number.isFinite(element.duration) ? element.duration : 0")
private external fun getAudioDuration(element: AudioElement): Double

@JsFun("(element, callback) => { element.ontimeupdate = () => callback(element.currentTime); }")
private external fun setAudioTimeUpdateCallback(element: AudioElement, callback: (Double) -> Unit)

@JsFun("(element, callback) => { element.onended = () => callback(); }")
private external fun setAudioEndedCallback(element: AudioElement, callback: () -> Unit)

@JsFun("(element) => { element.pause(); element.removeAttribute('src'); element.load(); }")
private external fun releaseAudioElement(element: AudioElement)

class HtmlAudioPlayer : AudioPlayerContract {
    private var element: AudioElement? = null

    override fun load(url: String) {
        val audioElement = element ?: createAudioElement().also { element = it }
        loadAudioSource(audioElement, url)
    }

    override fun play() {
        element?.let(::playAudio)
    }

    override fun pause() {
        element?.let(::pauseAudio)
    }

    override fun seekTo(seconds: Double) {
        element?.let { seekAudio(it, seconds) }
    }

    override fun getCurrentTime(): Double = element?.let(::getAudioCurrentTime) ?: 0.0

    override fun getDuration(): Double = element?.let(::getAudioDuration) ?: 0.0

    override fun setOnTimeUpdate(callback: (Double) -> Unit) {
        element?.let { setAudioTimeUpdateCallback(it, callback) }
    }

    override fun setOnEnded(callback: () -> Unit) {
        element?.let { setAudioEndedCallback(it, callback) }
    }

    override fun release() {
        element?.let(::releaseAudioElement)
        element = null
    }
}
