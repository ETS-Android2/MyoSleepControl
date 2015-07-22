#include <jni.h>
#include <GLES2/gl2.h>

#include "LuaHud.h"
#include "Scripting.h"

Scripting *_scripting = nullptr;

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_LuaHud_onSurfaceCreated(
		JNIEnv * env, jobject thiz, jstring script, jint texIDAscii, jint texIDRasen, jint texIDSky)
{
	_scripting = Scripting::GetInstance();
	_scripting->Init(texIDAscii);

	const char *nativeString = env->GetStringUTFChars(script, JNI_FALSE);
	_scripting->RunScript(nativeString);
	 env->ReleaseStringUTFChars(script, nativeString);
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_LuaHud_onSurfaceChanged(
		JNIEnv * env, jobject thiz, jint width, jint height)
{
	SIZE s;
	s.cx = width;
	s.cy = height;

	_scripting->SetUiSize(s);
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_LuaHud_onDrawFrame(
		JNIEnv * env, jobject thiz)
{
	_scripting->RenderHUD();
}
