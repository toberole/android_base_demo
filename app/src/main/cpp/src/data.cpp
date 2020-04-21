#include <jni.h>
#include <cstdlib>
#include <thread>
#include <zconf.h>
#include "log.h"
#include "Global.h"
#include "test.h"

/**
 * 可以通过 JavaVM获取到当前线程的JNIEnv
    vm->AttachCurrentThread() 调用AttachCurrentThread 附加当前线程到虚拟机VM当中，并返回线程对应的JNIEnv
    vm->GetEnv() AttachCurrentThread 之后调用才能获取到JNIEnv
    vm->DetachCurrentThread()
 */

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_test(JNIEnv *env, jobject jobj) {
    LOGI("Data_test 非静态native method");
}

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test1
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_test1(JNIEnv *env, jclass jclazz) {
    LOGI("Data_test1 静态native method");
}

/*
 * 传递基础数据类型
 *
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test2
 * Signature: (BCSIJFDZ)V
 */
JNIEXPORT void JNICALL Data_test2(
        JNIEnv *env, jclass jclazz,
        jbyte jb, jchar jc,
        jshort js, jint ji,
        jlong jl, jfloat jf,
        jdouble jd, jboolean jbo) {
    int8_t c_jb = jb;
    LOGI("Data_test2 jbyte %d", c_jb);

    uint16_t c_jch = jc;
    LOGI("Data_test2 jchar %d", c_jch);

    int16_t c_js = js;
    LOGI("Data_test2 jshort %d", c_js);

    int32_t c_ji = ji;
    LOGI("Data_test2 jint %d", c_ji);

    int64_t c_jl = jl;
    LOGI("Data_test2 jlong %ld", c_jl);

    float c_jf = jf;
    LOGI("Data_test2 jfloat %f", c_jf);

    double c_jd = jd;
    LOGI("Data_test2 jdouble %f", c_jd);

    uint8_t c_jbo = jbo;
    LOGI("Data_test2 jboolean %d", c_jbo);

}

/*
 * 传递 类对象
 *
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test3
 * Signature: (Lcom/xiaoge/org/jni/Bean;)V
 */
JNIEXPORT void JNICALL
Data_test3(JNIEnv *env, jclass jclazz, jobject jbean) {
    // 获取bean的class
    const char *bean_name = "com/xiaoge/org/jni/Bean";
    jclass bean_clazz = env->FindClass(bean_name);

    // 获取bean的字段
    jfieldID tag_fieldID = env->GetFieldID(bean_clazz, "tag", "Ljava/lang/String;");

    // 获取tag字段的值
    jstring tag_str = static_cast<jstring>(env->GetObjectField(jbean, tag_fieldID));
    const char *tagv = env->GetStringUTFChars(tag_str, nullptr);
    LOGI("Data_test3 bean tag: %s", tagv);
    env->ReleaseStringUTFChars(tag_str, tagv);
}


/*
 * 传递 String
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test4
 * Signature: (Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Data_test4
        (JNIEnv *env, jclass jclazz, jstring jstr) {
    jboolean b;
    const char *str = env->GetStringUTFChars(jstr, &b);
    if (b != 0) {
        LOGI("Data_test4 b != 0");
    }
    LOGI("Data_test4 str: %s", str);
    env->ReleaseStringUTFChars(jstr, str);
}

/*
 * 传递 数组
 *
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5
 * Signature: ([II)V
 */
JNIEXPORT void JNICALL
Data_test5(JNIEnv *env, jclass jclazz, jintArray jiarr, jint jlen) {
    // 方法一
    //  获取到数组
    jboolean jb;
    jint *arr = env->GetIntArrayElements(jiarr, &jb);// JNI_TRUE：表示临时缓冲区数组指针，JNI_FALSE：表示临时原始数组指针
    LOGI("test5 jboolean: %d", jb);
    // 获取数组的长度
    int len = env->GetArrayLength(jiarr);
    int total = 0;
    for (int i = 0; i < len; ++i) {
        total += arr[i];
    }
    LOGI("Data_test5 sum arr: %d", total);

    // 方法二
    jint buf[jlen];
    env->GetIntArrayRegion(jiarr, 0, jlen, buf);
    total = 0;
    for (int i = 0; i < jlen; ++i) {
        total += buf[i];
    }
    LOGI("Data_test5 sum arr: %d", total);

    // 对数组赋值
    for (int i = 0; i < jlen; ++i) {
        buf[i] = buf[i] + 10;
    }
    env->SetIntArrayRegion(jiarr, 0, jlen, buf);
}

/*
 * 传递 String数组
 *
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5_1
 * Signature: ([Ljava/lang/String;)V
 */
