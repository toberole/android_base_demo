package com.cat.zeus.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

// 使用注解
@SupportedAnnotationTypes({"com.cat.zeus.annotation.ZeusAPTOnClick"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ZeusAPTOnClickProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("ZeusAPTOnClickProcessor#process");
        return false;
    }
}
