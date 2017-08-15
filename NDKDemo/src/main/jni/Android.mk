LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE := secret
LOCAL_LDFLAGS := -Wl,--build-id
LOCAL_SRC_FILES := \
	D:\android_workspace\MiaoZhiLiCai\AnimationsDemo\TestNDK\src\main\jni\android_liujs_com_testndk_MainActivity.c \

LOCAL_C_INCLUDES += D:\android_workspace\MiaoZhiLiCai\AnimationsDemo\TestNDK\src\main\jni
LOCAL_C_INCLUDES += D:\android_workspace\MiaoZhiLiCai\AnimationsDemo\TestNDK\src\debug\jni

include $(BUILD_SHARED_LIBRARY)
