package dev.redstones.mediaplayerinfo

interface IMediaSession {

    /**
     * The name of the application owning the [IMediaSession]
     * */
    val owner: String
    val media: MediaInfo

    fun play()
    fun pause()
    fun playPause()
    fun stop()
    fun next()
    fun previous()

}
