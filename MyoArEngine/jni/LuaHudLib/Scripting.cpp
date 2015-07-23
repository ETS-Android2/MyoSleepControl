#include "Scripting.h"

#include "UIButton.h"
#include "UILabel.h"
#include "UIImage.h"
#include "UITextField.h"

#include "LuaBind.h"
#include <functional>
#include "global.h"
#include "GlHelper.h"

Scripting::Scripting(void)
	:_uiSize(), m_focus(nullptr)
{
}

Scripting::~Scripting(void)
{
}

Scripting *Scripting::theLuaEngine = nullptr;

Scripting *Scripting::GetInstance()
{
	if (Scripting::theLuaEngine == nullptr)
	{
		Scripting::theLuaEngine = new Scripting();
	}
	return Scripting::theLuaEngine;
}

void Scripting::SetUiSize(SIZE size)
{
	this->_uiSize = size;

	for (auto el : m_uiElements)
	{
		if (el->GetParent() == nullptr)
		{
			el->SetParentDimensions(POINT(), size);
		}
	}
}

void Scripting::Init(int asciiTexId)
{
	m_uiElements.clear();

	luaState = luaL_newstate();
	luaL_openlibs(luaState);

	lua_pushcfunction(luaState, &Scripting::lua_RegisterEventProxy);
	lua_setglobal(luaState, "RegisterEvent");
	lua_pushcfunction(luaState, &Scripting::lua_SetEventHandlerProxy);
	lua_setglobal(luaState, "SetEventHandler");

	std::function<void(UIElement *)> newElementCallback = [this](UIElement *field){
		field->SetParentDimensions(POINT(), this->_uiSize);
		m_uiElements.push_back(field);
	};

	UIElement::Init(luaState);

	UITextField::Init(luaState, newElementCallback);
	UILabel::Init(luaState, newElementCallback);
	UIButton::Init(luaState, newElementCallback);
	UIImage::Init(luaState, newElementCallback);
}

int Scripting::lua_atPanicFunction(lua_State *L)
{
	luaError = lua_tostring(L, -1);
	lua_pop(L, 1);
	longjmp(jumpBuffer, 1);
	return 1;
}

void Scripting::RunScript(const char *script)
{
	lua_atpanic(luaState, &Scripting::lua_atPanicFunctionProxy);

	int r = setjmp(jumpBuffer);

	if (r == 0)
	{
		if (luaL_loadstring (luaState, script)) {
			luaError = lua_tostring(luaState, -1);
			lua_pop(luaState, 1);
		}
		else{
			luaError = NULL;
			lua_call(luaState, 0, LUA_MULTRET);
		}
	}
}

bool Scripting::HasError()
{
	return luaError != NULL;
}

const char *Scripting::GetError()
{
	return luaError;
}

void Scripting::RenderHUD()
{
	if (HasError())
	{
		GlHelper *glHelper = new GlHelper();

		glHelper->DrawText((const unsigned char *)GetError(), 20.0f, _uiSize.cx - 60.0f);

		delete glHelper;
	}

	for (UIElement *var : this->m_uiElements)
	{
		var->Render();
	}
}

