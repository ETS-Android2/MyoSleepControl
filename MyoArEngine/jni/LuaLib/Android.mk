#LOCAL_PATH := $(call my-dir)
#include $(CLEAR_VARS)
#
#LOCAL_C_INCLUDES += $(LOCAL_PATH)/../lua
#LOCAL_C_INCLUDES += ${ANDROID_NDK}/sources/cxx-stl/gnu-libstdc++/4.8/include
#
#LOCAL_MODULE := MyoArEngine
#
#LOCAL_SRC_FILES := MyoArEngine.cpp # UIElement.cpp Scripting.cpp UIButton.cpp UIImage.cpp UILabel.cpp UITextField.cpp
#
#LOCAL_STATIC_LIBRARIES := liblua
#LOCAL_LDLIBS := -lGLESv2
#LOCAL_CPPFLAGS := -std=c++11
#
#include $(BUILD_SHARED_LIBRARY)



LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

#LOCAL_CFLAGS += -std=c99
LOCAL_CPPFLAGS := -std=c++11
LOCAL_CPP_FEATURES := rtti exceptions

LOCAL_C_INCLUDES += $(LOCAL_PATH)/../bmf

ANDROID_LIBS := $(LOCAL_PATH)/../../

LOCAL_C_INCLUDES += $(LOCAL_PATH)/../lua
LOCAL_C_INCLUDES += ${ANDROID_NDK}/sources/cxx-stl/gnu-libstdc++/4.8/include

LOCAL_MODULE    := MyoArEngine
LOCAL_SRC_FILES := MyoArEngine.cpp GlHelper.cpp UIElement.cpp Scripting.cpp UIButton.cpp UIImage.cpp UILabel.cpp UITextField.cpp

LOCAL_STATIC_LIBRARIES := liblua
LOCAL_STATIC_LIBRARIES += bmf
LOCAL_LDLIBS := -lGLESv1_CM -landroid


LOCAL_EXPORT_CFLAGS := -DANDROID=1

include $(BUILD_SHARED_LIBRARY)
