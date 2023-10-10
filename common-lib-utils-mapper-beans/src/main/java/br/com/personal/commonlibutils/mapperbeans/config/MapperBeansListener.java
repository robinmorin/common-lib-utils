package br.com.personal.commonlibutils.mapperbeans.config;

import br.com.personal.commonlibutils.core.exceptions.MapperRuntimeException;
import br.com.personal.commonlibutils.core.helpers.AppContextHelper;
import br.com.personal.commonlibutils.mapperbeans.annotations.EnableMapperBeans;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.reflections.Reflections;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Slf4j
@Configuration
public class MapperBeansListener implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        if(!AppContextHelper.isMainClassAnnotated(EnableMapperBeans.class)) return;
        var context = event.getApplicationContext();
        try {
            var beanFactory = context.getBeanFactory();
            Reflections reflections = new Reflections(AppContextHelper.getAppInfo().getBasePackage());
            var mapperClass = reflections.getTypesAnnotatedWith(Mapper.class, true);

            mapperClass.stream()
                    .filter(aClass -> !context.containsBean(StringUtils.uncapitalize(aClass.getSimpleName())))
                    .forEach(aClass ->  {
                        var beanName = StringUtils.uncapitalize(aClass.getSimpleName());
                        beanFactory.registerSingleton(beanName, Mappers.getMapper(aClass));
                        log.info("Registered Mapper {}", aClass.getSimpleName());
                    });
        } catch (Exception e){
            log.error("Errors ocurred trying to register the mappers from project. Error: {}", e.getMessage());
            throw new MapperRuntimeException(e);
        }

    }
}
