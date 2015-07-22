#pragma once

#include <jni.h>

extern "C"
{
	JNIEXPORT void JNICALL
	Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_onSurfaceCreated(
		JNIEnv * env, jobject thiz, jstring script, jint texIDAscii, jint texIDRasen, jint texIDSky);

	JNIEXPORT void JNICALL
	Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_onSurfaceChanged(
		JNIEnv * env, jobject thiz, jint width, jint height);

	JNIEXPORT void JNICALL
	Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_onDrawHud(
		JNIEnv * env, jobject thiz);

	JNIEXPORT void JNICALL
	Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_drawSkyBox(
		JNIEnv * env, jobject thiz);

	JNIEXPORT void JNICALL
	Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_drawWorld(
		JNIEnv * env, jobject thiz);
}
