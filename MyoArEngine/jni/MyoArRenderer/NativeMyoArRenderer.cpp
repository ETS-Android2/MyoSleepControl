#include <jni.h>
#include <GLES/gl.h>
#include <GLES2/gl2.h>

#include "NativeMyoArRenderer.h"
#include "Scripting.h"
#include "GlHelper.h"
#include "DummyWorld.h"

DummyWorld *_dummyWorld;
Scripting *_scripting = nullptr;

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_onSurfaceCreated(
		JNIEnv * env, jobject thiz, jstring script, jint texIDAscii, jint texIDRasen, jint texIDSky)
{
	_dummyWorld = new DummyWorld(texIDSky, texIDRasen);
	_scripting = Scripting::GetInstance();
	_scripting->Init(texIDAscii);

	const char *nativeString = env->GetStringUTFChars(script, JNI_FALSE);
	_scripting->RunScript(nativeString);
	 env->ReleaseStringUTFChars(script, nativeString);
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_onSurfaceChanged(
		JNIEnv * env, jobject thiz, jint width, jint height)
{
	SIZE size;
	size.cx = width;
	size.cy = height;

	_dummyWorld->InitializeViewport(size);
	_scripting->SetUiSize(size);
}

JNIEXPORT void JNICALL
Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_draw(
	JNIEnv * env, jobject thiz,
	jfloat x, jfloat y, jfloat z,
	jfloat r1, jfloat r2, jfloat r3, jfloat r4,
	jfloat r5, jfloat r6, jfloat r7, jfloat r8,
	jfloat r9, jfloat r10, jfloat r11, jfloat r12,
	jfloat r13, jfloat r14, jfloat r15, jfloat r16)
{
	float rotationMatrix[] = {r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16};

	_dummyWorld->Draw(x, y, z, rotationMatrix);
	_scripting->RenderHUD();
}
