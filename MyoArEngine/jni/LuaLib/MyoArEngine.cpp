#include <jni.h>
//#include <GLES2/gl2.h>

extern "C" {
  JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_JNIWrapper_getMessage(JNIEnv * env, jclass cls);
}
/*JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_JNIWrapper_on_1surface_1changed(
		JNIEnv * env, jclass cls, jint width, jint height) {
	//on_surface_changed();
}

JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_JNIWrapper_on_1draw_1frame(
		JNIEnv * env, jclass cls) {
	//on_draw_frame();

	//glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
	//glClear(GL_COLOR_BUFFER_BIT);
}*/



JNIEXPORT void JNICALL Java_de_nachregenkommtsonne_myoarengine_JNIWrapper_getMessage(JNIEnv * env, jclass cls) {
	//on_surface_created();
}
