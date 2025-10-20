package org.example.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

    public class DBConnection {
        private static DBConnection instance;
        private Connection connection;
        private final String URL = "jdbc:mysql://localhost:3355/volunnConnect";
        private final String USER = "root";
        private final String PASS = "mysqlPW";

        private DBConnection() throws SQLException {
            connection = DriverManager.getConnection(URL, USER, PASS);
        }

        public Connection getConnection() { return connection; }

        public static DBConnection getInstance() throws SQLException {
            if(instance == null) instance = new DBConnection();
            return instance;
        }
    }
