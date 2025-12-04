package com.example.granja.patos.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

public class GranjaHttpException extends HttpClientErrorException {
    private final String message;


    public GranjaHttpException(HttpStatus statusCode, String  message) {
        super(statusCode, message);
        this.message = message;
    }

    @Override
    public String getLocalizedMessage() {
        return message;
    }
}
