package com.cat.zeus.processor;

import com.cat.zeus.annotation.ZeusAPTBindView;
import com.cat.zeus.annotation.ZeusAPTOnClick;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.processing.Processor;
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
public class ZeusAPTBindViewOnClickProcessor1 extends BaseProcessor {
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    /**
     * 扫描到所有的注解回调
     */
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

    /*
        public class MainActivityViewBinding {
            public MainActivity target;
            MainActivityViewBinding(MainActivity target){
                this.target = target;
                bind(target);
            }

            public static void bind(MainActivity _mainActivity) {
                _mainActivity.btn_serializeSingle = (android.widget.Button) (_mainActivity.findViewById(2131165221));
                _mainActivity.tv_hint = (android.widget.TextView) (_mainActivity.findViewById(2131165333));
                _mainActivity.btn_serializeAll = (android.widget.Button) (_mainActivity.findViewById(2131165220));
                _mainActivity.btn_remove = (android.widget.Button) (_mainActivity.findViewById(2131165219));
                _mainActivity.btn_print = (android.widget.Button) (_mainActivity.findViewById(2131165218));
                _mainActivity.et_userName = (android.widget.EditText) (_mainActivity.findViewById(2131165246));
                _mainActivity.et_userAge = (android.widget.EditText) (_mainActivity.findViewById(2131165245));
                _mainActivity.et_singleUserName = (android.widget.EditText) (_mainActivity.findViewById(2131165244));
                _mainActivity.et_bookName = (android.widget.EditText) (_mainActivity.findViewById(2131165243));
            }
        }
    */
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

        int fields_map_size = fields_map.size();
        int methods_map_size = methods_map.size();

        boolean isFieldMap;
        Map<Integer, Element> temp;
        Set<Integer> set;

        if (fields_map_size <= methods_map_size) {
            isFieldMap = true;
            temp = fields_map;
            set = methods_map.keySet();
        } else {
            isFieldMap = false;
            temp = methods_map;
            set = fields_map.keySet();
        }

        Map<Integer, Element> fields_methods_map = new ConcurrentHashMap<>();

        Iterator<Map.Entry<Integer, Element>> iterable = temp.entrySet().iterator();
        while (iterable.hasNext()) {
            Map.Entry<Integer, Element> en = iterable.next();

            if (set.contains(en.getKey())) {
                fields_methods_map.put(en.getKey(), en.getValue());
                iterable.remove();

                if (!isFieldMap) {
                    fields_map.remove(en.getKey());
                } else {
                    methods_map.remove(en.getKey());
                }
            }
        }

        log("fields_map size: " + fields_map.size());
        log("methods_map size: " + methods_map.size());
        log("fields_methods_map size: " + fields_methods_map.size());

        MethodSpec bind = generateBindMethodByPoet(fields_map, methods_map, fields_methods_map);
        FieldSpec target = generateFieldByPoet(element);
        MethodSpec constructor = generateConstructorMethodByPoet(element);

        typeSpecBuilder
                .addModifiers(Modifier.PUBLIC)
                .addField(target)
                .addMethod(constructor)
                .addMethod(bind)
                .build();

        log("---------------------------------------");
    }

    private MethodSpec generateConstructorMethodByPoet(Element element) {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        return builder.build();
    }

    private FieldSpec generateFieldByPoet(Element element) {
        ClassName className = ClassName.bestGuess(((TypeElement) element).getQualifiedName().toString());
        FieldSpec fieldSpec = FieldSpec.builder(className.box(), "target", Modifier.PUBLIC).build();
        return fieldSpec;
    }

    /**
     * _mainActivity.btn_serializeSingle = (android.widget.Button) (_mainActivity.findViewById(2131165221));
     */
    private MethodSpec generateBindMethodByPoet(Map<Integer, Element> fields_map, Map<Integer, Element> methods_map, Map<Integer, Element> fields_methods_map) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("bind");
        builder.addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.VOID);

        if (fields_map != null && fields_map.size() > 0) {
            for (Map.Entry<Integer, Element> en : fields_map.entrySet()) {
                Integer id = en.getKey();
                VariableElement variableElement = (VariableElement) en.getValue();
                String variableElementSimpleName = variableElement.getSimpleName().toString();
                String variableElementType = variableElement.asType().toString();
                log("variableElementSimpleName: " + variableElementSimpleName);
                log("variableElementType: " + variableElementType);
            }
        }
        if (methods_map != null && fields_map.size() > 0) {
            for (Map.Entry<Integer, Element> en : methods_map.entrySet()) {
                Integer id = en.getKey();
                ExecutableElement executableElement = (ExecutableElement) en.getValue();
                String executableElementSimpleName = executableElement.getSimpleName().toString();
                String executableElementType = executableElement.asType().toString();
                log("executableElementSimpleName: " + executableElementSimpleName);
                log("executableElementType: " + executableElementType);
            }
        }



        if (fields_methods_map != null && fields_map.size() > 0) {
            for (Map.Entry<Integer, Element> en : methods_map.entrySet()) {
                Integer id = en.getKey();
                ExecutableElement executableElement = (ExecutableElement) en.getValue();
                String executableElementSimpleName = executableElement.getSimpleName().toString();
                String executableElementType = executableElement.asType().toString();
                log("executableElementSimpleName: " + executableElementSimpleName);
                log("executableElementType: " + executableElementType);
            }
        }
        return builder.build();
    }
}
