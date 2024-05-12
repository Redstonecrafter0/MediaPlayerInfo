package dev.redstones.mediaplayerinfo

import kotlinx.serialization.Serializable
import javax.imageio.ImageIO

/**
 * @property artworkPng if there is no artwork the [ByteArray] will be empty
 * */
@Serializable
data class MediaInfo(
    val title: String,
    val artist: String,
    val artworkPng: ByteArray,
    val position: Long,
    val duration: Long,
    val playing: Boolean
) {

    val artwork by lazy {
        System.currentTimeMillis()
        try {
            ImageIO.read(artworkPng.inputStream())
        } catch (_: Exception) {
            null
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as MediaInfo

        if (title != other.title) return false
        if (artist != other.artist) return false
        if (!artworkPng.contentEquals(other.artworkPng)) return false
        if (position != other.position) return false
        if (duration != other.duration) return false
        if (playing != other.playing) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + artist.hashCode()
        result = 31 * result + artworkPng.contentHashCode()
        result = 31 * result + position.hashCode()
        result = 31 * result + duration.hashCode()
        result = 31 * result + playing.hashCode()
        return result
    }

    override fun toString(): String {
        return "MediaInfo(title='$title', artist='$artist', position=$position, duration=$duration, playing=$playing)"
    }

}
