package br.com.personal.commonlibutils.manager.core.interceptors;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclarePrecedence;

@Aspect
@DeclarePrecedence("br.com.personal.commonlibutils.core.interceptors.ToStringOverrideAspectInterceptor")
public class AspectOrderConfiguration {
    // In the future, others aspect class its being including in the list above for set execution order
}
