LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_CPPFLAGS := -std=c++11
LOCAL_CPP_FEATURES := rtti exceptions

LOCAL_C_INCLUDES += $(LOCAL_PATH)/../LuaLib
LOCAL_C_INCLUDES += ${ANDROID_NDK}/sources/cxx-stl/gnu-libstdc++/4.8/include

LOCAL_MODULE    := LuaHudLib
LOCAL_SRC_FILES := GlHelper.cpp UIElement.cpp Scripting.cpp UIButton.cpp UIImage.cpp UILabel.cpp UITextField.cpp

LOCAL_STATIC_LIBRARIES := libLuaLib
LOCAL_LDLIBS := -lGLESv1_CM -landroid


LOCAL_EXPORT_CFLAGS := -DANDROID=1

include $(BUILD_STATIC_LIBRARY)
