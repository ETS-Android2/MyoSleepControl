#include <GLES/gl.h>
#include <GLES2/gl2.h>

#include "MyoWeaponRenderer.h"

MyoWeaponRenderer::MyoWeaponRenderer(ModelRenderer *modelRenderer) {
	_modelRenderer = modelRenderer;
}

MyoWeaponRenderer::~MyoWeaponRenderer() {
}

void MyoWeaponRenderer::Render(Model *model, float *myoRotationMatrix) {


	glMultMatrixf(myoRotationMatrix);

	glScalef(0.01f, 0.01f, 0.01f);
	glTranslatef(100.0f, 0.0f, 0.0f);

	_modelRenderer->RenderModel(model);
}
