#include <jni.h>
#include <GLES/gl.h>
#include <GLES2/gl2.h>

#include "NativeMyoArRenderer.h"
#include "Scripting.h"
#include "GlHelper.h"

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

void drawQuad(int texID, float *vertices, float *textures)
{
	unsigned short indices[] = {0,1,2,2,3,0};

    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, texID);

    glEnableClientState(GL_VERTEX_ARRAY);
    glVertexPointer(3, GL_FLOAT, 0, vertices);

    glEnableClientState(GL_TEXTURE_COORD_ARRAY);
    glTexCoordPointer(2, GL_FLOAT, 0, textures);

    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_SHORT, indices);

    glDisableClientState(GL_VERTEX_ARRAY);
    glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    glDisable(GL_TEXTURE_2D);
}

JNIEXPORT void JNICALL
Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_drawSkyBox(
	JNIEnv * env, jobject thiz)
{
  	//sky
	float vertices1[] = {
			100.0f, 100.0f, 49.0f,
			100.0f, -100.0f, 49.0f,
			-100.0f, -100.0f, 49.0f,
			-100.0f, 100.0f, 49.0f};

	float textures1[] = {
			.25f, .25f,
			.25f, .75f,
			.75f, .75f,
			.75f, .25f
	};

  	drawQuad(3, vertices1, textures1);

  	//4 sky sides
  	// x = 100

	float vertices2[] = {
			100.0f, 100.0f, 49.0f,
			100.0f, -100.0f, 49.0f,
			100.0f, -100.0f, -1.0f,
			100.0f, 100.0f, -1.0f};

	float textures2[] = {
			.25f, .25f,
			.25f, .75f,
			.0f, .75f,
			.0f, .25f
	};

	drawQuad(3, vertices2, textures2);

  	// y = -100

	float vertices3[] = {
			100.0f, -100.0f, -1.0f,
			100.0f, -100.0f, 49.0f,
			-100.0f, -100.0f, 49.0f,
			-100.0f, -100.0f, -1.0f};

	float textures3[] = {
			.25f, 1.f,
			.25f, .75f,
			.75f, .75f,
			.75f, 1.f
	};

	drawQuad(3, vertices3, textures3);
//  			new Vector3D(100.0f, -100.0f, -1.0f), new Vector2D(.25f, 1.f),
//  			new Vector3D(100.0f, -100.0f, 49.0f), new Vector2D(.25f, .75f),
//  			new Vector3D(-100.0f, -100.0f, 49.0f), new Vector2D(.75f, .75f),
//  			new Vector3D(-100.0f, -100.0f, -1.0f), new Vector2D(.75f, 1.f));

  	//x = -100
	float vertices4[] = {
			-100.0f, 100.0f, -1.0f,
			-100.0f, -100.0f, -1.0f,
			-100.0f, -100.0f, 49.0f,
			-100.0f, 100.0f, 49.0f};

	float textures4[] = {
			1.f, .25f,
			1.f, .75f,
			.75f, .75f,
			.75f, .25f
	};

	drawQuad(3, vertices4, textures4);
//  			new Vector3D(-100.0f, 100.0f, -1.0f), new Vector2D(1.f, .25f),
//  			new Vector3D(-100.0f, -100.0f, -1.0f), new Vector2D(1.f, .75f),
//  			new Vector3D(-100.0f, -100.0f, 49.0f), new Vector2D(.75f, .75f),
//  			new Vector3D(-100.0f, 100.0f, 49.0f), new Vector2D(.75f, .25f));

  	//y = 100
	float vertices5[] = {
			100.0f, 100.0f, 49.0f,
			100.0f, 100.0f, -1.0f,
			-100.0f, 100.0f, -1.0f,
			-100.0f, 100.0f, 49.0f};

	float textures5[] = {
			.25f, .25f,
			.25f, .0f,
			.75f, .0f,
			.75f, .25f
	};

	drawQuad(3, vertices5, textures5);
 // 			new Vector3D(100.0f, 100.0f, 49.0f), new Vector2D(.25f, .25f),
 // 			new Vector3D(100.0f, 100.0f, -1.0f), new Vector2D(.25f, .0f),
 // 			new Vector3D(-100.0f, 100.0f, -1.0f), new Vector2D(.75f, .0f),
 // 			new Vector3D(-100.0f, 100.0f, 49.0f), new Vector2D(.75f, .25f));
}

JNIEXPORT void JNICALL
Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_drawWorld(
	JNIEnv * env, jobject thiz)
{

}
