LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_CPPFLAGS := -std=c++11
LOCAL_CPP_FEATURES := rtti exceptions

LOCAL_C_INCLUDES += $(LOCAL_PATH)/../LuaLib
LOCAL_C_INCLUDES += $(LOCAL_PATH)/../LuaHudLib
LOCAL_C_INCLUDES += ${ANDROID_NDK}/sources/cxx-stl/gnu-libstdc++/4.8/include

LOCAL_MODULE    := MyoArRenderer
LOCAL_SRC_FILES := MyoArRenderBridge.cpp MyoArRenderer.cpp
LOCAL_SRC_FILES += ModelRenderer.cpp Model.cpp ModelFactory.cpp
LOCAL_SRC_FILES += Unit.cpp UnitRenderer.cpp MyoWeaponRenderer.cpp


LOCAL_STATIC_LIBRARIES := libLuaHudLib
LOCAL_LDLIBS := -lGLESv1_CM -landroid


LOCAL_EXPORT_CFLAGS := -DANDROID=1

include $(BUILD_SHARED_LIBRARY)
