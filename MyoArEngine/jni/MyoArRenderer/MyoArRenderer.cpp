#include <GLES/gl.h>
#include <GLES2/gl2.h>

#include "MyoArRenderer.h"
#include "Scripting.h"
#include "Model.h"
#include "ModelRenderer.h"
#include "ModelFactory.h"
#include "Unit.h"
#include "UnitRenderer.h"

#include <stdlib.h>

MyoArRenderer::MyoArRenderer(ModelFactory *modelFactory, Scripting *scripting) {
	_scripting = scripting;

	_modelRenderer = new ModelRenderer();
	_unitRenderer = new UnitRenderer(_modelRenderer);
	_myoWeaponRenderer = new MyoWeaponRenderer(_modelRenderer);

	_skyModel = modelFactory->CreateSkyModel();
	_floorModel = modelFactory->CreateFloorModel();
	_zombieModel = modelFactory->CreateZombieModel();
	_weaponModel = modelFactory->CreateWeaponModel();

	_units = new Unit *[NUM_UNITS];

	for (int i = 0; i < NUM_UNITS; i++)
	{
		int x = rand() % (2*UNITS_AREA) - UNITS_AREA;
		int y = rand() % (2*UNITS_AREA) - UNITS_AREA;
		_units[i] = new Unit(_zombieModel, x, y, 0.0f);
	}


	//TODO: 3 Waffen: _Schwert_, Zauberstab, Feuerwaffe
	//Mehr Einheiten
	//Highscore
}

MyoArRenderer::~MyoArRenderer() {
}

void MyoArRenderer::InitializeViewport(SIZE size)
{
	_size = size;

	glViewport(0, 0, _size.cx, _size.cy);

	int z = size.cy;
	size.cy = size.cx;
	size.cx = z;

	_scripting->SetUiSize(size);
}

void MyoArRenderer::DrawSkyBox()
{
	_modelRenderer->RenderModel(_skyModel);
}

void MyoArRenderer::InitializePerspective(float *rotationMatrix)
{
	glEnable(GL_DEPTH_TEST);
	glMatrixMode(GL_PROJECTION);
	glLoadIdentity();

	float fW = 0.1f * _size.cx / _size.cy;
	glFrustumf( -fW, fW, -0.1f, 0.1f, 0.1f, 200.0f);

	glMatrixMode(GL_MODELVIEW);
	glLoadMatrixf(rotationMatrix);
}


void MyoArRenderer::UpdateState(
/*player position:*/float x, float y, float z,
	float *rotationMatrix,
	float *myoRotationMatrix)
{
	float width = 1.0f;

	float weaponX = x + myoRotationMatrix[0];
	float weaponY = y + myoRotationMatrix[1];

	for (int i = 0; i < NUM_UNITS; i++)
	{
		float unitX = _units[i]->GetX();
		float unitY = _units[i]->GetY();

		if (weaponX < unitX + width && weaponX > unitX - width &&
				weaponY < unitY + width && weaponY > unitY - width)
		{
			_units[i]->Kill();
		}
	}

	_scripting->PostLogEvent("REDRAW");
}

void MyoArRenderer::Draw(float x, float y, float z, float *rotationMatrix, float *myoRotationMatrix)
{
	glClearColor(0.3f, 0.0f, 0.0f, 1.0f);
	glClear(GL_COLOR_BUFFER_BIT);
	glClear(GL_DEPTH_BUFFER_BIT);

	InitializePerspective(rotationMatrix);
	DrawSkyBox();

	glPushMatrix();
	glTranslatef(-x, -y, -z);
	DrawWorld();

	glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
	for (int i = 0; i < NUM_UNITS; i++)
	{
		_unitRenderer->Render(_units[i]);
	}
	glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

	glPopMatrix();

	glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
	_myoWeaponRenderer->Render(_weaponModel, myoRotationMatrix);
	glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

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
