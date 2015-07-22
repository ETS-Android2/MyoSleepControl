#include "GlHelper.h"
#include <GLES/gl.h>
#include "env.h"

GlHelper::GlHelper() {
}

void GlHelper::DrawQuad(Dimension *dim)
{
	short vertices[] = {
	    		(short)  dim->x, (short) dim->y,
	    		(short) (dim->x + dim->width), (short) dim->y,
	    		(short) (dim->x + dim->width), (short) (dim->y + dim->height),
	    		(short)  dim->x, (short) (dim->y + dim->height)
	    };

    unsigned short indices[] = {
    		0,1,2,2,3,0
    };

	glEnableClientState(GL_VERTEX_ARRAY);
    glVertexPointer(2, GL_SHORT, 0, vertices);

    glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_SHORT, indices);

    glDisableClientState(GL_VERTEX_ARRAY);
}

void GlHelper::DrawQuadWithTexture(Dimension *dim, int texID, DimensionF *texDim)
{
	short vertices[] = {
	    		(short)  dim->x, (short) dim->y,
	    		(short) (dim->x + dim->width), (short) dim->y,
	    		(short) (dim->x + dim->width), (short) (dim->y + dim->height),
	    		(short)  dim->x, (short) (dim->y + dim->height)
	    };

	float texCoords[] = {
	    		texDim->x, texDim->y + texDim->height,
	    		texDim->x + texDim->width, texDim->y + texDim->height,
	    		texDim->x + texDim->width, texDim->y,
	    		texDim->x, texDim->y,
	    };

    unsigned short indices[] = {
    		0,1,2,2,3,0
    };

    glEnable(GL_TEXTURE_2D);
	glBindTexture(GL_TEXTURE_2D, texID);

	glEnableClientState(GL_VERTEX_ARRAY);
	glVertexPointer(2, GL_SHORT, 0, vertices);

	glEnableClientState(GL_TEXTURE_COORD_ARRAY);
	glTexCoordPointer(2, GL_FLOAT, 0, texCoords);

	glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_SHORT, indices);

    glDisableClientState(GL_VERTEX_ARRAY);
    glDisableClientState(GL_TEXTURE_COORD_ARRAY);
	glDisable(GL_TEXTURE_2D);
}

void GlHelper::DrawText(const unsigned char *text, int posx, int posy)
{
	int texID = 1;

	for (int i = 0; i < strlen((const char *)text); i++)
	{
		int n = text[i];

		if (n < ' ')
			n = '?';
		if (n > '~')
			n = '?';

		n = n - ' ';

		int col = n % 25;
		int row = n / 25;

		Dimension dim;
		dim.x = posx + i * 20;
		dim.y = posy;
		dim.height = 37;
		dim.width = 20;

		DimensionF texDim;
		texDim.x = 20.f / 512.f * col;
		texDim.y = 37.f / 256.f * row;
		texDim.width  = 20.f / 512.f;
		texDim.height = 37.f / 256.f;

		this->DrawQuadWithTexture(&dim, texID, &texDim);
	}
}
