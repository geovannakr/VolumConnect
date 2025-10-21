package org.example.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3355/volunnConnect";
    private static final String USER = "root";
    private static final String PASS = "mysqlPW";

    private DBConnection() {
        // construtor privado para impedir instância
    }

    // Sempre retorna uma NOVA conexão
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
