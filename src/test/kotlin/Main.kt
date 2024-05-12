import dev.redstones.mediaplayerinfo.MediaPlayerInfo

fun main() {
    MediaPlayerInfo.getMediaSessions().forEach { session ->
        println(session.media)
    }
}
