通过apt(Annotation Processing Tools)技术，原理是在某些代码元素上（如类型、函数、字段等）添加注解，
在编译时编译器会检查 AbstractProcessor 的子类，并且调用该类型的process函数，
然后将添加了注解的所有元素都传递到process函数中，使得开发人员可以在编译器进行相应的处理.


创建 resources 告诉编译器在编译的时候使用哪个注解处理器[可以直接使用com.google.auto.service:auto-service]
1、在 java library 的 main 目录下创建文件 resources/META-INF/services/javax.annotation.processing.Processor
2、在文件中填写处理器路径 

