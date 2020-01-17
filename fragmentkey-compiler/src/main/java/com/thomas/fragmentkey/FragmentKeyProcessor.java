package com.thomas.fragmentkey;


import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class FragmentKeyProcessor extends AbstractProcessor {
    private Filer mFiler;
    private Elements mElementUtils;
    private Messager mMessager;
    private Elements elementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new LinkedHashSet<>();
        annotationTypes.add(Inject.class.getCanonicalName());
        return annotationTypes;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        Set<? extends Element> injectElements = roundEnv.getElementsAnnotatedWith(Inject.class);
        Map<TypeElement, List<FieldBundle>> maps = new HashMap<>();
        getFields(injectElements, maps);
        Set<TypeElement> typeElements = maps.keySet();
        for (TypeElement element : typeElements) {

            MethodSpec.Builder attach = MethodSpec.methodBuilder("onAttach")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(ClassName.get("android.content", "Context"), "context")
                    .addStatement("super.onAttach(context)")
                    .addStatement("$T arguments = getArguments()", ClassName.get("android.os", "Bundle"))
                    .returns(TypeName.VOID);

            MethodSpec.Builder resolve = MethodSpec.methodBuilder("send")
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement(String.format("%s target =new %s()", element.getSimpleName().toString(), element.getSimpleName().toString() + "Key"))
                    .addStatement("$T bundle = new $T()", ClassName.get("android.os", "Bundle"), ClassName.get("android.os", "Bundle"))
                    .returns(ClassName.get(element.asType()));


            for (FieldBundle fieldBundle : maps.get(element)) {
                TypeName type = fieldBundle.getType();
                String put = "";
                String get = "";
                if (type instanceof ParameterizedTypeName) {
                    ParameterizedTypeName type1 = (ParameterizedTypeName) type;
                    ClassName rawType = type1.rawType;
                    if (rawType.box().equals(ClassName.get(ArrayList.class))) {
                        List<TypeName> typeArguments = type1.typeArguments;
                        if (typeArguments.size() == 1) {
                            TypeName typeName = type1.typeArguments.get(0);
                            if (typeName.equals(ClassName.get(String.class))) {
                                resolve.addParameter(type, fieldBundle.getName());
                                put = String.format("bundle.putStringArrayList(\"%s\",%s)", fieldBundle.getName(), fieldBundle.getName());
                                get = String.format("this.%s = arguments.getStringArrayList(\"%s\")", fieldBundle.getOriginName(),
                                        fieldBundle.getName());
                            }
                            if (typeName.box().equals(ClassName.get(Integer.class))) {
                                resolve.addParameter(type, fieldBundle.getName());
                                put = String.format("bundle.putIntegerArrayList(\"%s\",%s)", fieldBundle.getName(), fieldBundle.getName());
                                get = String.format("this.%s = arguments.getIntegerArrayList(\"%s\")", fieldBundle.getOriginName(),
                                        fieldBundle.getName());
                            }
                            if (typeName.equals(ClassName.get("android.os", "Parcelable"))) {
                                resolve.addParameter(type, fieldBundle.getName());
                                put = String.format("bundle.putParcelableArrayList(\"%s\",%s)", fieldBundle.getName(), fieldBundle.getName());
                                get = String.format("this.%s = arguments.getParcelableArrayList(\"%s\")", fieldBundle.getOriginName(),
                                        fieldBundle.getName());
                            }
                        }
                    }
                }else {
                    if (type.equals(ClassName.get(String.class))) {
                        resolve.addParameter(type, fieldBundle.getName());
                        put = String.format("bundle.putString(\"%s\",%s)", fieldBundle.getName(), fieldBundle.getName());
                        get = String.format("this.%s = arguments.getString(\"%s\",this.%s)", fieldBundle.getOriginName(),
                                fieldBundle.getName(), fieldBundle.getOriginName());
                    }
                    if (type.box().equals(ClassName.get(Integer.class))) {
                        resolve.addParameter(type, fieldBundle.getName());
                        put = String.format("bundle.putInt(\"%s\",%s)", fieldBundle.getName(), fieldBundle.getName());
                        get = String.format("this.%s = arguments.getInt(\"%s\",this.%s)", fieldBundle.getOriginName(),
                                fieldBundle.getName(), fieldBundle.getOriginName());
                    }
                    if (type.box().equals(ClassName.get(Boolean.class))) {
                        resolve.addParameter(type, fieldBundle.getName());
                        put = String.format("bundle.putBoolean(\"%s\",%s)", fieldBundle.getName(), fieldBundle.getName());
                        get = String.format("this.%s = arguments.getBoolean(\"%s\",this.%s)", fieldBundle.getOriginName(),
                                fieldBundle.getName(), fieldBundle.getOriginName());
                    }
                    if (type.box().equals(ClassName.get(Float.class))) {
                        resolve.addParameter(type, fieldBundle.getName());
                        put = String.format("bundle.putFloat(\"%s\",%s)", fieldBundle.getName(), fieldBundle.getName());
                        get = String.format("this.%s = arguments.getFloat(\"%s\",this.%s)", fieldBundle.getOriginName(),
                                fieldBundle.getName(), fieldBundle.getOriginName());
                    }
                    if (type.box().equals(ClassName.get(Double.class))) {
                        resolve.addParameter(type, fieldBundle.getName());
                        put = String.format("bundle.putDouble(\"%s\",%s)", fieldBundle.getName(), fieldBundle.getName());
                        get = String.format("this.%s = arguments.getDouble(\"%s\",this.%s)", fieldBundle.getOriginName(),
                                fieldBundle.getName(), fieldBundle.getOriginName());
                    }
                    if (type.box().equals(ClassName.get(Long.class))) {
                        resolve.addParameter(type, fieldBundle.getName());
                        put = String.format("bundle.putLong(\"%s\",%s)", fieldBundle.getName(), fieldBundle.getName());
                        get = String.format("this.%s = arguments.getLong(\"%s\",this.%s)", fieldBundle.getOriginName(),
                                fieldBundle.getName(), fieldBundle.getOriginName());
                    }
                    if (type.box().equals(ClassName.get(Serializable.class))) {
                        resolve.addParameter(type, fieldBundle.getName());
                        put = String.format("bundle.putSerializable(\"%s\",%s)", fieldBundle.getName(), fieldBundle.getName());
                        get = String.format("this.%s = arguments.getSerializable(\"%s\")", fieldBundle.getOriginName(),
                                fieldBundle.getName());
                    }
                    if (type.box().equals(ClassName.get("android.os", "Parcelable"))) {
                        resolve.addParameter(type, fieldBundle.getName());
                        put = String.format("bundle.putParcelable(\"%s\",%s)", fieldBundle.getName(), fieldBundle.getName());
                        get = String.format("this.%s = arguments.getParcelable(\"%s\")", fieldBundle.getOriginName(),
                                fieldBundle.getName());
                    }
                }
                if (!get.equals("") && !put.equals("")) {
                    attach.addStatement(get);
                    resolve.addStatement(put);
                }
            }
            resolve.addStatement("target.setArguments(bundle)");
            resolve.addStatement("return target");

            TypeSpec clazz = TypeSpec.classBuilder(element.getSimpleName().toString() + "Key")
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(ClassName.get(element.asType()))
                    .addMethod(resolve.build())
                    .addMethod(attach.build())
                    .build();
            JavaFile javaFile = JavaFile.builder(getPackageName(element), clazz).build();
            try {
                javaFile.writeTo(mFiler);
            } catch (IOException e) {
                e.printStackTrace();
                mMessager.printMessage(Diagnostic.Kind.WARNING, element.getSimpleName()+"解析失败");
            }
        }
        return true;
    }

    private void getFields(Set<? extends Element> injectElements, Map<TypeElement, List<FieldBundle>> maps) {
        for (Element element : injectElements) {
            TypeElement clazz = (TypeElement) element.getEnclosingElement();
            VariableElement variableElement = (VariableElement) element;
            if (variableElement.getModifiers().contains(Modifier.PRIVATE)) {
                continue;
            }

            Inject annotation = variableElement.getAnnotation(Inject.class);
            String originName = variableElement.getSimpleName().toString();
            String name;
            if (annotation != null && !"".equals(annotation.name().trim())) {
                name = annotation.name().trim();
            } else if (originName.startsWith("m")) {
                name = originName.substring(2);
                name = originName.substring(1, 2).toLowerCase() + name;
            } else {
                name = originName;
            }
            List<FieldBundle> fieldBundles = maps.get(clazz);

            FieldBundle fieldBundle = new FieldBundle(originName, name, ClassName.get(variableElement.asType()), clazz);
            if (fieldBundles == null) {
                fieldBundles = new ArrayList<>();
                maps.put(clazz, fieldBundles);
            }
            fieldBundles.add(fieldBundle);
            //  mMessager.printMessage(Diagnostic.Kind.WARNING, fieldBundle.toString());
        }
    }


    private String getPackageName(TypeElement type) {
        return elementUtils.getPackageOf(type).getQualifiedName().toString();
    }


}