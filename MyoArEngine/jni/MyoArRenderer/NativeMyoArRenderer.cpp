#include <jni.h>
#include <GLES2/gl2.h>

#include "NativeMyoArRenderer.h"
#include "Scripting.h"

Scripting *_scripting = nullptr;
SIZE _size;
int _texIDRasen;
int _texIDSky;

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_onSurfaceCreated(
		JNIEnv * env, jobject thiz, jstring script, jint texIDAscii, jint texIDRasen, jint texIDSky)
{
	_scripting = Scripting::GetInstance();
	_scripting->Init(texIDAscii);
	_texIDRasen = texIDRasen;
	_texIDSky = texIDSky;

	const char *nativeString = env->GetStringUTFChars(script, JNI_FALSE);
	_scripting->RunScript(nativeString);
	 env->ReleaseStringUTFChars(script, nativeString);
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_onSurfaceChanged(
		JNIEnv * env, jobject thiz, jint width, jint height)
{

	_size.cx = width;
	_size.cy = height;

	_scripting->SetUiSize(_size);
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_onDrawHud(
		JNIEnv * env, jobject thiz)
{
	_scripting->RenderHUD();
}

JNIEXPORT void JNICALL
Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_drawSkyBox(
	JNIEnv * env, jobject thiz)
{

}

JNIEXPORT void JNICALL
Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_drawWorld(
	JNIEnv * env, jobject thiz)
{

}
