package org.example.exeption;

import static org.example.model.constant.Constants.CONNECTION_EXCEPTION_MESSAGE;

public class ConnectionException extends RuntimeException {

    public ConnectionException() {
        this(CONNECTION_EXCEPTION_MESSAGE);
    }

    public ConnectionException(String message) {
        super(message);
    }
}
