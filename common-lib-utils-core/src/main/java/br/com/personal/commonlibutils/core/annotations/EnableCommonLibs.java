package br.com.personal.commonlibutils.core.annotations;

import br.com.personal.commonlibutils.core.EnablingCommonLibUtilsConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EnablingCommonLibUtilsConfig.class)
public @interface EnableCommonLibs {

}
