#include <jni.h>
#include <GLES2/gl2.h>

#include "Scripting.h"
#include "env.h"

Scripting *_scripting = nullptr;


JNIEnv * global_env;

extern "C" {
JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onSurfaceCreated(
		JNIEnv * env);

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onSurfaceChanged(
		JNIEnv * env, jint width, jint height);

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onDrawFrame(
		JNIEnv * env);
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onSurfaceCreated(
		JNIEnv * env) {

	_scripting = Scripting::GetInstance();
	_scripting->Init();

	_scripting->RunScript(" \
	Label = CreateLabel(); \
	Label:SetSize(450, 200); \
	Label:SetPosition(10, 10); \
	Label:SetAnchor(\"ANCHOR_BOTTOM_LEFT\", \"ANCHOR_BOTTOM_LEFT\"); \
	Label:SetText(\"testtest testtest test test test\"); \
	Label:Show(); \
			\
			\
");
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onSurfaceChanged(
		JNIEnv * env, jint width, jint height) {

	SIZE s;
	s.cx = width;
	s.cy = height;

	_scripting->SetUiSize(s);
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_C_onDrawFrame(
		JNIEnv * env) {

	//glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
	//glClear(GL_COLOR_BUFFER_BIT);

	_scripting->RenderHUD();
}
