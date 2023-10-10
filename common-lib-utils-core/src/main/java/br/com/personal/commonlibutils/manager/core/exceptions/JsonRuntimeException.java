package br.com.personal.commonlibutils.manager.core.exceptions;

public class JsonRuntimeException extends RuntimeException {
    public JsonRuntimeException(String message) {
        super(message);
    }

    public JsonRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
