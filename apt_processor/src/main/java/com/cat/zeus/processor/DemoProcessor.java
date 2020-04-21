package com.cat.zeus.processor;

import com.cat.zeus.annotation.ZeusAPTBindView;

import java.util.List;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.element.VariableElement;

public class DemoProcessor extends BaseProcessor {
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return super.getSupportedAnnotationTypes();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 扫描整个工程 找到所有包含ZeusAPTBindView的元素
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ZeusAPTBindView.class);
        for (Element e : elements) {
            if (e instanceof TypeElement) {
                // 打印包信息
                PackageElement packageElement = processingEnv.getElementUtils().getPackageOf(e);
                String packageQualifiedName = packageElement.getQualifiedName().toString();
                String packageSimpleName = packageElement.getSimpleName().toString();
                log("packageQualifiedName: " + packageQualifiedName);
                log("packageSimpleName: " + packageSimpleName);

                // 打印泛型信息
                List<? extends TypeParameterElement> typeParameterElements = ((TypeElement) e).getTypeParameters();
                for (TypeParameterElement typeParameterElement : typeParameterElements) {
                    log("typeParameterElement" + typeParameterElement.getSimpleName().toString());
                }
            } else if (e instanceof ExecutableElement) {

            } else if (e instanceof VariableElement) {

            }
        }
        return true;
    }
}
