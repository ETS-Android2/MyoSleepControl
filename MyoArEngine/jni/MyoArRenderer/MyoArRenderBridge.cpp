#include <jni.h>
#include <GLES/gl.h>
#include <GLES2/gl2.h>

#include "MyoArRenderBridge.h"
#include "Scripting.h"
#include "GlHelper.h"
#include "MyoArRenderer.h"
#include "ModelFactory.h"

MyoArRenderer *_myoArRenderer;

JNIEXPORT void JNICALL
Java_de_nachregenkommtsonne_myoarengine_MyoArRenderBridge_onSurfaceCreated(
		JNIEnv * env, jobject thiz, jstring script, jint texIDAscii, jint texIDRasen, jint texIDSky)
{
	Scripting *scripting = Scripting::GetInstance();
	scripting->Init(texIDAscii);

	const char *nativeString = env->GetStringUTFChars(script, JNI_FALSE);
	scripting->RunScript(nativeString);
	env->ReleaseStringUTFChars(script, nativeString);

	ModelFactory *modelFactory = new ModelFactory(texIDSky, texIDRasen);
	_myoArRenderer = new MyoArRenderer(modelFactory, scripting);
	delete modelFactory;
}

JNIEXPORT void JNICALL
Java_de_nachregenkommtsonne_myoarengine_MyoArRenderBridge_onSurfaceChanged(
		JNIEnv * env, jobject thiz, jint width, jint height)
{
	SIZE size;
	size.cx = width;
	size.cy = height;

	_myoArRenderer->InitializeViewport(size);
}

JNIEXPORT void JNICALL
Java_de_nachregenkommtsonne_myoarengine_MyoArRenderBridge_draw(
	JNIEnv * env, jobject thiz,
	jfloat x, jfloat y, jfloat z,
	jfloat r1, jfloat r2, jfloat r3, jfloat r4,
	jfloat r5, jfloat r6, jfloat r7, jfloat r8,
	jfloat r9, jfloat r10, jfloat r11, jfloat r12,
	jfloat r13, jfloat r14, jfloat r15, jfloat r16)
{
	float rotationMatrix[] = {r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16};

	_myoArRenderer->Draw(x, y, z, rotationMatrix);
}
