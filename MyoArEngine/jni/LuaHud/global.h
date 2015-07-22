#pragma once

#define LUA_FUNCTION // markiert Funktionen die aus Lua aufgerufen werden zur besseren lesbarkeit des Codes

struct SIZE
{
public:
	int cx;
	int cy;
};

struct POINT
{
public:
	int x;
	int y;
};

#include <lua.hpp>

#define GLUT_KEY_LEFT 0
#define GLUT_KEY_RIGHT 0
#define GLUT_KEY_UP 0
#define GLUT_KEY_DOWN 0

#define GLUT_LEFT_BUTTON 0
#define GLUT_DOWN 0
#define GLUT_UP 0
#define GLUT_BITMAP_HELVETICA_10 0
