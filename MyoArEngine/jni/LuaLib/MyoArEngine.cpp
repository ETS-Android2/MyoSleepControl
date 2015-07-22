#include <jni.h>
#include <GLES2/gl2.h>

#include "Scripting.h"
#include "env.h"

Scripting *_scripting = nullptr;


JNIEnv * global_env;

extern "C" {
JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onSurfaceCreated(
		JNIEnv * env, jobject thiz, jstring script, jint texIDAscii, jint texIDRasen, jint texIDSky);

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onSurfaceChanged(
		JNIEnv * env, jobject thiz, jint width, jint height);

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onDrawFrame(
		JNIEnv * env, jobject thiz);
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onSurfaceCreated(
		JNIEnv * env, jobject thiz, jstring script, jint texIDAscii, jint texIDRasen, jint texIDSky) {

	_scripting = Scripting::GetInstance();
	_scripting->Init(texIDAscii);

	const char *nativeString = env->GetStringUTFChars(script, JNI_FALSE);
	_scripting->RunScript(nativeString);
	 env->ReleaseStringUTFChars(script, nativeString);


	/*_scripting->RunScript(" \
	Label = CreateLabel(); \
	Label:SetSize(450, 200); \
	Label:SetPosition(10, 10); \
	Label:SetAnchor(\"ANCHOR_BOTTOM_LEFT\", \"ANCHOR_BOTTOM_LEFT\"); \
	Label:SetText(\"testtest testtest test test test\"); \
	Label:Show(); \
			\
			\
");*/
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onSurfaceChanged(
		JNIEnv * env, jobject thiz, jint width, jint height) {

	SIZE s;
	s.cx = width;
	s.cy = height;

	_scripting->SetUiSize(s);
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onDrawFrame(
		JNIEnv * env, jobject thiz) {

	//glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
	//glClear(GL_COLOR_BUFFER_BIT);

	_scripting->RenderHUD();
}
