package dev.redstones.mediaplayerinfo.impl.linux

import dev.redstones.mediaplayerinfo.IMediaSession
import dev.redstones.mediaplayerinfo.MediaInfo
import dev.redstones.mediaplayerinfo.impl.linux.dbus.Player
import org.freedesktop.dbus.DBusMap
import java.net.URL

class LinuxMediaSession(private val dbus: Player, override val owner: String): IMediaSession {

    override val media: MediaInfo = generateMediaInfo()

    override fun play() {
        dbus.Play()
    }

    override fun pause() {
        dbus.Pause()
    }

    override fun playPause() {
        dbus.PlayPause()
    }

    override fun stop() {
        dbus.Stop()
    }

    override fun next() {
        dbus.Next()
    }

    override fun previous() {
        dbus.Previous()
    }

    private fun generateMediaInfo(): MediaInfo {
        val metadata = LinuxMediaPlayerInfo.getProperty<DBusMap<String, Any>>(owner, "Metadata")
        val playing = LinuxMediaPlayerInfo.getProperty<String>(owner, "PlaybackStatus") == "Playing"
        val position = LinuxMediaPlayerInfo.getProperty<Double>(owner, "Position").toLong() / 1000000
        val duration = metadata["mpris:length"]!!.toString().toLong() / 1000000
        val title = metadata["xesam:title"] as String
        val artist = metadata["xesam:artist"].run {
            if (this is String) {
                this
            } else {
                (this as List<*>).joinToString(", ")
            }
        }
        val artworkUrl = metadata["mpris:artUrl"]
        val artwork = if (artworkUrl is String) {
            URL(artworkUrl).readBytes()
        } else {
            ByteArray(0)
        }
        return MediaInfo(
            title,
            artist,
            artwork,
            position,
            duration,
            playing
        )
    }

}
