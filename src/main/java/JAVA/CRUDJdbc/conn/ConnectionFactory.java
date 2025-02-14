package JAVA.CRUDJdbc.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String url = "jdbc:mysql://localhost:3307/devdojo_maratona";
    private static final String username = "root";
    private static final String password = "root";
    public static Connection GetConnection() throws SQLException {
            return DriverManager.getConnection(url, username, password);
    }

}

