#pragma once

#include "Scripting.h"
#include "Model.h"
#include "ModelRenderer.h"
#include "ModelFactory.h"
#include "Unit.h"
#include "UnitRenderer.h"
#include "MyoWeaponRenderer.h"

#define NUM_UNITS 1000
#define UNITS_AREA 200

class MyoArRenderer {
private:
	SIZE _size;
	Scripting *_scripting;

	ModelRenderer *_modelRenderer;
	UnitRenderer *_unitRenderer;
	MyoWeaponRenderer *_myoWeaponRenderer;

	Model *_skyModel;
	Model *_floorModel;
	Model *_zombieModel;
	Model *_weaponModel;

	Unit **_units;

	void DrawSkyBox();
	void InitializePerspective(float *rotationMatrix);
	void DrawWorld();
	void InitializeHudPerspective();

public:
	MyoArRenderer(ModelFactory *modelFactory, Scripting *scripting);
	virtual ~MyoArRenderer();

	void InitializeViewport(SIZE size);
	void UpdateState(float x, float y, float z, float *rotationMatrix, float *myoRotationMatrix);
	void Draw(float x, float y, float z, float *rotationMatrix, float *myoRotationMatrix);
};
