#pragma once

#include <jni.h>

extern "C"
{
	JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_LuaHud_onSurfaceCreated(
		JNIEnv * env, jobject thiz, jstring script, jint texIDAscii, jint texIDRasen, jint texIDSky);

	JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_LuaHud_onSurfaceChanged(
		JNIEnv * env, jobject thiz, jint width, jint height);

	JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_LuaHud_onDrawFrame(
		JNIEnv * env, jobject thiz);
}
