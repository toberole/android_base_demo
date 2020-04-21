//
// Created by Apple on 2019/11/18.
//

#ifndef JNI_XXXX_DEMO_C_FUNC_H
#define JNI_XXXX_DEMO_C_FUNC_H

// 按照C的方法签名 使得C语言可以调用
#ifdef __cplusplus
extern "C" {
#endif

int add_cpp_func(int a, int b);

#ifdef __cplusplus
}
#endif
#endif //JNI_XXXX_DEMO_C_FUNC_H
