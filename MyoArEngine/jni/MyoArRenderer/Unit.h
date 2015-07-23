#pragma once

#include "Model.h"

class Unit
{
private:
	Model *_model;
	float _positionX;
	float _positionY;
	float _positionZ;

	bool _alive;

public:
	Unit(Model *model, float posX, float posY, float posZ);
	virtual ~Unit();

	Model *GetModel();
	float GetX();
	float GetY();
	float GetZ();

	void Kill();
	bool IsAlive();
};
