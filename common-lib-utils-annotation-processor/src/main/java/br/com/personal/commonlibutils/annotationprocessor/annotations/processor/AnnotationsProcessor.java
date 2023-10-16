package br.com.personal.commonlibutils.annotationprocessor.annotations.processor;

import br.com.personal.commonlibutils.manager.annotations.EnableCommonLibs;
import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import java.util.Set;

@SupportedAnnotationTypes({"br.com.personal.commonlibutils.manager.annotations.EnableCommonLibs",
                            "javax.annotation.processing.Generated",
                            "org.aspectj.lang.annotation.Aspect"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@AutoService(Processor.class)
public class AnnotationsProcessor extends AbstractProcessor {
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        return annotations.stream().anyMatch(annot -> annot.getAnnotationsByType(EnableCommonLibs.class).length > 0);
//        if (annotations.stream().anyMatch(annot -> annot.getAnnotationsByType(EnableCommonLibs.class).length > 0)) {
//            annotations.stream()
//                    .filter(annot -> annot.getAnnotationsByType(Aspect.class).length > 0)
//                    .forEach(annot ->
//                            roundEnv.getElementsAnnotatedWith(annot)
//                                    .forEach(element -> processingEnv.getMessager()
//                                            .printMessage(Diagnostic.Kind.WARNING, "A classe aspect " + element.getSimpleName() + " não será compilada.")));
//        }
//        return true;
    }
}
