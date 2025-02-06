package repository;

import JAVA.jdbc.conexao.ConnectionFactory;
import dominio.Producer;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ProducerRepository {
    public static void save(Producer producer) {
        String sql = "INSERT INTO `devdojo_maratona`.`producer` (`name`) VALUES('%s');".formatted(producer.getName());
        try (Connection conn = ConnectionFactory.GetConnection(); Statement stmt = conn.createStatement() ) {
            int rowsAffected = stmt.executeUpdate(sql);
            System.out.println(rowsAffected);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
