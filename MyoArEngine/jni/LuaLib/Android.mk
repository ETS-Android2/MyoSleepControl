LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_C_INCLUDES += $(LOCAL_PATH)/../lua
LOCAL_C_INCLUDES += ${ANDROID_NDK}/sources/cxx-stl/gnu-libstdc++/4.8/include

LOCAL_MODULE := MyoArEngine

LOCAL_SRC_FILES := MyoArEngine.cpp UIElement.cpp Scripting.cpp UIButton.cpp UIImage.cpp UILabel.cpp UITextField.cpp

LOCAL_STATIC_LIBRARIES := liblua
#LIB_PATH := $(LOCAL_PATH)/../../libs/
#LOCAL_LDLIBS += $(LIB_PATH) -llua

LOCAL_CPPFLAGS := -fexceptions -std=c++11 --rtti -fPIC

include $(BUILD_STATIC_LIBRARY)
