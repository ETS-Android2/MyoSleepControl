#include <jni.h>
#include <GLES2/gl2.h>

#include <jni.h>

extern "C"{
	JNIEXPORT jstring JNICALL Java_de_nachregenkommtsonne_myoarengine_C_getMessage (JNIEnv * env, jobject jObj, jshortArray samples);
}

JNIEXPORT jstring JNICALL Java_de_nachregenkommtsonne_myoarengine_C_getMessage (JNIEnv * env, jobject jObj, jshortArray samples)
{
	glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
	glClear(GL_COLOR_BUFFER_BIT);

	return env->NewStringUTF("FF1");
}
