package com.cat.zeus.processor;

import com.cat.zeus.annotation.ZeusAPTBindView;
import com.cat.zeus.annotation.ZeusAPTOnClick;
import com.cat.zeus.utils.ElementUtils;
import com.cat.zeus.utils.StringUtils;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;

@SupportedAnnotationTypes(
        {
                "com.cat.zeus.annotation.ZeusAPTBindView",
                "com.cat.zeus.annotation.ZeusAPTOnClick"
        }
)
public class ZeusAPTBindViewOnClickProcessor2 extends BaseProcessor {
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set == null || set.size() == 0) return true;

        Set<? extends Element> methodElements = roundEnvironment.getElementsAnnotatedWith(ZeusAPTOnClick.class);
        Set<? extends Element> fieldElements = roundEnvironment.getElementsAnnotatedWith(ZeusAPTBindView.class);

        Map<Element, List<Element>> map = new HashMap<>();

        for (Element e : methodElements) {
            Element enclosingElement = e.getEnclosingElement();
            List<Element> list = map.get(enclosingElement);
            if (list == null) {
                list = new ArrayList<>();
                map.put(enclosingElement, list);
            }
            list.add(e);
        }

        for (Element e : fieldElements) {
            Element enclosingElement = e.getEnclosingElement();
            List<Element> list = map.get(enclosingElement);
            if (list == null) {
                list = new ArrayList<>();
                map.put(enclosingElement, list);
            }
            list.add(e);
        }

        for (Map.Entry<Element, List<Element>> en : map.entrySet()) {
            Element key = en.getKey();
            List<Element> value = en.getValue();
            generateFileByPoet(key, value);
        }

        return true;
    }

    private void generateFileByPoet(Element element, List<Element> elements) {
        log("---------------------------------------");

        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(element.getSimpleName() + "ViewBinding");

        Map<Integer, Element> fields_map = new ConcurrentHashMap<>();
        Map<Integer, Element> methods_map = new ConcurrentHashMap<>();

        for (Element e : elements) {
            if (e instanceof VariableElement) {
                ZeusAPTBindView zeusAPTBindView = e.getAnnotation(ZeusAPTBindView.class);
                Integer id = zeusAPTBindView.value();
                fields_map.put(id, e);
            } else if (e instanceof ExecutableElement) {
                ZeusAPTOnClick zeusAPTOnClick = e.getAnnotation(ZeusAPTOnClick.class);
                Integer id = zeusAPTOnClick.value();
                methods_map.put(id, e);
            }
        }

        MethodSpec bind = generateBindMethodByPoet(element, fields_map, methods_map);
        FieldSpec target = generateFieldByPoet(element);
        MethodSpec constructor = generateConstructorMethodByPoet(element);

        typeSpecBuilder
                .addModifiers(Modifier.PUBLIC)
                .addField(target)
                .addMethod(constructor)
                .addMethod(bind);
        String packageName = ElementUtils.getPackageName(elementUtils, (TypeElement) element);

        try {
            JavaFile javaFile = JavaFile.builder(packageName, typeSpecBuilder.build()).build();
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        log("---------------------------------------");
    }

    private MethodSpec generateBindMethodByPoet(Element element, Map<Integer, Element> fields_map, Map<Integer, Element> methods_map) {
        ClassName className = ClassName.bestGuess(element.getSimpleName().toString());
        String parameter = "_" + StringUtils.toLowerCaseFirstChar(className.simpleName());

        MethodSpec.Builder builder = MethodSpec.methodBuilder("bind");
        builder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.VOID)
                .addParameter(className, parameter);


        if (fields_map != null && fields_map.size() > 0) {
            String s = "{0}.{1} = ({2})({3}.findViewById({4}));\n";
            for (Map.Entry<Integer, Element> en : fields_map.entrySet()) {
                Integer id = en.getKey();
                VariableElement variableElement = (VariableElement) en.getValue();

                String variableElementSimpleName = variableElement.getSimpleName().toString();
                String variableElementType = variableElement.asType().toString();
                log("variableElementSimpleName: " + variableElementSimpleName);
                log("variableElementType: " + variableElementType);
                builder.addCode(MessageFormat.format(s, parameter, variableElementSimpleName, variableElementType, parameter, String.valueOf(id)));
            }
        }

        if (methods_map != null && fields_map.size() > 0) {
            String s = "{0}.findViewById({1}).setOnClickListener(new android.view.View.OnClickListener() '{'public void onClick(android.view.View v) '{'{2}.{3}();}});\n";
            for (Map.Entry<Integer, Element> en : methods_map.entrySet()) {
                Integer id = en.getKey();
                ExecutableElement executableElement = (ExecutableElement) en.getValue();
                String executableElementSimpleName = executableElement.getSimpleName().toString();
                String executableElementType = executableElement.asType().toString();
                log("executableElementSimpleName: " + executableElementSimpleName);
                log("executableElementType: " + executableElementType);

                builder.addCode(MessageFormat.format(s, parameter, String.valueOf(id), parameter, executableElementSimpleName));
            }
        }
        return builder.build();
    }

    private MethodSpec generateConstructorMethodByPoet(Element element) {
        ClassName className = ClassName.bestGuess(((TypeElement) element).getQualifiedName().toString());
        String parameter = "target";
        MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(className, parameter)
                .addCode("this.target = target;\nbind(target);\n");
        return constructor.build();
    }

    private FieldSpec generateFieldByPoet(Element element) {
        ClassName className = ClassName.bestGuess(((TypeElement) element).getQualifiedName().toString());
        FieldSpec fieldSpec = FieldSpec.builder(className.box(), "target", Modifier.PUBLIC).build();
        return fieldSpec;
    }
}
