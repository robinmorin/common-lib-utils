package br.com.personal.commonlibutils.core.helpers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ResolvableType;

import javax.annotation.PostConstruct;
import java.util.Optional;

@SuppressWarnings({"java:S3008", "java:S1144", "java:S3010"})
@Configuration
public class BeanStaticHelper {

    private static BeanStaticHelper INSTANCE;

    @Autowired
    private ApplicationContext applicationContext;

    public static <T> T getBean(Class<T> tClass) {
        return INSTANCE.applicationContext.getBean(tClass);
    }

    public static <T> T getBean(String className) {
        return (T) INSTANCE.applicationContext.getBean(className);
    }

    public static <T> T getBeanByGeneric(Class tClass, Class tClassGeneric) {
        var tClasses = INSTANCE.applicationContext.getBeanNamesForType(ResolvableType.forClassWithGenerics(tClass, tClassGeneric));
        return getBean(tClasses[0]);
    }

    public static String getProperty(String name, String defaultValue) {
        return Optional.ofNullable(INSTANCE.applicationContext.getEnvironment().getProperty(name)).orElse(defaultValue);
    }

    public static String getProperty(String name) {
        return INSTANCE.applicationContext.getEnvironment().getProperty(name);
    }

    @PostConstruct
    public void registerInstance() {
        INSTANCE = this;
    }

}


