#include "GlHelper.h"
#include <GLES/gl.h>
#include <android/asset_manager.h>
#include "env.h"

GlHelper::GlHelper() {
	// TODO Auto-generated constructor stub

}

void GlHelper::DrawQuad(Dimension dim)
{
    //glEnableClientState(GL_COLOR_ARRAY);

    short vertices[] = {
    		(short) dim.x, (short) dim.y,
    		(short) (dim.x + dim.width), (short) dim.y,
    		(short) (dim.x + dim.width), (short) (dim.y + dim.height),
    		(short) dim.x, (short) (dim.y + dim.height)
    };

    glVertexPointer(2, GL_SHORT, 0, vertices);

    /*float colores[] = {
    		1.0f, 1.0f, 1.0f, 1.0f,
    		1.0f, 1.0f, 1.0f, 1.0f,
    		1.0f, 1.0f, 1.0f, 1.0f,
    		1.0f, 1.0f, 1.0f, 1.0f
    };

    glColorPointer(4, GL_FLOAT, 0, colores);*/

	float texCoords[] = {
			0.f, 0.f,
			0.f, 1.f,
			1.f, 1.f,
			1.f, 1.f,
			1.f, 0.f,
			0.f, 0.f
	};

    glEnableClientState(GL_TEXTURE_COORD_ARRAY);
	glTexCoordPointer(2, GL_FLOAT, 8, texCoords);

    unsigned short indices[] =
    {
    		0,1,2,2,3,0
    };

    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_SHORT, indices);

    //glDisableClientState(GL_COLOR_ARRAY);
}

void GlHelper::DrawText(const unsigned char *text, int x, int y)
{
	int texID = 1;

	//glEnable(GL_TEXTURE_2D);
    //glEnableClientState(GL_TEXTURE_COORD_ARRAY);
//	glBindTexture(GL_TEXTURE_2D, texID);
//	glFrontFace(GL_CW);

	Dimension dim;
	dim.x = 100;
	dim.y = 100;
	dim.height = 100;
	dim.width = 100;


	float texCoords[] = {
			0.f, 0.f,
			0.f, 100.f,
			100.f, 100.f,
			100.f, 100.f,
			100.f, 0.f,
			0.f, 0.f
	};

//	glTexCoordPointer(2, GL_FLOAT, 8, texCoords);

	DrawQuad(dim);

//	glDisableClientState(GL_TEXTURE_COORD_ARRAY);
//	glDisable(GL_TEXTURE_2D);

}
