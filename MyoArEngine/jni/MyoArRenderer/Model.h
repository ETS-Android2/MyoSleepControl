/*
 * Model.h
 *
 *  Created on: 23.07.2015
 *      Author: j39f3fs
 */

#ifndef MODEL_H_
#define MODEL_H_

class Model {
	int _textureID;
	float *_vertices;
	float *_textureCoords;
	unsigned short *_indices;
	unsigned int _numIndices;

public:
	Model(int textureID,
		float *vertices,
		float *textureCoords,
		unsigned short *indices,
		unsigned int numIndices);
	virtual ~Model();

	int GetTextureID();
	float *GetVertices();
	float *GetTextureCoords();
	unsigned short *GetIndices();
	unsigned int GetNumIndices();
};

#endif /* MODEL_H_ */
