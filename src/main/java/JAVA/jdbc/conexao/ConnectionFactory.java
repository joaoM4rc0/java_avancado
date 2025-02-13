package JAVA.jdbc.conexao;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
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
    public static JdbcRowSet GetJdbcRowSet() throws SQLException {
        JdbcRowSet jdbcRowSet = RowSetProvider.newFactory().createJdbcRowSet();
        jdbcRowSet.setUrl(url);
        jdbcRowSet.setUsername(username);
        jdbcRowSet.setPassword(password);
        return jdbcRowSet;
    }
    public static CachedRowSet GetCachedRowSet() throws SQLException {
        CachedRowSet cachedRowSet= RowSetProvider.newFactory().createCachedRowSet();
        return cachedRowSet;
    }
}

