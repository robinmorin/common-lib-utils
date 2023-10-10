package br.com.personal.commonlibutils.core.helpers;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@SuppressWarnings({"java:S3008", "java:S1144", "java:S3010"})
@Component
public final class BeanStaticHelper {

    private static BeanStaticHelper instance;

    private final ApplicationContext applicationContext;

    private BeanStaticHelper(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
        instance = this;
    }

    public static <T> T getBean(Class<T> tClass) {
        return instance.applicationContext.getBean(tClass);
    }

}

