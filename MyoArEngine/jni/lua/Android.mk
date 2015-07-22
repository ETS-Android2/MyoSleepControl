LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := lua
LOCAL_SRC_FILES := lapi.c lauxlib.c lbaselib.c lcode.c ldblib.c ldebug.c ldo.c ldump.c 
LOCAL_SRC_FILES += lfunc.c lgc.c linit.c liolib.c llex.c lmathlib.c lmem.c loadlib.c 
LOCAL_SRC_FILES += lobject.c lopcodes.c loslib.c lparser.c lstate.c lstring.c lstrlib.c 
LOCAL_SRC_FILES += ltable.c ltablib.c ltm.c lundump.c lvm.c lzio.c lcorolib.c lctype.c
LOCAL_SRC_FILES += lutf8lib.c

#LOCAL_LDLIBS := -ld -lm

LOCAL_CFLAGS := -fno-PIC

include $(BUILD_STATIC_LIBRARY)