#ifndef LUALIB_GLHELPER_H_
#define LUALIB_GLHELPER_H_

#include "uielement.h"

class GlHelper {
public:
	GlHelper();

	void DrawQuad(Dimension *dim);
	void DrawQuadWithTexture(Dimension *dim, int texID, DimensionF *texDim);
	void DrawText(const unsigned char *text, int x, int y);
};

#endif /* LUALIB_GLHELPER_H_ */
