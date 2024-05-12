#pragma once

#include <jni.h>

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jobject JNICALL Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaPlayerInfo_getMediaSessions(JNIEnv* env, jobject obj);

JNIEXPORT void JNICALL Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaSession_play(JNIEnv* env, jobject obj);
JNIEXPORT void JNICALL Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaSession_pause(JNIEnv* env, jobject obj);
JNIEXPORT void JNICALL Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaSession_playPause(JNIEnv* env, jobject obj);
JNIEXPORT void JNICALL Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaSession_stop(JNIEnv* env, jobject obj);
JNIEXPORT void JNICALL Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaSession_next(JNIEnv* env, jobject obj);
JNIEXPORT void JNICALL Java_dev_redstones_mediaplayerinfo_impl_win_WindowsMediaSession_previous(JNIEnv* env, jobject obj);

#ifdef __cplusplus
}
#endif
