LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_C_INCLUDES += $(LOCAL_PATH)/../lua
LOCAL_MODULE     := luajava
#LOCAL_MODULE    := MyoArEngine
#LOCAL_SRC_FILES := luajava.c
LOCAL_SRC_FILES  := MyoArEngine.cpp
LOCAL_STATIC_LIBRARIES := liblua

include $(BUILD_SHARED_LIBRARY)
