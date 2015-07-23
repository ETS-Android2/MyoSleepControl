#include <GLES/gl.h>
#include <GLES2/gl2.h>

#include "MyoArRenderer.h"
#include "Scripting.h"
#include "Model.h"
#include "ModelRenderer.h"

MyoArRenderer::MyoArRenderer(int texIDSky, int texIDRasen, Scripting *scripting) {
	_texIDSky = texIDSky;
	_texIDRasen = texIDRasen;
	_scripting = scripting;

	_modelRenderer = new ModelRenderer();

	{
		float vertices[] = {
				100.0f, 100.0f, -1.0f,
				100.0f, -100.0f, -1.0f,

				100.0f, 100.0f, -1.0f,
				100.0f, 100.0f, 49.0f,
				100.0f, -100.0f, 49.0f,
				100.0f, -100.0f, -1.0f,

				-100.0f, 100.0f, -1.0f,
				-100.0f, 100.0f, 49.0f,
				-100.0f, -100.0f, 49.0f,
				-100.0f, -100.0f, -1.0f,

				-100.0f, 100.0f, -1.0f,
				-100.0f, -100.0f, -1.0f
		};

		float textures[] = {
				0.00f, 0.25f,
				0.00f, 0.75f,

				0.25f, 0.00f,
				0.25f, 0.25f,
				0.25f, 0.75f,
				0.25f, 1.00f,

				0.75f, 0.00f,
				0.75f, 0.25f,
				0.75f, 0.75f,
				0.75f, 1.00f,

				1.00f, 0.25f,
				1.00f, 0.75f,
		};

		unsigned short indices[] = {
				0,3,4, 4,1,0,
				2,6,7, 7,3,2,
				3,7,8, 8,4,3,
				4,8,9, 9,5,4,
				7,10,11, 11,8,7
		};

		_skyModel = new Model(_texIDSky, vertices, textures, indices, 30);
	}

	{
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

		unsigned short indices[] = {0,1,2,2,3,0};

		_floorModel = new Model(_texIDRasen, vertices, textures, indices, 6);
	}
}
MyoArRenderer::~MyoArRenderer() {
}

void MyoArRenderer::InitializeViewport(SIZE size)
{
	_size = size;

	glViewport(0, 0, _size.cx, _size.cy);

	_scripting->SetUiSize(size);
}

void MyoArRenderer::DrawSkyBox()
{
	_modelRenderer->RenderModel(_skyModel);
}

void MyoArRenderer::InitializePerspective(float *rotationMatrix)
{
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	float fW = 0.1f * _size.cx / _size.cy;
	glFrustumf( -fW, fW, -0.1f, 0.1f, 0.1f, 200.0f);

	glMatrixMode(GL_MODELVIEW);
	glLoadMatrixf(rotationMatrix);
}

void MyoArRenderer::Draw(float x, float y, float z, float *rotationMatrix)
{
	glClearColor(0.3f, 0.0f, 0.0f, 1.0f);
	glClear(GL_COLOR_BUFFER_BIT);
	glClear(GL_DEPTH_BUFFER_BIT);

	InitializePerspective(rotationMatrix);
	DrawSkyBox();

	glTranslatef(x, y, z);
	DrawWorld();

	InitializeHudPerspective();
	_scripting->RenderHUD();
}

void MyoArRenderer::DrawWorld()
{
	_modelRenderer->RenderModel(_floorModel);
}

void MyoArRenderer::InitializeHudPerspective()
{
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
