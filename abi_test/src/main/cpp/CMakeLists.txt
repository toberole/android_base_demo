cmake_minimum_required(VERSION 3.4.1)

# 清除符号表 减小so体积
#set(CMAKE_C_FLAGS_RELEASE "${CMAKE_C_FLAGS_RELEASE} -s")
#set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -s")

include_directories(${CMAKE_CURRENT_LIST_DIR}/include)

aux_source_directory(${CMAKE_CURRENT_LIST_DIR}/src SRC_FILES)

add_library(native-lib SHARED ${SRC_FILES})

find_library(libs
        log
        android
        )

# 指定生成so名字为native-lib,编译器会自动为生成一个"lib"+"so name"的so库
target_link_libraries(native-lib ${libs})