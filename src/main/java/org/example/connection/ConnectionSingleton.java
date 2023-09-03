package org.example.connection;

import java.sql.Connection;
import java.util.Optional;

public class ConnectionSingleton {

    private static volatile ConnectionSingleton instance;
    private final Optional<Connection> connectionOptional;

    private ConnectionSingleton(Optional<Connection> connectionOptional) {
        this.connectionOptional = connectionOptional;
    }

    public static ConnectionSingleton getInstance() {
        return setInstance(Optional.empty());
    }

    public static ConnectionSingleton setInstance(Optional<Connection> value) {
        synchronized (ConnectionSingleton.class) {
            if (instance == null) {
                instance = new ConnectionSingleton(Optional.empty());
            }
            if (value.isPresent()) {
                instance = new ConnectionSingleton(value);
            }
        }
        return instance;
    }

    public Optional<Connection> getConnection() {
        return connectionOptional;
    }
}
