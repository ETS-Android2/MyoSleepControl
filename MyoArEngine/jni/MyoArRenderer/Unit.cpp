#include "Unit.h"

#include "Model.h"

Unit::Unit(Model *model, float posX, float posY, float posZ)
{
	_model = model;
	_positionX = posX;
	_positionY = posY;
	_positionZ = posZ;

	_alive = true;
}

Unit::~Unit()
{
}

Model *Unit::GetModel()
{
	return _model;
}

float Unit::GetX()
{
	return _positionX;
}

float Unit::GetY()
{
	return _positionY;
}

float Unit::GetZ()
{
	return _positionZ;
}

void Unit::Kill()
{
	_alive = false;
}

bool Unit::IsAlive()
{
	return _alive;
}
