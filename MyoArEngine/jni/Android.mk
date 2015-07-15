LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := MyoArEngine
LOCAL_SRC_FILES := MyoArEngine.cpp

include $(BUILD_SHARED_LIBRARY)