JNIEXPORT void JNICALL Data_test5_11
        (JNIEnv *env, jclass jclazz, jobjectArray jsarr) {
    jsize len = env->GetArrayLength(jsarr);
    for (int i = 0; i < len; ++i) {
        jstring jstr = static_cast<jstring>(env->GetObjectArrayElement(jsarr, i));
        const char *s = env->GetStringUTFChars(jstr, nullptr);
        LOGI("test5_1 jsarr[%d] = %s", i, s);
        env->ReleaseStringUTFChars(jstr, s);
    }
}

/*
 * 传递 byte数组
 *
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5_2
 * Signature: ([B)V
 */
JNIEXPORT void JNICALL Data_test5_12
        (JNIEnv *env, jclass jclazz, jbyteArray jbarr) {
    jbyte *jbs = env->GetByteArrayElements(jbarr, nullptr);
    jsize len = env->GetArrayLength(jbarr);
    for (int i = 0; i < len; ++i) {
        LOGI("test5_2 jbarr[%d] = %d", i, jbs[i]);
    }

    env->ReleaseByteArrayElements(jbarr, jbs, 0);
}

char **stringArrToCharArr(JNIEnv *env, jclass jc, jobjectArray strArray) {
    jstring jstr;
    jsize len = env->GetArrayLength(strArray);
    char **pstr = (char **) malloc(len * sizeof(char *));
    int i = 0;

    for (i = 0; i < len; i++) {
        jstr = static_cast<jstring>(env->GetObjectArrayElement(strArray, i));
        pstr[i] = (char *) (env->GetStringUTFChars(jstr, 0));
    }
    return pstr;
}

/*
 * 传递 char 数组
 *
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5_3
 * Signature: ([C)V
 */
JNIEXPORT void JNICALL Data_test5_13
        (JNIEnv *env, jclass jclazz, jcharArray jch_arr) {
    jchar *arr = env->GetCharArrayElements(jch_arr, nullptr);
    jsize len = env->GetArrayLength(jch_arr);

    char16_t buf[len + 1];
    for (int i = 0; i < len; ++i) {
        buf[i] = arr[i];
    }

    LOGI("test5_3 %s", buf);
    env->ReleaseCharArrayElements(jch_arr, arr, 0);
}

/*
 * 传递 callback
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5_4
 * Signature: (Ljava/lang/Object;)V
 */
JNIEXPORT void JNICALL Data_test5_14
        (JNIEnv *env, jclass jclazz, jobject jcb) {

    jclass jcb_clazz = env->GetObjectClass(jcb);
    if (jcb_clazz != nullptr) {
        jmethodID onError_id = env->GetMethodID(jcb_clazz, "onError", "(ILjava/lang/String;)V");
        jstring errormsg = env->NewStringUTF("hello error");
        env->CallVoidMethod(jcb, onError_id, -1, errormsg);

        jmethodID onCallback_Short_id = env->GetMethodID(jcb_clazz, "onCallback_Short", "(I[S)V");
        short arr[] = {1, 2, 3};
        int len = sizeof(arr) / sizeof(arr[0]);
        jshortArray jshort_arr = env->NewShortArray(len);
        env->SetShortArrayRegion(jshort_arr, 0, len, arr);
        env->CallVoidMethod(jcb, onCallback_Short_id, 1, jshort_arr);

        jmethodID onCallback_byte_id = env->GetMethodID(jcb_clazz, "onCallback_byte", "(I[B)V");
        int8_t bytes[] = {1, 2, 3};
        len = sizeof(bytes) / sizeof(bytes[0]);
        jbyteArray jbytes = env->NewByteArray(len);
        env->SetByteArrayRegion(jbytes, 0, len, bytes);
        env->CallVoidMethod(jcb, onCallback_byte_id, 1, jbytes);
    }
}

/*
 * 传递 callback
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5_4_1
 * Signature: (Lcom/xiaoge/org/jni/Data$Callback;)V
 */

