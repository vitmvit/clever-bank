package org.example.exeption;

public class ConnectionException extends RuntimeException {

    public ConnectionException() {
        this("Connection is lost");
    }

    public ConnectionException(String message) {
        super(message);
    }
}
