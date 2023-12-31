package br.com.personal.commonlibutils.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MessageError {

    JSON_TRANSFORM_COMPLEX("Object %s is too complex. Error: %s. Trying give response toString -> %s");

    private final String template;

}
