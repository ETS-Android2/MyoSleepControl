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

	glViewport(0, 0, _size.cx, _size.cy);

	glEnable(GL_POINT_SMOOTH);
	glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);

	_scripting->SetUiSize(_size);
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

void perspectiveGL( float fovY, float aspect, float zNear, float zFar )
{
    float fW, fH;

    fH = 1.0f * zNear;
    fW = fH * aspect;

    glFrustumf( -fW, fW, -fH, fH, zNear, zFar );
}

JNIEXPORT void JNICALL
Java_de_nachregenkommtsonne_myoarengine_NativeMyoArRenderer_draw(
	JNIEnv * env, jobject thiz,
	jfloat x, jfloat y, jfloat z,
	jfloat r1, jfloat r2, jfloat r3, jfloat r4,
	jfloat r5, jfloat r6, jfloat r7, jfloat r8,
	jfloat r9, jfloat r10, jfloat r11, jfloat r12,
	jfloat r13, jfloat r14, jfloat r15, jfloat r16)
{
	float rotationMatrix[] = {r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16};

	glClearColor(0.3f, 0.0f, 0.0f, 1.0f);
	glClear(GL_COLOR_BUFFER_BIT);
	glClear(GL_DEPTH_BUFFER_BIT);

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	perspectiveGL(90.0f, (float)  _size.cx / _size.cy, 0.1f, 200.0f);

	glMatrixMode(GL_MODELVIEW);
	glLoadMatrixf(rotationMatrix);

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

	drawQuad(_texIDSky, vertices1, textures1);

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

	drawQuad(_texIDSky, vertices2, textures2);

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

	drawQuad(_texIDSky, vertices3, textures3);

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

	drawQuad(_texIDSky, vertices4, textures4);

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

	drawQuad(_texIDSky, vertices5, textures5);


	glTranslatef(x, y, z);

	float vertices[] = {
			10000.0f, 10000.0f, -1.0f,
			10000.0f, -10000.0f, -1.0f,
			-10000.0f, -10000.0f, -1.0f,
			-10000.0f, 10000.0f, -1.0f};

	float textures[] = {
			0.f, 0.f,
			2500.f, 0.f,
			2500.f, 2500.f,
			0.f, 2500.f
	};

	drawQuad(_texIDRasen, vertices, textures);

	glDisable(GL_DEPTH_TEST);
	glClear(GL_DEPTH_BUFFER_BIT);

	glLoadIdentity();

	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();
	glOrthof(0.0f, (float)_size.cx, 0.0f, (float)_size.cy, -1.0f, 1.0f); // {0,0} ist unten links
	glMatrixMode(GL_MODELVIEW);

	glRotatef(-90, 0, 0, 1);
	glTranslatef(-_size.cy, 0, 0);

	_scripting->RenderHUD();
}
