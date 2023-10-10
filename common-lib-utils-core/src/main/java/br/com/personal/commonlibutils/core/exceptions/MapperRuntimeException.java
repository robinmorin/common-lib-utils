package br.com.personal.commonlibutils.core.exceptions;

public class MapperRuntimeException extends RuntimeException {
    public MapperRuntimeException(String message) {
        super(message);
    }

    public MapperRuntimeException(Throwable cause) {
        super(cause);
    }

    public MapperRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
