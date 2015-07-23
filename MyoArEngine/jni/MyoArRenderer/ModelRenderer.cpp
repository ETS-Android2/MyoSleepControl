#include <GLES/gl.h>
#include <GLES2/gl2.h>

#include "ModelRenderer.h"

ModelRenderer::ModelRenderer()
{
}

ModelRenderer::~ModelRenderer() {
}

void ModelRenderer::RenderModel(Model *model)
{
    glEnable(GL_TEXTURE_2D);
    glBindTexture(GL_TEXTURE_2D, model->GetTextureID());

    glEnableClientState(GL_VERTEX_ARRAY);
    glVertexPointer(3, GL_FLOAT, 0, model->GetVertices());

    glEnableClientState(GL_TEXTURE_COORD_ARRAY);
    glTexCoordPointer(2, GL_FLOAT, 0, model->GetTextureCoords());

    glDrawElements(GL_TRIANGLES, model->GetNumIndices(), GL_UNSIGNED_SHORT, model->GetIndices());

    glDisableClientState(GL_VERTEX_ARRAY);
    glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    glDisable(GL_TEXTURE_2D);
}
