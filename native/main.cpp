#include "main.h"

#include <iostream>
#include <vector>
#include <fstream>
#include <winrt/Windows.Foundation.Collections.h>
#include <winrt/Windows.Media.Control.h>
#include <winrt/Windows.Storage.h>
#include <winrt/Windows.Storage.Streams.h>

using namespace winrt;
using namespace Windows::Media::Control;
using namespace Windows::Storage::Streams;

#define HANDLE_ASYNC_ERROR(operation) \
    try { \
        operation; \
    } catch (...) { \
       std::exception_ptr ex = std::current_exception(); \
        try { \
            std::rethrow_exception(ex); \
        } catch (const std::bad_exception& e) { \
            std::wcerr << e.what() << std::endl; \
        } catch (...) { \
            std::cerr << "Unhandled exception" << std::endl; \
        } \
    }

jobject Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaPlayerInfo_getMediaSessions(JNIEnv* env, jobject obj) {
    jclass listClass = env->FindClass("Ljava/util/LinkedList;");
    jmethodID listConstructor = env->GetMethodID(listClass, "<init>", "()V");
    jmethodID listAdd = env->GetMethodID(listClass, "add", "(Ljava/lang/Object;)Z");
    jclass mediaSessionClass = env->FindClass("Ldev/redstones/mediaplayerinfo/impl/win/WindowsMediaSession;");
    jmethodID mediaSessionConstructor = env->GetMethodID(mediaSessionClass, "<init>", "(Ldev/redstones/mediaplayerinfo/MediaInfo;Ljava/lang/String;I)V");
    jclass mediaInfoClass = env->FindClass("Ldev/redstones/mediaplayerinfo/MediaInfo;");
    jmethodID mediaInfoConstructor = env->GetMethodID(mediaInfoClass, "<init>", "(Ljava/lang/String;Ljava/lang/String;[BJJZ)V");

    jobject list = env->NewObject(listClass, listConstructor);

    HANDLE_ASYNC_ERROR(
            auto smtc = GlobalSystemMediaTransportControlsSessionManager::RequestAsync().get();
            auto sessions = smtc.GetSessions();
            for (int i = 0; i < sessions.Size(); ++i) {
                auto session = sessions.GetAt(i);
                auto timeline = session.GetTimelineProperties();
                auto mediaProperties = session.TryGetMediaPropertiesAsync().get();

                auto thumbnail = mediaProperties.Thumbnail().OpenReadAsync().get();
                auto reader = DataReader(thumbnail.GetInputStreamAt(0));
                reader.LoadAsync(thumbnail.Size()).get();
                std::vector<uint8_t> buffer(thumbnail.Size());
                auto bufferView = array_view<uint8_t>(buffer);
                reader.ReadBytes(bufferView);
                reader.Close();
                thumbnail.Close();

                jstring jTitle = env->NewStringUTF(to_string(mediaProperties.Title()).c_str());
                jstring jArtist = env->NewStringUTF(to_string(mediaProperties.Artist()).c_str());
                jbyteArray jArtwork = env->NewByteArray(static_cast<long>(buffer.size()));
                env->SetByteArrayRegion(jArtwork, 0, static_cast<long>(buffer.size()), reinterpret_cast<const jbyte*>(buffer.data()));
                jlong jPosition;
                jboolean jPlaying = session.GetPlaybackInfo().PlaybackStatus() == GlobalSystemMediaTransportControlsSessionPlaybackStatus::Playing;
                if (jPlaying) {
                    jPosition = std::chrono::duration_cast<std::chrono::seconds>(winrt::clock::now() - timeline.LastUpdatedTime() + timeline.Position()).count();
                } else {
                    jPosition = std::chrono::duration_cast<std::chrono::seconds>(timeline.Position()).count();
                }
                jlong jDuration = std::chrono::duration_cast<std::chrono::seconds>(timeline.EndTime() - timeline.StartTime()).count();

                jobject mediaInfo = env->NewObject(mediaInfoClass, mediaInfoConstructor, jTitle, jArtist, jArtwork, jPosition, jDuration, jPlaying);

                jstring jOwner = env->NewStringUTF(to_string(session.SourceAppUserModelId()).c_str());

                jobject mediaSession = env->NewObject(mediaSessionClass, mediaSessionConstructor, mediaInfo, jOwner, i);
                env->CallBooleanMethod(list, listAdd, mediaSession);
            }

            return list;
    )
    return list;
}

void Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaSession_play(JNIEnv* env, jobject obj) {
    jfieldID indexField = env->GetFieldID(env->GetObjectClass(obj), "index", "I");
    jint index = env->GetIntField(obj, indexField);
    HANDLE_ASYNC_ERROR(
            auto sessions = GlobalSystemMediaTransportControlsSessionManager::RequestAsync().get().GetSessions();
            if (index >= 0 && index < sessions.Size()) {
                auto session = sessions.GetAt(index);
                session.TryPlayAsync();
            }
    )
}

void Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaSession_pause(JNIEnv* env, jobject obj) {
    jfieldID indexField = env->GetFieldID(env->GetObjectClass(obj), "index", "I");
    jint index = env->GetIntField(obj, indexField);
    HANDLE_ASYNC_ERROR(
            auto sessions = GlobalSystemMediaTransportControlsSessionManager::RequestAsync().get().GetSessions();
            if (index >= 0 && index < sessions.Size()) {
                auto session = sessions.GetAt(index);
                session.TryPauseAsync();
            }
    )
}

void Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaSession_playPause(JNIEnv* env, jobject obj) {
    jfieldID indexField = env->GetFieldID(env->GetObjectClass(obj), "index", "I");
    jint index = env->GetIntField(obj, indexField);
    HANDLE_ASYNC_ERROR(
            auto sessions = GlobalSystemMediaTransportControlsSessionManager::RequestAsync().get().GetSessions();
            if (index >= 0 && index < sessions.Size()) {
                auto session = sessions.GetAt(index);
                session.TryTogglePlayPauseAsync();
            }
    )
}

void Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaSession_stop(JNIEnv* env, jobject obj) {
    HANDLE_ASYNC_ERROR(
            jfieldID indexField = env->GetFieldID(env->GetObjectClass(obj), "index", "I");
            jint index = env->GetIntField(obj, indexField);
            auto sessions = GlobalSystemMediaTransportControlsSessionManager::RequestAsync().get().GetSessions();
            if (index >= 0 && index < sessions.Size()) {
                auto session = sessions.GetAt(index);
                session.TryStopAsync();
            }
    )
}

void Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaSession_next(JNIEnv* env, jobject obj) {
    jfieldID indexField = env->GetFieldID(env->GetObjectClass(obj), "index", "I");
    jint index = env->GetIntField(obj, indexField);
    HANDLE_ASYNC_ERROR(
            auto sessions = GlobalSystemMediaTransportControlsSessionManager::RequestAsync().get().GetSessions();
            if (index >= 0 && index < sessions.Size()) {
                auto session = sessions.GetAt(index);
                session.TrySkipNextAsync();
            }
    )
}

void Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaSession_previous(JNIEnv* env, jobject obj) {
    jfieldID indexField = env->GetFieldID(env->GetObjectClass(obj), "index", "I");
    jint index = env->GetIntField(obj, indexField);
    HANDLE_ASYNC_ERROR(
            auto sessions = GlobalSystemMediaTransportControlsSessionManager::RequestAsync().get().GetSessions();
            if (index >= 0 && index < sessions.Size()) {
                auto session = sessions.GetAt(index);
                session.TrySkipPreviousAsync();
            }
    )
}

int main() {
    GlobalSystemMediaTransportControlsSessionManager smtc = GlobalSystemMediaTransportControlsSessionManager::RequestAsync().get();
    auto sessions = smtc.GetSessions();
    for (int i = 0; i < sessions.Size(); ++i) {
        auto session = sessions.GetAt(i);
        auto mediaProps = session.TryGetMediaPropertiesAsync().get();
        auto title = to_string(mediaProps.Title());
        auto artist = to_string(mediaProps.Artist());
        auto thumbnail = mediaProps.Thumbnail().OpenReadAsync().get();
        auto reader = DataReader(thumbnail.GetInputStreamAt(0));
        reader.LoadAsync(thumbnail.Size()).get();
        std::vector<uint8_t> buffer(thumbnail.Size());
        auto bufferView = array_view<uint8_t>(buffer);
        reader.ReadBytes(bufferView);
        reader.Close();
        std::ofstream file("thumbnail.png", std::ios::out | std::ios::binary);
        file.write(reinterpret_cast<char*>(buffer.data()), buffer.size());
        file.close();
        auto timeline = session.GetTimelineProperties();
        long long positionFrom = (std::chrono::duration_cast<std::chrono::milliseconds>(timeline.LastUpdatedTime().time_since_epoch()).count() - 11647238400000) / 1000;
        long long now = (std::chrono::duration_cast<std::chrono::milliseconds>(std::chrono::system_clock::now().time_since_epoch()).count()) / 1000;
        long long pos = std::chrono::duration_cast<std::chrono::seconds>(timeline.Position()).count();
        std::cout << title << std::endl;
        std::cout << artist << std::endl;
        std::cout << positionFrom << std::endl;
        std::cout << now << std::endl;
        std::cout << pos << std::endl;
        std::cout << now - positionFrom + pos << std::endl;
        std::cout << std::chrono::duration_cast<std::chrono::seconds>(winrt::clock::now() - timeline.LastUpdatedTime() + timeline.Position()).count() << std::endl;
    }
    return 0;
}
