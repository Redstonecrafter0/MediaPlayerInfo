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

To use MediaPlayerInfo in your project, you need to include it as a dependency.

```kotlin
dependencies {
    implementation("com.github.Redstonecrafter0:MediaPlayerInfo:VERSION")
}
```

```groovy
dependencies {
    implementation 'com.github.Redstonecrafter0:MediaPlayerInfo:VERSION'
}
```

Replace `VERSION` with the latest version of MediaPlayerInfo.

## Documentation

The documentation for MediaPlayerInfo is available at [MediaPlayerInfo Documentation](https://redstonecrafter0.github.io/mediaplayerinfo). It provides comprehensive information about the usage, API references, and more.

## Contributing

We welcome contributions to MediaPlayerInfo! Please see our [Contributing Guide](CONTRIBUTING.md) for more details.

## License

MediaPlayerInfo is licensed under the [GNU Affero General Public License](LICENSE).
