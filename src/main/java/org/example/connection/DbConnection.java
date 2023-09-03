package org.example.connection;

import org.example.config.ConfigReader;

import java.sql.Connection;
import java.util.Map;
import java.util.Optional;

public class DbConnection {
    public void createConnection() {
        if (ConnectionSingleton.getInstance().getConnection().isEmpty()) {
            ConfigReader configReader = new ConfigReader();
            Map<String, String> configMap = configReader.getConfigMap();
            Connection connection = new DatabaseConnector().connection(
                    configMap.get("driver"),
                    configMap.get("url"),
                    configMap.get("username"),
                    configMap.get("password")
            );
            ConnectionSingleton.setInstance(connection == null ? Optional.empty() : Optional.of(connection));
        }
    }

    public Optional<Connection> getConnection() {
        if (ConnectionSingleton.getInstance().getConnection().isEmpty()) {
            createConnection();
        }
        return ConnectionSingleton.getInstance().getConnection();
    }
}
