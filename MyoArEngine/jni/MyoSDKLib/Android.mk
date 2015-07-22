LOCAL_PATH := $(call my-dir)
# Add prebuilt libocr
include $(CLEAR_VARS)

LOCAL_MODULE := libgesture-classifier
LOCAL_SRC_FILES := libgesture-classifier.so

include $(PREBUILT_SHARED_LIBRARY)