bool Scripting::MouseEvent(int button, int state, int x, int y)
{
	lua_atpanic(luaState, &Scripting::lua_atPanicFunctionProxy);

	int r = setjmp(jumpBuffer);

	if (r == 0)
	{
		// wenn hier in TextField ist -> Focus setzen
		// wenn hier ein button ist -> Click event senden
		// sonst -> focus löschen
		if (button == GLUT_LEFT_BUTTON && state == GLUT_DOWN)
		{
			if (m_focus != nullptr)
			{
				m_focus->SetHasFocus(false);
			}

			m_focus = nullptr;

			for (auto feld : this->m_uiElements)
			{
				UITextField *textField = dynamic_cast<UITextField *>(feld);

				if (textField != nullptr)
				{
					Dimension dim = textField->GetAbsoluteDimensions();

					if (textField->IsVisible() &&
						x > dim.x && x < dim.x + dim.width &&
						y > dim.y && y < dim.y + dim.height) // hit test
					{
						m_focus = textField;
						m_focus->SetHasFocus(true);
						m_focus->SetCursorByCoordiantes(x, y);


						return true;
					}
				}

				UIButton *button = dynamic_cast<UIButton *>(feld);

				if (button != nullptr && button->IsVisible())
				{
					Dimension dim = button->GetAbsoluteDimensions();

					if (x > dim.x && x < dim.x + dim.width &&
						y > dim.y && y < dim.y + dim.height) // hit test
					{
						button->OnClick(luaState);
						return true;
					}
				}
			}
		}
		else if ((button == 3 || button == 4) && state == GLUT_UP)
		{
			for (auto feld : this->m_uiElements)
			{
				UILabel *textField = dynamic_cast<UILabel *>(feld);

				if (textField != nullptr && textField->IsVisible())
				{
					Dimension dim = textField->GetAbsoluteDimensions();

					if (x > dim.x && x < dim.x + dim.width &&
						y > dim.y && y < dim.y + dim.height) // hit test
					{
						textField->Scroll(button == 3);
						return true;
					}
				}
			}
		}
	}
	return false;
}

bool Scripting::KeyboardEvent(unsigned char c, int p1, int p2)
{
	// wenn TextField focus hat ihm die taste senden
	if (this->m_focus != nullptr)
	{
		this->m_focus->SendKey(luaState, c);
		return true;
	}

	return false;
}

void Scripting::SpecialEvent(int c, int p1, int p2)
{
	if (m_focus != nullptr)
	{
		if (c == GLUT_KEY_LEFT) // left
		{
			m_focus->SetCursorPositionRelative(-1);
		}
		else if (c == GLUT_KEY_RIGHT)
		{
			m_focus->SetCursorPositionRelative(1);
		}
		else if (c == GLUT_KEY_UP)
		{
			m_focus->SetCursorPositionRelativeVertical(-1);

		}
		else if (c == GLUT_KEY_DOWN)
		{
			m_focus->SetCursorPositionRelativeVertical(1);
		}
	}
}

int Scripting::lua_RegisterEvent(lua_State *L)
{
	std::string eventName = luaL_checkstring(L, 1);

	this->m_registeredEvents.insert(eventName);

	return 0;
}

void Scripting::PostLogEvent(const char *text)
{
	if (this->m_luaEventHandlerId != -1)
	{
		if (this->m_registeredEvents.find("EVENT_GLOBAL_LOG") != this->m_registeredEvents.end())
		{
			lua_rawgeti(this->luaState, LUA_REGISTRYINDEX, this->m_luaEventHandlerId);
			lua_pushstring(this->luaState, "EVENT_GLOBAL_LOG");
			lua_pushstring(this->luaState, text);
			lua_call(this->luaState, 2, 0); // event, event_text
		}
	}
}

int LUA_FUNCTION Scripting::lua_SetEventHandler(lua_State *L)
{
	if (lua_isfunction(L, 1) == 1)
	{
		lua_pushvalue(L, 1);
		m_luaEventHandlerId = luaL_ref(L, LUA_REGISTRYINDEX);
	}

	return 0;
}

void Scripting::InstallFunction(std::string name, std::function<int(lua_State *)> handler)
{
	this->m_registeredFunctions[name] = handler;
	lua_pushstring(luaState, name.c_str());
	lua_pushcclosure(luaState, &Scripting::lua_ExternalFunctionProxy, 1);
	lua_setglobal(luaState, name.c_str());
}

int LUA_FUNCTION Scripting::lua_ExternalFunction(lua_State *L)
{
	std::string fName = luaL_checkstring(L, lua_upvalueindex(1));
	std::function<int(lua_State *)> handler = this->m_registeredFunctions[fName];

	return handler(L);
}
