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
	Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_draw(
		JNIEnv * env, jobject thiz,
		jfloat x, jfloat y, jfloat z,
		jfloat r1, jfloat r2, jfloat r3, jfloat r4,
		jfloat r5, jfloat r6, jfloat r7, jfloat r8,
		jfloat r9, jfloat r10, jfloat r11, jfloat r12,
		jfloat r13, jfloat r14, jfloat r15, jfloat r16);
}
