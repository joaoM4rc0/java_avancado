package JAVA.jdbc.repository;

import JAVA.jdbc.conexao.ConnectionFactory;
import JAVA.jdbc.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
@Log4j2
public class ProducerRepository {
    public static void save(Producer producer) {
        String sql = "INSERT INTO `devdojo_maratona`.`producer` (`name`) VALUES('%s');".formatted(producer.getName());
        try (Connection conn = ConnectionFactory.GetConnection(); Statement stmt = conn.createStatement() ) {
            int rowsAffected = stmt.executeUpdate(sql);
            log.info("####### Inserindo name: {}, linhas afetadas {} #######",producer.getName(),  rowsAffected);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void Delete(int id) {
        String sql = "DELETE FROM `devdojo_maratona`.`producer` WHERE id=('%d');".formatted(id);
        try (Connection conn = ConnectionFactory.GetConnection(); Statement stmt = conn.createStatement() ) {
            int rowsAffected = stmt.executeUpdate(sql);
            log.info("####### deletando id: {}, linhas afetadas {} #######",id, rowsAffected);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void Update(Producer producer) {
        String sql = "UPDATE `devdojo_maratona`.`producer`SET name='%s' WHERE id=('%d');".formatted(producer.getName(), producer.getId());
        try (Connection conn = ConnectionFactory.GetConnection(); Statement stmt = conn.createStatement() ) {
            int rowsAffected = stmt.executeUpdate(sql);
            log.info("####### atualizando : {}, linhas afetadas {} #######",producer.getId(), rowsAffected);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
