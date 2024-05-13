package dev.redstones.mediaplayerinfo.impl.linux.dbus

import org.freedesktop.dbus.DBusMap
import org.freedesktop.dbus.TypeRef
import org.freedesktop.dbus.annotations.DBusInterfaceName
import org.freedesktop.dbus.annotations.DBusProperty
import org.freedesktop.dbus.interfaces.DBusInterface
import org.freedesktop.dbus.types.Variant


@DBusInterfaceName("org.mpris.MediaPlayer2.Player")
@DBusProperty(name = "Metadata", type = Player.PropertyMetadataType::class, access = DBusProperty.Access.READ)
@DBusProperty(name = "PlaybackStatus", type = String::class, access = DBusProperty.Access.READ)
@DBusProperty(name = "LoopStatus", type = String::class, access = DBusProperty.Access.READ_WRITE)
@DBusProperty(name = "Volume", type = Double::class, access = DBusProperty.Access.READ_WRITE)
@DBusProperty(name = "Shuffle", type = Double::class, access = DBusProperty.Access.READ_WRITE)
@DBusProperty(name = "Position", type = Integer::class, access = DBusProperty.Access.READ)
@DBusProperty(name = "Rate", type = Double::class, access = DBusProperty.Access.READ_WRITE)
@DBusProperty(name = "MinimumRate", type = Double::class, access = DBusProperty.Access.READ_WRITE)
@DBusProperty(name = "MaximumRate", type = Double::class, access = DBusProperty.Access.READ_WRITE)
@DBusProperty(name = "CanControl", type = Boolean::class, access = DBusProperty.Access.READ)
@DBusProperty(name = "CanPlay", type = Boolean::class, access = DBusProperty.Access.READ)
@DBusProperty(name = "CanPause", type = Boolean::class, access = DBusProperty.Access.READ)
@DBusProperty(name = "CanSeek", type = Boolean::class, access = DBusProperty.Access.READ)
interface Player: DBusInterface {

    fun Play()
    fun Pause()
    fun PlayPause()
    fun Stop()
    fun Next()
    fun Previous()

    interface PropertyMetadataType: TypeRef<Map<String?, Variant<*>?>?>

}
