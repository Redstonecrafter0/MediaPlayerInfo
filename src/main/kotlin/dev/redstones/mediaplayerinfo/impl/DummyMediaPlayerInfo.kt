package dev.redstones.mediaplayerinfo.impl

import dev.redstones.mediaplayerinfo.IMediaSession
import dev.redstones.mediaplayerinfo.MediaPlayerInfo

object DummyMediaPlayerInfo: MediaPlayerInfo {

    override fun getMediaSessions() = emptyList<IMediaSession>()

}
