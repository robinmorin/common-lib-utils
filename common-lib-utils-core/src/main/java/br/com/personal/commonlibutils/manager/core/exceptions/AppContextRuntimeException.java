package br.com.personal.commonlibutils.manager.core.exceptions;

public class AppContextRuntimeException extends RuntimeException {
    public AppContextRuntimeException(String message) {
        super(message);
    }

    public AppContextRuntimeException(Throwable cause) {
        super(cause);
    }

    public AppContextRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
