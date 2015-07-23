#pragma once

#include "Model.h"

class ModelRenderer {
public:
	ModelRenderer();
	virtual ~ModelRenderer();

	void RenderModel(Model *model);

};

