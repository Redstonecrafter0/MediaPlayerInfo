cmake_minimum_required(VERSION 3.24)
project(MediaPlayerInfo)

find_package(JNI)

set(CMAKE_CXX_STANDARD 17)

include_directories(${JNI_INCLUDE_DIRS})

add_library(MediaPlayerInfo SHARED native/main.cpp native/main.h)

set_target_properties(MediaPlayerInfo PROPERTIES VS_WINRT_COMPONENT TRUE)
target_link_libraries(MediaPlayerInfo WindowsApp.lib JNI::JNI)

add_executable(Test native/main.cpp native/main.h)

set_target_properties(Test PROPERTIES VS_WINRT_COMPONENT TRUE)
target_link_libraries(Test WindowsApp.lib JNI::JNI)
