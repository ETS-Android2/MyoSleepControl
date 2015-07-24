#pragma once

#include "Model.h"
#include "ModelRenderer.h"

class MyoWeaponRenderer
{
private:
	ModelRenderer *_modelRenderer;

public:
	MyoWeaponRenderer(ModelRenderer *modelRenderer);
	virtual ~MyoWeaponRenderer();

	void Render(Model *model, float *myoRotationMatrix, bool inverse);
};
