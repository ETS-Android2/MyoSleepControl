#pragma once
#include "Scripting.h"

class DummyWorld {
private:
	SIZE _size;
	int _texIDSky, _texIDRasen;

public:
	DummyWorld(int texIDSky, int texIDRasen);
	virtual ~DummyWorld();

	void InitializeViewport(SIZE size);
	void Draw(float x, float y, float z, float *rotationMatrix);
};

