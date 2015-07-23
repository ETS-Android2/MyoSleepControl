#include <GLES/gl.h>
#include <GLES2/gl2.h>

#include "DummyWorld.h"
#include "Scripting.h"

DummyWorld::DummyWorld(int texIDSky, int texIDRasen) {
	_texIDSky = texIDSky;
	_texIDRasen = texIDRasen;
}

DummyWorld::~DummyWorld() {
}

void DummyWorld::InitializeViewport(SIZE size)
{
	_size = size;

	glViewport(0, 0, _size.cx, _size.cy);

	glEnable(GL_POINT_SMOOTH);
	glHint(GL_POINT_SMOOTH_HINT, GL_NICEST);

}

void drawTriangles(int texID, float *vertices, float *textures, unsigned short *indices, unsigned int numVertices)
{
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, texID);

    glEnableClientState(GL_VERTEX_ARRAY);
    glVertexPointer(3, GL_FLOAT, 0, vertices);

    glEnableClientState(GL_TEXTURE_COORD_ARRAY);
    glTexCoordPointer(2, GL_FLOAT, 0, textures);

    glDrawElements(GL_TRIANGLES, numVertices, GL_UNSIGNED_SHORT, indices);

    glDisableClientState(GL_VERTEX_ARRAY);
    glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    glDisable(GL_TEXTURE_2D);
}

void drawQuad(int texID, float *vertices, float *textures)
{
	unsigned short indices[] = {0,1,2,2,3,0};

	drawTriangles(texID, vertices, textures, indices, 6);
}

void perspectiveGL( float fovY, float aspect, float zNear, float zFar )
{
    float fW, fH;

    fH = 1.0f * zNear;
    fW = fH * aspect;

    glFrustumf( -fW, fW, -fH, fH, zNear, zFar );
}


void DummyWorld::Draw(float x, float y, float z, float *rotationMatrix)
{
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

}
