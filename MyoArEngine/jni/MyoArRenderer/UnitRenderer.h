#pragma once

#include "Unit.h"
#include "ModelRenderer.h"

class UnitRenderer
{
private:
	ModelRenderer *_modelRenderer;
public:
	UnitRenderer(ModelRenderer *modelRenderer);
	virtual ~UnitRenderer();

	void Render(Unit *unit);
};
