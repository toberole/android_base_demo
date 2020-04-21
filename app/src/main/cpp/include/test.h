#ifndef _Included_com_xiaoge_org_jni_Test
#define _Included_com_xiaoge_org_jni_Test

#include <jni.h>

/**
 * 按照C语言的方式生成方法签名
 */
#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     com_xiaoge_org_jni_Test
 * Method:    test
 * Signature: (BCSIFDLjava/lang/String;Ljava/lang/Object;)V
 */
void Test_test
        (JNIEnv *, jobject, jbyte, jchar, jshort, jint, jfloat, jdouble, jstring, jobject);

/*
 * Class:     com_xiaoge_org_jni_Test
 * Method:    test1
 * Signature: (B)V
 */
void Test_test1
        (JNIEnv *, jobject, jbyte);

/*
 * Class:     com_xiaoge_org_jni_Test
 * Method:    test2
 * Signature: (ILjava/lang/Object;)V
 */
void Test_test2
        (JNIEnv *, jobject, jint, jobject);


JNIEXPORT jint JNICALL Test1_sysHello(JNIEnv *env, jclass jclazz, jstring jstr);


/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_test
        (JNIEnv *, jobject);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test1
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_test1
        (JNIEnv *, jclass);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test2
 * Signature: (BCSIJFDZ)V
 */
JNIEXPORT void JNICALL Data_test2
        (JNIEnv *, jclass, jbyte, jchar, jshort, jint, jlong, jfloat, jdouble, jboolean);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test3
 * Signature: (Lcom/xiaoge/org/jni/Bean;)V
 */
JNIEXPORT void JNICALL Data_test3
        (JNIEnv *, jclass, jobject);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test4
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Data_test4
        (JNIEnv *, jclass, jstring);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5
 * Signature: ([II)V
 */
JNIEXPORT void JNICALL Data_test5
        (JNIEnv *, jclass, jintArray, jint);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5_1
 * Signature: ([Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Data_test5_11
        (JNIEnv *, jclass, jobjectArray);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5_2
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Data_test5_12
        (JNIEnv *, jclass, jbyteArray);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5_3
 * Signature: ([C)V
 */
JNIEXPORT void JNICALL Data_test5_13
        (JNIEnv *, jclass, jcharArray);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5_4
 * Signature: (Lcom/xiaoge/org/jni/Data$Callback;)V
 */
JNIEXPORT void JNICALL Data_test5_14
        (JNIEnv *, jclass, jobject);


/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5_4_1
 * Signature: (Lcom/xiaoge/org/jni/Data$Callback;)V
 */
JNIEXPORT void JNICALL Data_test5_14_11
        (JNIEnv *, jclass, jobject);


/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5_5
 * Signature: (Lcom/xiaoge/org/jni/Gender;)V
 */
JNIEXPORT void JNICALL Data_test5_15
        (JNIEnv *, jclass, jobject);
/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test6
 * Signature: ()B
 */
JNIEXPORT jbyte JNICALL Data_test6
        (JNIEnv *, jclass);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test7
 * Signature: ()C
 */
JNIEXPORT jchar JNICALL Data_test7
        (JNIEnv *, jclass);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test8
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Data_test8
        (JNIEnv *, jclass);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test9
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Data_test9
        (JNIEnv *, jclass);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test10
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Data_test10
        (JNIEnv *, jclass);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test11
 * Signature: ()Lcom/xiaoge/org/jni/Bean;
 */
JNIEXPORT jobject JNICALL Data_test11
        (JNIEnv *, jclass);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test12
 * Signature: ()Lcom/xiaoge/org/jni/Bean;
 */
JNIEXPORT jobject JNICALL Data_test12
        (JNIEnv *, jclass);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test13
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_test13
        (JNIEnv *, jobject);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test14
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_test14
        (JNIEnv *, jclass);

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test14
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_test14_1
        (JNIEnv *, jclass);


/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test_fork
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_test_fork
        (JNIEnv *, jclass);


/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    native_load_so
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_native_load_so
        (JNIEnv *, jclass);




#ifdef __cplusplus
}
#endif

#endif
