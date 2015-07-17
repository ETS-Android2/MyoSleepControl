#include <jni.h>
#include <GLES2/gl2.h>

#include <jni.h>
#include "Scripting.h"

Scripting *_scripting = nullptr;

extern "C" {
JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onSurfaceCreated(
		JNIEnv * env, jobject jObj);

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onSurfaceChanged(
		JNIEnv * env, jobject jObj, jint width, jint height);

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onDrawFrame(
		JNIEnv * env, jobject jObj);
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onSurfaceCreated(
		JNIEnv * env, jobject jObj) {

	_scripting = Scripting::GetInstance();
	_scripting->Init();

	_scripting->RunScript(" \
	Label = CreateLabel(); \
	Label:SetSize(450, 200); \
	Label:SetPosition(10, 10); \
	Label:SetAnchor(\"ANCHOR_BOTTOM_LEFT\", \"ANCHOR_BOTTOM_LEFT\"); \
	Label:SetText(\"test\"); \
	Label:Show(); \
");
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onSurfaceChanged(
		JNIEnv * env, jobject jObj, jint width, jint height) {

	SIZE s;
	s.cx = width;
	s.cy = height;

	_scripting->SetUiSize(s);
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onDrawFrame(
		JNIEnv * env, jobject jObj) {

	//glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
	//glClear(GL_COLOR_BUFFER_BIT);

	_scripting->RenderHUD();
}
