#include <jni.h>
#include "test.h"

JavaVM *g_jvm;

/**
* 动态注册的java的类
*/
const char *Test_Clazz = "com/xiaoge/org/jni/Test";
const char *Test1_Clazz = "com/xiaoge/org/jni/Test1";
const char *Data_Clazz = "com/xiaoge/org/jni/Data";

