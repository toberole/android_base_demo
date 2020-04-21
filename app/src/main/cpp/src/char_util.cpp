#include <string>
#include <jni.h>
/**
* java内部是使用的16bit的unicode编码（utf-16）来表示字符串的，无论英文还是中文都是2字节；
* C/C++使用的是原始数据，ascii就是一个字节，中文一般是GB2312编码，用2个字节表示一个汉字。
*/

/**
 * java unicode字符串->C/C++ 汉字,java字符串映射到JNI层是jstring类型，
 * 1、如果其中不包含汉字，可以调用GetStringUTFChars/GetStringChars将它转化成一个UTF-8/UTF-16字符串；
 * 2、如果包含汉字，则需要使用java.lang.String的API先将它转化成一个jbytearray，然后将这个jarray对象 里的内容复制到char类型的内存中。
*/

/**
 * Unicode字符串->char *
 */
char *GetStringUnicodeChars(JNIEnv *env, jstring jsrc, const char *encoding) {
    char *rtn = NULL;
    // 第一步 先转换为byte数组
    jclass clazz = env->FindClass("java/lang/String");
    jmethodID methodID = env->GetMethodID(clazz, "getBytes", "(Ljava/lang/String;)[B");
    jstring jencoding = env->NewStringUTF(encoding);
    jbyteArray jbyte_array = (jbyteArray) env->CallObjectMethod(jsrc, methodID, jencoding);

    jsize byte_array_len = env->GetArrayLength(jbyte_array);
    jbyte *jbyte_array_ptr = env->GetByteArrayElements(jbyte_array, JNI_FALSE);
    if (byte_array_len > 0) {
        rtn = (char *) malloc(byte_array_len + 1);
        memcpy(rtn, jbyte_array_ptr, byte_array_len);
        rtn[byte_array_len] = 0;
    }

    env->ReleaseByteArrayElements(jbyte_array, jbyte_array_ptr, 0);

    return rtn;
}

/**
 * C/C++ 汉字->Java unicode字符串
 * jni返回给java的字符串，c/c++首先应该负责把这个字符串变成UTF-8或者UTF-16格式，
 * 然后通过NewStringUTF 或者 NewString来把它封装成jstring，最后返回给java。
 *
 * 1、如果字符串中不含中文字符，只是标准的ascii码，那么用NewString/NewStringUTF就可以构造返回结果；
 * 2、如果字符串中有中文字符，那么需要用Java String的API来构造jstring，其本质是调用String的构造函数 public String (byte[] data, String charsetName)
 */
//NewStringUnicode:Unicode char * -> jstring
jstring NewStringUnicode(JNIEnv *env, const char *src, const char *encoding) {
    jbyteArray byte_array = env->NewByteArray(strlen(src));
    env->SetByteArrayRegion(byte_array, 0, strlen(src), (jbyte *) src);
    jstring jencoding = env->NewStringUTF(encoding);

    jclass clazz = env->FindClass("java/lang/String");
    jmethodID methodID = env->GetMethodID(clazz, "<init>", "([BLjava/lang/String;)V");
    return (jstring) env->NewObject(clazz, methodID, byte_array, jencoding);
}