plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlinx.serialization)
}

val nightlyCommitHash = System.getenv("NIGHTLY_COMMIT")
val preRelease = System.getenv("CI_PRERELEASE")

group = "dev.redstones.mediaplayerinfo"
version = "0.1.0${if (preRelease != null) "-${preRelease}" else ""}${if (nightlyCommitHash != null) "+${nightlyCommitHash}" else ""}"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.kotlinx.serialization.core)
}

java {
    withSourcesJar()
}

tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

kotlin {
    jvmToolchain(17)
}