JNIEXPORT void JNICALL Data_test5_14_11
        (JNIEnv *env, jclass jclazz, jobject jcb) {
    jclass jcb_clazz = env->FindClass("com/xiaoge/org/jni/Data$Callback");
    if (jcb_clazz != nullptr) {
        jmethodID onError_id = env->GetMethodID(jcb_clazz, "onError", "(ILjava/lang/String;)V");
        jstring errormsg = env->NewStringUTF("hello error");
        // 错误写法直接使用"hello error"
        // JNI DETECTED ERROR IN APPLICATION: use of deleted global reference
        // "hello error" 是在 native C++栈上开辟的内存 「jni会用"全局引用变量-> 引用=指针"指向它」 方法结束栈内存就释放 全局引用变量也删除了
        // env->CallVoidMethod(jcb, onError_id, -1, "hello error");
        env->CallVoidMethod(jcb, onError_id, -1, errormsg);
        // DeleteLocalRef 相当于引用计数减1
        env->DeleteLocalRef(errormsg);

        jmethodID onCallback_Short_id = env->GetMethodID(jcb_clazz, "onCallback_Short", "(I[S)V");
        short arr[] = {1, 2, 3};
        int len = sizeof(arr) / sizeof(arr[0]);
        jshortArray jshort_arr = env->NewShortArray(len);
        env->SetShortArrayRegion(jshort_arr, 0, len, arr);
        env->CallVoidMethod(jcb, onCallback_Short_id, 1, jshort_arr);

        jmethodID onCallback_byte_id = env->GetMethodID(jcb_clazz, "onCallback_byte", "(I[B)V");
        int8_t bytes[] = {1, 2, 3};
        len = sizeof(bytes) / sizeof(bytes[0]);
        jbyteArray jbytes = env->NewByteArray(len);
        env->SetByteArrayRegion(jbytes, 0, len, bytes);
        env->CallVoidMethod(jcb, onCallback_byte_id, 1, jbytes);
    }
}

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test5_5
 * Signature: (Lcom/xiaoge/org/jni/Gender;)V
 */
JNIEXPORT void JNICALL Data_test5_15
        (JNIEnv *env, jclass jclazz, jobject jobj_enum) {
    jclass jobj_enum_clazz = env->GetObjectClass(jobj_enum);
    jmethodID getValue_id = env->GetMethodID(jobj_enum_clazz, "getValue", "()I");
    int v = env->CallIntMethod(jobj_enum, getValue_id);

    LOGI("jobj_enum v = %d", v);
}

/*
 * 返回值 byte
 *
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test6
 * Signature: ()B
 */
JNIEXPORT jbyte JNICALL Data_test6
        (JNIEnv *env, jclass jclazz) {
    LOGI("Data_test6");
    int8_t ret = 66;
    return ret;
}

/*
 * 返回值 char
 *
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test7
 * Signature: ()C
 */
JNIEXPORT jchar JNICALL Data_test7(JNIEnv *env, jclass jclazz) {
    LOGI("Data_test7");
    int16_t ret = 97;
    return ret;
}

/*
 * 返回值 int
 *
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test8
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Data_test8(JNIEnv *env, jclass jclazz) {
    LOGI("Data_test8");
    int32_t ret = 6666;
    return ret;
}

/*
 * 返回值 String
 *
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test9
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Data_test9(JNIEnv *env, jclass jclazz) {
    jstring jstr = env->NewStringUTF("hello data_test9");
    return jstr;
}

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test10
 * Signature: ()[I
 */
JNIEXPORT jintArray JNICALL Data_test10
        (JNIEnv *env, jclass jclazz) {
    int arr[] = {1, 2, 3, 4, 5};
    int len = sizeof(arr) / sizeof(arr[0]);
    jintArray jintArr = env->NewIntArray(len);
    env->SetIntArrayRegion(jintArr, 0, len, arr);
    return jintArr;
}

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test11
 * Signature: ()Lcom/xiaoge/org/jni/Bean;
 */
JNIEXPORT jobject JNICALL Data_test11
        (JNIEnv *env, jclass jclazz) {
    jclass obj_clazz = env->FindClass("com/xiaoge/org/jni/Bean");
    // 注意构造函数的名字"<init>" 而不是类名，获取无参构造函数的
    jmethodID constructor_id = env->GetMethodID(obj_clazz, "<init>", "()V");
    jobject ret = env->NewObject(obj_clazz, constructor_id);
    return ret;
}

jobject newJavaobj(JNIEnv *env) {
    jclass obj_clazz = env->FindClass("com/xiaoge/org/jni/Bean");
    // 注意构造函数的名字"<init>" 而不是类名，获取有参构造函数的
    jmethodID constructor_id = env->GetMethodID(obj_clazz, "<init>", "(Ljava/lang/String;)V");
    jstring jstr = env->NewStringUTF("hello bean");
    jobject ret = env->NewObject(obj_clazz, constructor_id, jstr);
    env->DeleteLocalRef(jstr);
    return ret;
}

/* AllocObject */
/**
 * 使用函数AllocObject可以根据传入的jclass创建一个Java对象，
 * 但是他的状态时非初始化的，在使用这个对象之前绝对要用CallNonvirtualVoidMethod来调用该jclass的构造函数，
 * 这样就可以延迟构造函数的调用
 */
jobject newJavaobj1(JNIEnv *env) {
    jclass obj_clazz = env->FindClass("com/xiaoge/org/jni/Bean");
    // AllocObject创建一个java对象
    jobject jobj = env->AllocObject(obj_clazz);
    // 获取构造方法
    jmethodID constructor_id = env->GetMethodID(obj_clazz, "<init>", "(Ljava/lang/String;)V");
    // 调用构造方法
    jstring jstr = env->NewStringUTF("hello AllocObject");
    env->CallNonvirtualVoidMethod(jobj, obj_clazz, constructor_id, jstr);
    env->DeleteLocalRef(jstr);
    return jobj;
}
/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test12
 * Signature: ()Lcom/xiaoge/org/jni/Bean;
 */
