package dev.redstones.mediaplayerinfo.impl.linux

import dev.redstones.mediaplayerinfo.IMediaSession
import dev.redstones.mediaplayerinfo.MediaPlayerInfo
import dev.redstones.mediaplayerinfo.impl.linux.dbus.Player
import org.freedesktop.dbus.DBusMap
import org.freedesktop.dbus.connections.impl.DBusConnectionBuilder
import org.freedesktop.dbus.interfaces.DBus
import org.freedesktop.dbus.interfaces.Properties


object LinuxMediaPlayerInfo: MediaPlayerInfo {

    private val conn = DBusConnectionBuilder.forSessionBus().build()
    private val dbus = conn.getRemoteObject("org.freedesktop.DBus", "/", DBus::class.java)

    override fun getMediaSessions(): List<IMediaSession> {
        return dbus.ListNames()
            .filter { it.startsWith("org.mpris.MediaPlayer2.") }
            .map { LinuxMediaSession(conn.getRemoteObject(it, "/org/mpris/MediaPlayer2", Player::class.java), it.removePrefix("org.mpris.MediaPlayer2.")) }
            .filter { getProperty<String>(it.owner, "PlaybackStatus") != "Stopped" }
    }

    internal fun <T> getProperty(owner: String, property: String): T {
        val properties = conn.getRemoteObject("org.mpris.MediaPlayer2.${owner}", "/org/mpris/MediaPlayer2", Properties::class.java)
        return properties.Get("org.mpris.MediaPlayer2.Player", property)
    }

}
