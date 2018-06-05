package com.example.MicServices_2.exception;

public class JdbcRuntimeException extends RuntimeException {
    public JdbcRuntimeException() {
        super();
    }

    public JdbcRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcRuntimeException(String message) {
        super(message);
    }

    public JdbcRuntimeException(Throwable cause) {
        super(cause);
    }
}
