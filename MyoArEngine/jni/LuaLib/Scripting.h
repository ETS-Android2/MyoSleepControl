#pragma once

#include <vector>
#include <functional>
#include <map>
#include <string>
#include <setjmp.h>

#include "UIElement.h"
#include "UITextField.h"
#include "global.h"

using namespace std;

class Scripting
{
public:
	~Scripting(void);

	void Init();
	void RunScript(const char *file);

	bool HasError();
	const char *GetError();

	void RenderHUD();

	void InstallFunction(std::string name, std::function<int(lua_State *)> handler);

	bool MouseEvent(int button, int state, int x, int y);
	bool KeyboardEvent(unsigned char c, int p1, int p2);
	void SpecialEvent(int c, int p1, int p2);

	void SetUiSize(SIZE rect);

	void PostLogEvent(char *);

	static Scripting *GetInstance();
private:
	Scripting(void);

	int m_luaEventHandlerId;
	std::set<std::string> m_registeredEvents;
	std::map<std::string, std::function<int(lua_State *)>> m_registeredFunctions;

	static Scripting *theLuaEngine;
	SIZE _uiSize;

	lua_State *luaState;

	const char *luaError;
	jmp_buf jumpBuffer;

	std::vector<UIElement *> m_uiElements;
	UITextField *m_focus;

	static int LUA_FUNCTION lua_atPanicFunctionProxy(lua_State *L){return Scripting::theLuaEngine->lua_atPanicFunction(L);}
	static int LUA_FUNCTION lua_RegisterEventProxy  (lua_State *L){return Scripting::theLuaEngine->lua_RegisterEvent(L);}
	static int LUA_FUNCTION lua_SetEventHandlerProxy(lua_State *L){return Scripting::theLuaEngine->lua_SetEventHandler(L);}
	static int LUA_FUNCTION lua_ExternalFunctionProxy(lua_State *L){ return Scripting::theLuaEngine->lua_ExternalFunction(L); }

	int LUA_FUNCTION lua_atPanicFunction(lua_State *L);
	int LUA_FUNCTION lua_RegisterEvent  (lua_State *L);
	int LUA_FUNCTION lua_SetEventHandler(lua_State *L);
	int LUA_FUNCTION lua_ExternalFunction(lua_State *L);
};

