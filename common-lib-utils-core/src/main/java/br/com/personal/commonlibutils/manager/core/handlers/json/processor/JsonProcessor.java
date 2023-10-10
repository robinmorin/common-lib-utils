package br.com.personal.commonlibutils.manager.core.handlers.json.processor;

import br.com.personal.commonlibutils.manager.core.exceptions.JsonRuntimeException;
import br.com.personal.commonlibutils.manager.core.handlers.json.provider.MapperJsonProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public class JsonProcessor {

    private static final MapperJsonProvider mapperJsonProvider = new MapperJsonProvider();
    private static ObjectMapper jsonMapper = mapperJsonProvider.get();

    private static final Consumer<String[]> applyFilter =
            excludeFields -> {
                jsonMapper = mapperJsonProvider.get();
                if(excludeFields != null){
                    jsonMapper.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
                        private final List<String> exclusions = List.of(excludeFields);
                        @Override
                        public boolean hasIgnoreMarker(final AnnotatedMember m) {
                            return exclusions.contains(m.getName()) || super.hasIgnoreMarker(m);
                        }
                    });
                }
            };

    private JsonProcessor() throws IllegalAccessException { throw new IllegalAccessException("Constructor Private");}


    public static String toJson(Object object, String...excludeFields) {
        try {
            applyFilter.accept(excludeFields);
            return jsonMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new JsonRuntimeException(String.format("Error processing object type %s to Json", object.getClass().getSimpleName()), e.getCause());
        }
    }


    public static <T>  T toObject(String json, Class<T> typeParameterClass) {
        try {
            return jsonMapper.readValue(json.getBytes(), typeParameterClass);
        } catch (IOException e) {
            throw new JsonRuntimeException(String.format("Error processing from Json to object type %s", typeParameterClass.getSimpleName()), e.getCause());
        } finally {
            jsonMapper.setFilterProvider(null);
        }
    }

    public static <T,S> T convertTo(S object, Class<T> tClass) {
        try {
            return jsonMapper.convertValue(object, tClass);
        } catch (IllegalArgumentException e) {
            throw new JsonRuntimeException(String.format("Error processing converting from object type %s to object type %s", object.getClass().getSimpleName(), tClass.getSimpleName()), e.getCause());
        }
    }
}

