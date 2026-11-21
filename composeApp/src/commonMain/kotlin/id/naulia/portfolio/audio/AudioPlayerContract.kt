package id.naulia.portfolio.audio

interface AudioPlayerContract {
    fun load(url: String)
    fun play()
    fun pause()
    fun seekTo(seconds: Double)
    fun getCurrentTime(): Double
    fun getDuration(): Double
    fun setOnTimeUpdate(callback: (Double) -> Unit)
    fun setOnEnded(callback: () -> Unit)
    fun release()
}