JNIEXPORT jobject JNICALL Data_test12
        (JNIEnv *env, jclass jclazz) {
    return newJavaobj(env);
}

/**
 * Push/PopLocalFrame函数对提供了对局部引用的生命周期更方便的管理。
 * 可以在本地函数的入口处调用PushLocalFrame，然后在出口处调用PopLocalFrame，
 * 这样的话，在两个函数之间任何位置创建的局部引用都会被释放。
 * 如果在函数的入口处调用了PushLocalFrame，那么一定要在函数返回时调用PopLocalFrame。
 *
 * GC在回收java对象时 如果native局部引用变量引用的上层java对象不释放 那么VM GC收集器可能会暂时关闭垃圾收集GCLocker[不同的收集器有不同的策略]。
 */
/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test13
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_test13(JNIEnv *env, jobject jobj) {
    LOGI("Data_test13 Push/PopLocalFrame 管理jni局部变量");
    int expect_localVar_count = 10;
    jint ret = env->PushLocalFrame(expect_localVar_count);

    if (ret == JNI_ERR) {
        LOGI("Data_test13 PushLocalFrame不支持%d个局部变量", expect_localVar_count);
        return;
    }
    jstring jstr = env->NewStringUTF("hello Push/PopLocalFrame");
    env->PopLocalFrame(nullptr);
}

void func() {
    LOGI("thread func");
}
/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test14
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_test14
        (JNIEnv *env, jclass jclazz) {
    LOGI("Data_test14 enter");
    std::thread th(func);
    th.join();

    jmethodID callback_id = env->GetStaticMethodID(jclazz, "callback", "(ILjava/lang/String;)V");
    env->CallStaticVoidMethod(jclazz, callback_id, 110, env->NewStringUTF("hello callback"));

    LOGI("Data_test14 out");
}

jclass jsc;

void func1() {
    int status;
    JNIEnv *env;
    bool isAttached = false;
    status = g_jvm->GetEnv((void **) &env, JNI_VERSION_1_6);
    if (status < 0) {
        g_jvm->AttachCurrentThread(&env, NULL);//将当前线程注册到虚拟机中．
        isAttached = true;
    }

    jmethodID callback_id = env->GetStaticMethodID(jsc, "callback", "(ILjava/lang/String;)V");
    env->CallStaticVoidMethod(jsc, callback_id, 120,
                              env->NewStringUTF("hello Data_test14_1 callback"));
    if (isAttached) {
        g_jvm->DetachCurrentThread();
    }
}
/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test14
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_test14_1
        (JNIEnv *env, jclass jclazz) {
    LOGI("Data_test14_1 enter");
    jsc = static_cast<jclass>(env->NewGlobalRef(jclazz));
    std::thread th(func1);
    th.join();
    LOGI("Data_test14_1 out");
}

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    test_fork
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_test_fork
        (JNIEnv *env, jclass jclazz) {
    LOGI("fork ......");

    if (1)
        return;
    /**
     * 注意arm架构没有除0异常
     */
    pid_t fpid;
    fpid = fork();
    if (fpid < 0) {
        LOGI("fork error ...");
    } else if (fpid == 0) {
        int i = 1 / 0;
        LOGI("I am the child process, res is %d", i);
        LOGI("I am the child process, my process id is %d", getpid());

        // 子进程死亡 不影响主进程
//        int *p = nullptr;
//        *p = 1;
    } else {// fpid为子进程的pid
        int i = 1 / 0;
        LOGI("I am the child process, res is %d", i);
        LOGI("I am the parent process, my process id is %d", getpid());
    }
}

/*
 * Class:     com_xiaoge_org_jni_Data
 * Method:    native_load_so
 * Signature: ()V
 */
JNIEXPORT void JNICALL Data_native_load_so
        (JNIEnv *env, jclass jclazz){
    LOGI(".... native_load_so ....");
}

/*

处理 JNI 临界区需要 VM 的帮助，或者关闭 GC，或者采用 GCLocker 类似的机制，
或者钉住包含对象的子空间，或者仅仅钉住对象。不同的 GCs 采用不同的策略处理 JNI 临界区，
某个收集器的副作用 —— 比如延迟 GC 周期 —— 可能并不会出现在另一个收集器中。

请注意规范中：在临界区中，本地代码不能调用其它 JNI 方法，这仅仅是最低的要求。
测试表明，在规范允许的范围内，实现的质量决定了打破规范时的严重程度。某些 GC 更宽松，而某些会更严格。
如果想要保证可移植性，那么请遵循规范，而不是实现细节。

 */