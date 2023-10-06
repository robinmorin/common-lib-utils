package br.com.personal.commonlibutils.json.processor;

import br.com.personal.commonlibutils.json.provider.MapperJsonProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

/***
 * Classe JsonUtils
 * - Contem metodos com tratamento de exception para facilitar
 *   a serialização e deserialização JSON.
 */
public class JsonProcessor {

    private static final MapperJsonProvider mapperJsonProvider = new MapperJsonProvider();
    private static ObjectMapper JSON = mapperJsonProvider.get();

    private static final Consumer<String[]> applyFilter =
            excludeFields -> {
                JSON = mapperJsonProvider.get();
                if(excludeFields != null){
                    JSON.setAnnotationIntrospector(new JacksonAnnotationIntrospector() {
                        final List<String> exclusions = List.of(excludeFields);
                        @Override
                        public boolean hasIgnoreMarker(final AnnotatedMember m) {
                            return exclusions.contains(m.getName()) || super.hasIgnoreMarker(m);
                        }
                    });
                }
            };

    private JsonProcessor() throws IllegalAccessException { throw new IllegalAccessException("Constructor Private");}

    /***
     * Serializa um objeto e retorna um String em formato JSON
     * @param object
     * @return String
     */
    public static String toJson(Object object, String...excludeFields) {
        try {
            applyFilter.accept(excludeFields);
            return JSON.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error processing object type %s to Json", object.getClass().getSimpleName()), e.getCause());
        }
    }


    /***
     * Desserializa um String em formato JSON e transforma em um objeto
     * do tipo enviado como parametro.
     * @param json
     * @param typeParameterClass
     * @return
     * @param <T>
     */
    public static <T>  T toObject(String json, Class<T> typeParameterClass) {
        try {
            return JSON.readValue(json.getBytes(), typeParameterClass);
        } catch (IOException e) {
            throw new RuntimeException(String.format("Error processing from Json to object type %s", typeParameterClass.getSimpleName()), e.getCause());
        } finally {
            JSON.setFilterProvider(null);
        }
    }


    /***
     * Método para realizar uma conversão de dois passos a partir do valor fornecido,
     * em uma instância do tipo de valor fornecido.
     * @param object
     * @param tClass
     * @return
     * @param <T>
     * @param <S>
     */
    public static <T,S> T convertTo(S object, Class<T> tClass) {
        try {
            return JSON.convertValue(object, tClass);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(String.format("Error processing converting from object type %s to object type %s", object.getClass().getSimpleName(), tClass.getSimpleName()), e.getCause());
        }
    }
}

