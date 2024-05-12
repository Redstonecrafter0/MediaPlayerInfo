package dev.redstones.mediaplayerinfo.impl.win

import dev.redstones.mediaplayerinfo.IMediaSession
import dev.redstones.mediaplayerinfo.MediaPlayerInfo
import java.nio.file.Files

object WindowsMediaPlayerInfo: MediaPlayerInfo {

    init {
        val dllFile = Files.createTempDirectory("mediaplayerinfo-").resolve("MediaPlayerInfo.dll").toFile()
        dllFile.writeBytes(javaClass.getResourceAsStream("/mediaplayerinfo/natives/win/MediaPlayerInfo.dll")!!.readAllBytes())
        System.load(dllFile.canonicalPath)
    }

    external override fun getMediaSessions(): List<IMediaSession>

}
