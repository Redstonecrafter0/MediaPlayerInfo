plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlinx.serialization)

    `maven-publish`
    signing
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
    implementation(libs.bundles.dbus)
}

java {
    withSourcesJar()
}

val dokkaJavadocJar = tasks.register<Jar>("dokkaJavadocJar") {
    dependsOn(tasks.dokkaJavadoc)
    from(tasks.dokkaJavadoc.flatMap { it.outputDirectory })
    archiveClassifier.set("javadoc")
}

tasks.register("getVersion") {
    doLast {
        println(version)
    }
}

kotlin {
    jvmToolchain(17)
}

signing {
    useGpgCmd()
}

publishing {
    repositories {
        maven {
            name = "Central"
            url = uri("https://central.sonatype.com")
            credentials {
                username = System.getenv("MAVEN_USERNAME")
                password = System.getenv("MAVEN_PASSWORD")
            }
        }
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/Redstonecrafter0/MediaPlayerInfo")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
        maven {
            name = "local"
            url = uri(layout.buildDirectory.dir("repos/local"))
        }
    }

    publications {
        publications.create<MavenPublication>("mavenJava") {
            from(components["java"])
            artifact(dokkaJavadocJar)
            artifactId = "media-player-info"
            pom {
                name.set(rootProject.name)
                description.set("A Kotlin library for getting platform based media info")
                url.set("https://github.com/Redstonecrafter0/MediaPlayerInfo")
                licenses {
                    license {
                        name.set("GNU Affero General Public License version 3.0")
                        url.set("http://www.gnu.org/licenses/agpl-3.0.html")
                    }
                }
                developers {
                    developer {
                        name.set("Redstonecrafter0")
                        email.set("54239558+Redstonecrafter0@users.noreply.github.com")
                        organization.set("Redstonecrafter0")
                        organizationUrl.set("https://redstones.dev")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/Redstonecrafter0/MediaPlayerInfo")
                    developerConnection.set("scm:git:https://github.com/Redstonecrafter0/MediaPlayerInfo")
                    url.set("https://github.com/Redstonecrafter0/MediaPlayerInfo")
                }
            }
            the<SigningExtension>().sign(this)
        }
    }
}
