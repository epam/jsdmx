package com.epam.jsdmx.json20.structure.reader;

public class JsonRuntimeException extends RuntimeException {
    public JsonRuntimeException(String message) {
        super(message);
    }

    public JsonRuntimeException(Throwable cause) {
        super(cause);
    }
}
