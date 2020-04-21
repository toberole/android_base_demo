#include "log.h"
#include "test.h"

/*
 * Class:     com_xiaoge_org_jni_Test
 * Method:    test2
 * Signature: (ILjava/lang/Object;)V
 */
void Test_test2(JNIEnv *env, jobject instance, jint i, jobject o) {
    LOGI("Test_test2 测试动态注册");
}

/*
 * Class:     com_xiaoge_org_jni_Test
 * Method:    test1
 * Signature: (B)V
 */
void Test_test1(JNIEnv *env, jobject instance, jbyte b) {
    LOGI("Test_test1 测试动态注册");
}

//extern "C"{
//#include "avx2intrin.h"
//}

void Test_test(JNIEnv *env, jobject instance,
               jbyte b, jchar ch,
               jshort s, jint i,
               jfloat f, jdouble d,
               jstring str_, jobject o) {
    const char *str = env->GetStringUTFChars(str_, 0);
    LOGI("Test_test 测试动态注册");
    env->ReleaseStringUTFChars(str_, str);

//    test1_cpp();
}

/*
 * Class:     com_xiaoge_org_jni_Test1
 * Method:    sysHello
 * Signature: (Ljava/lang/String;)I
 */
JNIEXPORT jint JNICALL Test1_sysHello(JNIEnv *env, jclass jclazz, jstring jstr) {
    LOGI("Test1_sysHello 测试动态注册");
    return 0;
}