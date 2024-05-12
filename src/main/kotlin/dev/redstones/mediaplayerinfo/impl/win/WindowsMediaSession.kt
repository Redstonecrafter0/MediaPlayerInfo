package dev.redstones.mediaplayerinfo.impl.win

import dev.redstones.mediaplayerinfo.IMediaSession
import dev.redstones.mediaplayerinfo.MediaInfo

class WindowsMediaSession(
    override val media: MediaInfo,
    override val owner: String,
    private val index: Int
) : IMediaSession {

    external override fun play()
    external override fun pause()
    external override fun playPause()
    external override fun stop()
    external override fun next()
    external override fun previous()

}
