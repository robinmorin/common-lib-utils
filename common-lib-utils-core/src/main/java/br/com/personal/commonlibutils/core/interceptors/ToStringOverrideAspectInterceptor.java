package br.com.personal.commonlibutils.core.interceptors;

import br.com.personal.commonlibutils.core.annotations.EnableCommonLibs;
import br.com.personal.commonlibutils.core.annotations.ToStringJson;
import br.com.personal.commonlibutils.core.exceptions.MessageError;
import br.com.personal.commonlibutils.core.handlers.json.processor.JsonProcessor;
import br.com.personal.commonlibutils.core.helpers.AppContextHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ToStringOverrideAspectInterceptor {
    private Boolean isAnnotatedCommonLibsActive;

    @Pointcut("within(@br.com.personal.commonlibutils.core.annotations.ToStringJson *) && execution(* *.toString())")
    public void pointCutToStringJson() {
    }

    @Around("pointCutToStringJson()")
    public Object aroundInterceptor(ProceedingJoinPoint joinPoint) throws Throwable {
        if(Boolean.FALSE.equals(isAnnotatedCommonLibsActive) ||
                Boolean.FALSE.equals(isAnnotatedCommonLibsActive = AppContextHelper.isMainClassAnnotated(EnableCommonLibs.class)))
            return joinPoint.proceed();

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

}
