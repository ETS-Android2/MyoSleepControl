LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_C_INCLUDES += $(LOCAL_PATH)/../lua
LOCAL_C_INCLUDES += ${ANDROID_NDK}/sources/cxx-stl/gnu-libstdc++/4.8/include

LOCAL_MODULE := MyoArEngine

LOCAL_SRC_FILES := MyoArEngine.cpp UIElement.cpp Scripting.cpp UIButton.cpp UIImage.cpp UILabel.cpp UITextField.cpp

LOCAL_STATIC_LIBRARIES := liblua

LOCAL_CPPFLAGS := -std=c++11 --rtti

include $(BUILD_STATIC_LIBRARY)