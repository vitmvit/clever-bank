package org.example;

import org.example.connection.DbConnection;

public class Main {
    private static final DbConnection dbConnection = new DbConnection();

    public static void main(String[] args) {
        dbConnection.createConnection();
    }
}