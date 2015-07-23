#pragma once

#include "Model.h"

class ModelFactory {
	int _texIDSky, _texIDRasen;

public:
	ModelFactory(int texIDSky, int texIDRasen);
	virtual ~ModelFactory();

	Model *CreateSkyModel();
	Model *CreateFloorModel();
	Model *CreateZombieModel();
	Model *CreateWeaponModel();
};
