package br.com.personal.commonlibutils.core.helpers;

import br.com.personal.commonlibutils.core.exceptions.AppContextRuntimeException;
import br.com.personal.commonlibutils.core.interfaces.IAppInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.annotation.Annotation;
import java.util.InvalidPropertiesFormatException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringTokenizer;

@SuppressWarnings("java:S3516")
public final class AppContextHelper {

    private AppContextHelper() throws IllegalAccessException {
        throw new IllegalAccessException("Constructor private");
    }

    public static boolean isMainClassAnnotated(Class<? extends Annotation> annotationClass) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Class<?> mainClass = classLoader.loadClass(getAppInfo().getFullMainClass());
            return Objects.nonNull(mainClass.getAnnotation(SpringBootApplication.class)) &&
                   Objects.nonNull(mainClass.getAnnotation(annotationClass));
        } catch (ClassNotFoundException | NoSuchElementException e) {
            return false;
        }
    }

    public static IAppInfo getAppInfo(){
        try {
            StringTokenizer tokenizer = new StringTokenizer(System.getProperty("sun.java.command")," ");
            var fullMainClass = tokenizer.nextToken();
            var lastPoint = fullMainClass.lastIndexOf(".");
            if (lastPoint == -1) throw new InvalidPropertiesFormatException("Invalid value in System properties");
            var basePack = fullMainClass.substring(0, lastPoint);
            var mainClass = fullMainClass.substring(lastPoint+1);
            return new AppInfoObj(basePack, mainClass, fullMainClass);
        } catch (Exception e){
            throw new AppContextRuntimeException(e);
        }
    }

    public static Class<?> getClassFromProject(String className){
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            return classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            throw new AppContextRuntimeException(e);
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class AppInfoObj implements IAppInfo{
        private final String basePackage;
        private final String mainClass;
        private final String fullMainClass;
    }

}
