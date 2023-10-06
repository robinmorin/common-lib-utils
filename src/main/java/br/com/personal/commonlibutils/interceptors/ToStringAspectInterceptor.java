package br.com.personal.commonlibutils.interceptors;

import br.com.personal.commonlibutils.annotations.EnableCommonLibs;
import br.com.personal.commonlibutils.annotations.ToStringJson;
import br.com.personal.commonlibutils.exceptions.MessageError;
import br.com.personal.commonlibutils.json.processor.JsonProcessor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@Aspect
public class ToStringAspectInterceptor {
    private Boolean isAnnotatedCommomLibsActive;

    @Pointcut("within(@br.com.personal.commonlibutils.annotations.ToStringJson *) && execution(* *.toString())")
    public void pointCutToStringJson() {
    }

    @Around("pointCutToStringJson()")
    public Object aroundInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        if(!isAnnotatedCommomLibs()) return joinPoint.proceed();
        String proceedAction = "";
        var objectPoint = joinPoint.getTarget();
        var annotation = objectPoint.getClass().getAnnotation(ToStringJson.class);
        var excludeFields = annotation.ignoreFields();
        try {
            proceedAction = JsonProcessor.toJson(objectPoint, excludeFields);
        } catch (StackOverflowError ex) {
            proceedAction = String.format(MessageError.JSON_TRANSFORM_COMPLEX.getTemplate(), objectPoint.getClass().getSimpleName(), proceedAction);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return proceedAction;
    }

    public boolean isAnnotatedCommomLibs() {
        if(Objects.nonNull(isAnnotatedCommomLibsActive)) return isAnnotatedCommomLibsActive;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try {
            Class<?> mainClass = classLoader.loadClass(System.getProperty("sun.java.command"));
            if(Objects.nonNull(mainClass.getAnnotation(SpringBootApplication.class))){
                return Objects.nonNull(mainClass.getAnnotation(EnableCommonLibs.class)) ?
                        (isAnnotatedCommomLibsActive = Boolean.TRUE) : (isAnnotatedCommomLibsActive = Boolean.FALSE);
            } else
                return (isAnnotatedCommomLibsActive = Boolean.FALSE);
        } catch (ClassNotFoundException e) {
            return (isAnnotatedCommomLibsActive = Boolean.FALSE);
        }
    }
}
