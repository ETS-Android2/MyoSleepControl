#include <GLES/gl.h>
#include <GLES2/gl2.h>

#include "MyoWeaponRenderer.h"

MyoWeaponRenderer::MyoWeaponRenderer(ModelRenderer *modelRenderer) {
	_modelRenderer = modelRenderer;
}

MyoWeaponRenderer::~MyoWeaponRenderer() {
}

void MyoWeaponRenderer::Render(Model *model, float *myoRotationMatrix) {

	glPushMatrix();
	{
		glMultMatrixf(myoRotationMatrix);

		glScalef(-0.1f, -0.1f, -0.1f);
		glTranslatef(10.0f, 0.0f, 0.0f);


		_modelRenderer->RenderModel(model);
	}
	glPopMatrix();
}
