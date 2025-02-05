package JAVA.jdbc.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final String url = "jdbc:mysql://localhost:3307/devdojo_maratona";
    private static final String username = "root";
    private static final String password = "root";
    public static Connection GetConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println(connection);
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException("erro ao conectar no banco de dados, erro" + e);
        }
        return null;
    }
}

