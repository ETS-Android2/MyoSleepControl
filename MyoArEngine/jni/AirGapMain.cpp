#include <jni.h>

extern "C"{
	JNIEXPORT jstring JNICALL Java_de_nachregenkommtsonne_myoarengine_C_getMessage (JNIEnv * env, jobject jObj, jshortArray samples);
}

JNIEXPORT jstring JNICALL Java_de_nachregenkommtsonne_myoarengine_C_getMessage (JNIEnv * env, jobject jObj, jshortArray samples)
{
	return env->NewStringUTF("FF1");
}
