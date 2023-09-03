package org.example.connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnector {

    public Connection connection(String driver, String url, String username, String password) {
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();
            ;
            return DriverManager.getConnection(url, username, password);
        } catch (Exception ex) {
            System.out.println("Невозможно установить соединение");
        }
        return null;
    }
}
