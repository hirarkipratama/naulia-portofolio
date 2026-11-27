package id.naulia.portfolio.audio

actual val audioPlayer: AudioPlayerContract = object : AudioPlayerContract {
    override fun load(url: String) = Unit
    override fun play() = Unit
    override fun pause() = Unit
    override fun seekTo(seconds: Double) = Unit
    override fun getCurrentTime(): Double = 0.0
    override fun getDuration(): Double = 0.0
    override fun setOnTimeUpdate(callback: (Double) -> Unit) = Unit
    override fun setOnEnded(callback: () -> Unit) = Unit
    override fun release() = Unit
}
