#pragma once

#include "Scripting.h"
#include "Model.h"
#include "ModelRenderer.h"
#include "ModelFactory.h"
#include "Unit.h"
#include "UnitRenderer.h"

class MyoArRenderer {
private:
	SIZE _size;
	Scripting *_scripting;
	ModelRenderer *_modelRenderer;
	UnitRenderer *_unitRenderer;
	Model *_skyModel;
	Model *_floorModel;
	Model *_zombieModel;
	Unit *_zombieUnit;

	void DrawSkyBox();
	void InitializePerspective(float *rotationMatrix);
	void DrawWorld();
	void InitializeHudPerspective();

public:
	MyoArRenderer(ModelFactory *modelFactory, Scripting *scripting);
	virtual ~MyoArRenderer();

	void InitializeViewport(SIZE size);
	void Draw(float x, float y, float z, float *rotationMatrix);
};
