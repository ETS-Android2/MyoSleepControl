#include "ModelFactory.h"
#include "Model.h"

ModelFactory::ModelFactory(int texIDSky, int texIDRasen) {
	_texIDSky = texIDSky;
	_texIDRasen = texIDRasen;
}

ModelFactory::~ModelFactory() {
}

Model *ModelFactory::CreateSkyModel()
{
	float vertices[] = {

			100.0f, 100.0f, -1.0f,
			100.0f, -100.0f, -1.0f,

			100.0f, 100.0f, -1.0f,
			100.0f, 100.0f, 49.0f,
			100.0f, -100.0f, 49.0f,
			100.0f, -100.0f, -1.0f,

			-100.0f, 100.0f, -1.0f,
			-100.0f, 100.0f, 49.0f,
			-100.0f, -100.0f, 49.0f,
			-100.0f, -100.0f, -1.0f,

			-100.0f, 100.0f, -1.0f,
			-100.0f, -100.0f, -1.0f
	};

	float textures[] = {
			0.00f, 0.25f,
			0.00f, 0.75f,

			0.25f, 0.00f,
			0.25f, 0.25f,
			0.25f, 0.75f,
			0.25f, 1.00f,

			0.75f, 0.00f,
			0.75f, 0.25f,
			0.75f, 0.75f,
			0.75f, 1.00f,

			1.00f, 0.25f,
			1.00f, 0.75f,
	};

	unsigned short indices[] = {
			0,3,4, 4,1,0,
			2,6,7, 7,3,2,
			3,7,8, 8,4,3,
			4,8,9, 9,5,4,
			7,10,11, 11,8,7
	};

	return new Model(_texIDSky, vertices, textures, indices, 30);
}

Model *ModelFactory::CreateFloorModel()
{
	float vertices[] = {
			10000.0f, 10000.0f, -1.0f,
			10000.0f, -10000.0f, -1.0f,
			-10000.0f, -10000.0f, -1.0f,
			-10000.0f, 10000.0f, -1.0f};

	float textures[] = {
			0.f, 0.f,
			2500.f, 0.f,
			2500.f, 2500.f,
			0.f, 2500.f
	};

	unsigned short indices[] = {0,1,2,2,3,0};

	return new Model(_texIDRasen, vertices, textures, indices, 6);
}

Model *ModelFactory::CreateZombieModel()
{
	float vertices[] = {
			-1.0f, -1.0f, -1.0f,
			-1.0f,  1.0f, -1.0f,
			 1.0f,  1.0f, -1.0f,
			 1.0f, -1.0f, -1.0f,

			-1.0f, -1.0f,  1.0f,
			-1.0f,  1.0f,  1.0f,
			 1.0f,  1.0f,  1.0f,
			 1.0f, -1.0f,  1.0f,
	};

	float textures[] = {
			0.0f, 0.0f,
			0.0f, 0.0f,
			0.0f, 0.0f,
			0.0f, 0.0f,

			0.0f, 0.0f,
			0.0f, 0.0f,
			0.0f, 0.0f,
			0.0f, 0.0f,
};

	unsigned short indices[] = {
			0,1,2, 2,3,0,
			4,5,6, 6,7,4,

			0,1,5, 5,4,0,
			3,4,7, 7,6,3,

			0,3,7, 7,4,0,
			1,2,6, 6,5,1
	};


	return new Model(0, vertices, textures, indices, 36);
}

Model *ModelFactory::CreateWeaponModel()
{
	float vertices[] = {
			-1.0f, -1.0f, -1.0f,
			-1.0f,  1.0f, -1.0f,
			 1.0f,  1.0f, -1.0f,
			 1.0f, -1.0f, -1.0f,

			-1.0f, -1.0f,  1.0f,
			-1.0f,  1.0f,  1.0f,
			 1.0f,  1.0f,  1.0f,
			 1.0f, -1.0f,  1.0f,
	};

	float textures[] = {
			0.0f, 0.0f,
			0.0f, 0.0f,
			0.0f, 0.0f,
			0.0f, 0.0f,

			0.0f, 0.0f,
			0.0f, 0.0f,
			0.0f, 0.0f,
			0.0f, 0.0f,
};

	unsigned short indices[] = {
			0,1,2, 2,3,0,
			4,5,6, 6,7,4,

			0,1,5, 5,4,0,
			3,4,7, 7,6,3,

			0,3,7, 7,4,0,
			1,2,6, 6,5,1
	};


	return new Model(_texIDRasen, vertices, textures, indices, 36);
}
