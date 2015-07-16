#LOCAL_PATH := $(call my-dir)

#include $(call all-subdir-makefiles)

LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_CFLAGS += -std=c99

LOCAL_MODULE    := AirGap
LOCAL_SRC_FILES := AirGapMain.cpp

LOCAL_EXPORT_CFLAGS := -DANDROID=1

include $(BUILD_SHARED_LIBRARY)

# Add prebuilt libocr
include $(CLEAR_VARS)

LOCAL_MODULE := libgesture-classifier
LOCAL_SRC_FILES := libgesture-classifier.so

include $(PREBUILT_SHARED_LIBRARY)