package br.com.personal.commonlibutils.manager.core.handlers.json.provider;

import br.com.personal.commonlibutils.manager.core.interfaces.ISinglePrototype;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.util.Supplier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapperJsonProvider implements Supplier<ObjectMapper> {

    private final MapperPrototype prototype = new MapperPrototype();
    private static class MapperPrototype implements ISinglePrototype<ObjectMapper> {
        private static final List<?> onFeatures = List.of(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,
                SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS,
                DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        private static final List<?> offFeatures = List.of(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                SerializationFeature.FAIL_ON_EMPTY_BEANS);

        public ObjectMapper newInstance() {
            var objMap = new ObjectMapper();
             onFeatures.stream().filter(SerializationFeature.class::isInstance).map(c->(SerializationFeature)c).forEach(objMap::enable);
             onFeatures.stream().filter(DeserializationFeature.class::isInstance).map(c->(DeserializationFeature)c).forEach(objMap::enable);
             offFeatures.stream().filter(SerializationFeature.class::isInstance).map(c->(SerializationFeature)c).forEach(objMap::disable);
             offFeatures.stream().filter(DeserializationFeature.class::isInstance).map(c->(DeserializationFeature)c).forEach(objMap::disable);
            return objMap;
        }
    }

    public ObjectMapper get() {
        return prototype.newInstance();
    }
}
