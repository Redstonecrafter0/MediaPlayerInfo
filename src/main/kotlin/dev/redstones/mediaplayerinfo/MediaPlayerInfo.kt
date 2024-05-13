package dev.redstones.mediaplayerinfo

import dev.redstones.mediaplayerinfo.impl.DummyMediaPlayerInfo
import dev.redstones.mediaplayerinfo.impl.linux.LinuxMediaPlayerInfo
import dev.redstones.mediaplayerinfo.impl.win.WindowsMediaPlayerInfo

interface MediaPlayerInfo {

    companion object Instance: MediaPlayerInfo by systemMediaPlayerInfo

    /**
     * Gets a list of [IMediaSession]. Consider them short-lived and get a new list every time you do something.
     * */
    fun getMediaSessions(): List<IMediaSession>

}

val systemMediaPlayerInfo = when {
    System.getProperty("os.name").lowercase().startsWith("windows") -> WindowsMediaPlayerInfo
    System.getProperty("os.name").lowercase() == "linux" -> LinuxMediaPlayerInfo
    else -> DummyMediaPlayerInfo
}
