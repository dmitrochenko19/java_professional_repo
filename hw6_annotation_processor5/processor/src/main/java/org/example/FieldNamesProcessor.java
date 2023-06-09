package org.example;

import com.google.auto.service.AutoService;


import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes("org.example.CustomToString")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class FieldNamesProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for(TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element annotatedElement : annotatedElements) {
                Map<Boolean, List<Element>> annotatedMethods = annotatedElement.getEnclosedElements().stream().collect(Collectors.partitioningBy(element -> element.getSimpleName().toString().startsWith("get")));
                List<Element> getters = annotatedMethods.get(true);

                if (getters.isEmpty()) {
                    continue;
                }
                String className = ((TypeElement) getters.get(0).getEnclosingElement()).getQualifiedName().toString();
                List<String> stringGetters = getters.stream().map(getter -> getter.getSimpleName().toString()).collect(Collectors.toList());
                try {
                    writeBuilderFile(className, stringGetters);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }


    private void writeBuilderFile(String className, List<String> getters) throws IOException {
        String packageName = null;
        int lastDot = className.lastIndexOf('.');
        if (lastDot > 0) {
            packageName = className.substring(0, lastDot);
        }
        String simpleClassName = className.substring(lastDot + 1);
        String toStringsClassName = "ToString"+className.substring(className.lastIndexOf('.')+1);
        JavaFileObject builderFile = processingEnv.getFiler().createSourceFile(toStringsClassName);
        try (PrintWriter out = new PrintWriter(builderFile.openWriter())) {
            if (packageName != null) {
                out.print("package ");
                out.print(packageName);
                out.println(";");
                out.println();
            }
            out.print("public class ");
            out.print(toStringsClassName);
            out.println(" {");
            out.println();
            out.print(" public static String toString("+simpleClassName+" object){");
            out.println();
            out.print(" return ");
            String result = getters.stream().map(m -> "object." + m + "()").collect(Collectors.joining("+\",\"+"));
            out.println(result + ";");
            out.println("    }");
            out.println("}");
        }
    }

}

