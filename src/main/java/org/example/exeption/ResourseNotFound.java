package org.example.exeption;

public class ResourseNotFound extends RuntimeException {
    public ResourseNotFound(String message, Throwable cause) {
        super(message, cause);
    }
}
