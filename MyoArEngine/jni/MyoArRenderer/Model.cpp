#include "Model.h"
#include "unistd.h"

Model::Model(int textureID,
		float *vertices,
		float *textureCoords,
		unsigned short *indices,
		unsigned int numIndices)
{
	_textureID = textureID;

	_indices = new unsigned short[numIndices];

	int maxIndex = 0;
	for (int i = 0; i < numIndices; i++)
	{
		if (indices[i] > maxIndex)
			maxIndex = indices[i];
		_indices[i] = indices[i];
	}

	maxIndex++;

	_vertices = new float[maxIndex * 3];
	_textureCoords = new float[maxIndex * 2];

	memcpy(_vertices, vertices, sizeof(float) * maxIndex * 3);
	memcpy(_textureCoords, textureCoords, sizeof(float) * maxIndex * 2);

	_numIndices = numIndices;
}

Model::~Model() {
}

int Model::GetTextureID()
{
	return this->_textureID;
}

float *Model::GetVertices()
{
	return _vertices;
}

float *Model::GetTextureCoords()
{
	return _textureCoords;
}

unsigned short *Model::GetIndices()
{
	return _indices;
}

unsigned int Model::GetNumIndices()
{
	return _numIndices;
}
