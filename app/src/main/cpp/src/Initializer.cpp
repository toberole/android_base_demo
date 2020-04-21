#include <jni.h>
#include "log.h"
#include "Global.h"
#include "test.h"

/**
 * 动态注册的参数
 */
const JNINativeMethod Test_Methods[] = {
        {
                "test",// java层的方法
                "(BCSIFDLjava/lang/String;Ljava/lang/Object;)V",// java层方法对应的签名
                (void *) Test_test// native 层方法
        },
        {
                "test1",// java层的方法
                "(B)V",// java层方法对应的签名
                (void *) Test_test1// native 层方法
        },
        {
                "test2",// java层的方法
                "(ILjava/lang/Object;)V",// java层方法对应的签名
                (void *) Test_test2// native 层方法
        }
};

const JNINativeMethod Test1_Methods[] = {
        {
                "sysHello",// java层的方法
                "(Ljava/lang/String;)I",// java层方法对应的签名
                (void *) Test1_sysHello// native 层方法
        }
};

const JNINativeMethod Data_Methods[] = {
        {
                "test",// java层的方法
                "()V",// java层方法对应的签名
                (void *) Data_test// native 层方法
        },
        {
                "test1",// java层的方法
                "()V",// java层方法对应的签名
                (void *) Data_test1// native 层方法
        },
        {
                "test2",// java层的方法
                "(BCSIJFDZ)V",// java层方法对应的签名
                (void *) Data_test2// native 层方法
        },
        {
                "test3",// java层的方法
                "(Lcom/xiaoge/org/jni/Bean;)V",// java层方法对应的签名
                (void *) Data_test3// native 层方法
        },
        {
                "test4",// java层的方法
                "(Ljava/lang/String;)V",// java层方法对应的签名
                (void *) Data_test4// native 层方法
        },
        {
                "test5",// java层的方法
                "([II)V",// java层方法对应的签名
                (void *) Data_test5// native 层方法
        },
        {
                "test5_1",// java层的方法
                "([Ljava/lang/String;)V",// java层方法对应的签名
                (void *) Data_test5_11// native 层方法
        },
        {
                "test5_2",// java层的方法
                "([B)V",// java层方法对应的签名
                (void *) Data_test5_12// native 层方法
        },
        {
                "test5_3",// java层的方法
                "([C)V",// java层方法对应的签名
                (void *) Data_test5_13// native 层方法
        },
        {
                "test5_4",// java层的方法
                "(Ljava/lang/Object;)V",// java层方法对应的签名
                (void *) Data_test5_14// native 层方法
        },
        {
                "test5_4_1",// java层的方法
                "(Lcom/xiaoge/org/jni/Data$Callback;)V",// 注意内部类和内部接口的签名
                (void *) Data_test5_14_11// native 层方法
        },
        {
                "test5_5",// java层的方法
                "(Lcom/xiaoge/org/jni/Gender;)V",
                (void *) Data_test5_15// native 层方法
        },
        {
                "test6",// java层的方法
                "()B",
                (void *) Data_test6// native 层方法
        },
        {
                "test7",// java层的方法
                "()C",
                (void *) Data_test7// native 层方法
        },
        {
                "test8",// java层的方法
                "()I",
                (void *) Data_test8// native 层方法
        },
        {
                "test9",// java层的方法
                "()Ljava/lang/String;",
                (void *) Data_test9// native 层方法
        },
        {
                "test10",// java层的方法
                "()[I",
                (void *) Data_test10// native 层方法
        },
        {
                "test11",// java层的方法
                "()Lcom/xiaoge/org/jni/Bean;",
                (void *) Data_test11// native 层方法
        },
        {
                "test12",// java层的方法
                "()Lcom/xiaoge/org/jni/Bean;",
                (void *) Data_test12// native 层方法
        },
        {
                "test13",// java层的方法
                "()V",// java层方法对应的签名
                (void *) Data_test13// native 层方法
        },
        {
                "test14",// java层的方法
                "()V",// java层方法对应的签名
                (void *) Data_test14// native 层方法
        },
        {
                "test14_1",// java层的方法
                "()V",// java层方法对应的签名
                (void *) Data_test14_1// native 层方法
        },
        {
                "test_fork",// java层的方法
                "()V",// java层方法对应的签名
                (void *) Data_test_fork// native 层方法
        },
        {
                "native_load_so",// java层的方法
                "()V",// java层方法对应的签名
                (void *) Data_native_load_so// native 层方法
        }
};

jint startReg(JNIEnv *env) {
    jclass cla = env->FindClass(Test_Clazz);
    if (cla == nullptr) {
        LOGI("找不到类 %s", Test_Clazz);
        return JNI_ERR;
    }

    int method_count = sizeof(Test_Methods) / sizeof(Test_Methods[0]);
    LOGI("%s类 method_count=%d", Test_Clazz, method_count);
    int ret = env->RegisterNatives(cla, Test_Methods, method_count);
    if (ret == JNI_ERR) {
        LOGI("Register类 %s失败", Test_Clazz);
        return JNI_ERR;
    }

    cla = env->FindClass(Test1_Clazz);
    if (cla == nullptr) {
        LOGI("找不到类 %s", Test1_Clazz);
        return JNI_ERR;
    }

    method_count = sizeof(Test1_Methods) / sizeof(Test1_Methods[0]);
    LOGI("%s类 method_count=%d", Test1_Clazz, method_count);
    ret = env->RegisterNatives(cla, Test1_Methods, method_count);
    if (ret == JNI_ERR) {
        LOGI("Register类 %s失败", Test1_Clazz);
        return JNI_ERR;
    }

    cla = env->FindClass(Data_Clazz);
    if (cla == nullptr) {
        LOGI("找不到类 %s", Data_Clazz);
        return JNI_ERR;
    }

    method_count = sizeof(Data_Methods) / sizeof(Data_Methods[0]);
    LOGI("%s类 method_count=%d", Data_Clazz, method_count);
    ret = env->RegisterNatives(cla, Data_Methods, method_count);
    if (ret == JNI_ERR) {
        LOGI("Register类 %s失败", Data_Clazz);
        return JNI_ERR;
    }

    return ret;
}

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGI("... JNI_OnLoad ...");

    JNIEnv *env = NULL;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }

    jint result = startReg(env);

    g_jvm = vm;
    LOGD("RegisterNatives result: %d", result);
    return JNI_VERSION_1_6;
}



