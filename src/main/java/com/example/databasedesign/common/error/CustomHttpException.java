package com.example.databasedesign.common.error;

public class CustomHttpException extends RuntimeException {
    private HttpExceptionMessage httpExceptionMessage;

    public CustomHttpException(HttpExceptionMessage httpExceptionMessage, Throwable cause) {
        super(httpExceptionMessage.getMessage(), cause);
    }
    public CustomHttpException(HttpExceptionMessage httpExceptionMessage) {
        super(httpExceptionMessage.getMessage());
    }
}
