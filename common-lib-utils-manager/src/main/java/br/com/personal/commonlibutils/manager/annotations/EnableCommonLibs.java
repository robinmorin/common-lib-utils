package br.com.personal.commonlibutils.manager.annotations;

import org.springframework.context.annotation.Configuration;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Configuration
public @interface EnableCommonLibs {

}
