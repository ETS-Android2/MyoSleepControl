#include "UnitRenderer.h"

#include <GLES/gl.h>
#include <GLES2/gl2.h>

UnitRenderer::UnitRenderer(ModelRenderer *modelRenderer)
{
	_modelRenderer = modelRenderer;
}

UnitRenderer::~UnitRenderer()
{
}

void UnitRenderer::Render(Unit *unit)
{
	glPushMatrix();
	{
		glTranslatef(unit->GetX(), unit->GetY(), unit->GetZ());
		_modelRenderer->RenderModel(unit->GetModel());
	}
	glPopMatrix();
}
