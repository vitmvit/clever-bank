package org.example.exeption;

public class RequestException extends RuntimeException {
    public RequestException(String message) {
        super(message);
    }
}
