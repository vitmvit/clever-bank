package org.example.exeption;

public class FileWriteException extends RuntimeException {
    public FileWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
