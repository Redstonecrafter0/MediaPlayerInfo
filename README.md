# MediaPlayerInfo

MediaPlayerInfo is a Kotlin wrapper around platform-specific APIs designed to read the currently playing media.

The original idea came from [this PoC](https://github.com/Redstonecrafter0/WindowsReadMediaInfo).

## MacOS
MacOS is not supported as I don't have the hardware to test this. PRs are welcome.

## Features

- Platform-independent API integration
- Easy to use Kotlin wrapper
- Support for multiple media types

## Usage

### Dependency

To use MediaPlayerInfo in your project, you need to include it as a dependency.

```kotlin
dependencies {
    implementation("dev.redstones.mediaplayerinfo:MediaPlayerInfo:VERSION")
}
```

```groovy
dependencies {
    implementation 'dev.redstones.mediaplayerinfo:MediaPlayerInfo:VERSION'
}
```

Replace `VERSION` with the latest version of MediaPlayerInfo.

#### Dev Builds

In case you want to use a prerelease you must add the maven repo for it.

```kotlin
repositories {
    maven("https://maven.pkg.github.com/Redstonecrafter0/MediaPlayerInfo")
}
```

### Code
```kotlin
// print all current sessions current tracks
MediaPlayerInfo.getMediaSessions().forEach { session ->
    println(session.media)
}
```

## Documentation

The documentation for MediaPlayerInfo is available at [MediaPlayerInfo Documentation](https://redstonecrafter0.github.io/mediaplayerinfo). It provides comprehensive information about the usage, API references, and more.

## Contributing

We welcome contributions to MediaPlayerInfo! Please see our [Contributing Guide](CONTRIBUTING.md) for more details.

## License

MediaPlayerInfo is licensed under the [GNU Affero General Public License](LICENSE).
