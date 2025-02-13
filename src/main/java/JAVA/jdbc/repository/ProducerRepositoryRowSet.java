package JAVA.jdbc.repository;

import JAVA.jdbc.conexao.ConnectionFactory;
import JAVA.jdbc.dominio.Producer;
import JAVA.jdbc.listener.CustomRowSetListener;
import lombok.extern.log4j.Log4j2;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProducerRepositoryRowSet {
    public static List<Producer> FindByNameRowSet(String name) {
        String sql = "Select * FROM devdojo_maratona.producer where name like ?;";
        List<Producer> producers = new ArrayList<>();
        try(JdbcRowSet jrs = ConnectionFactory.GetJdbcRowSet()) {
            jrs.addRowSetListener(new CustomRowSetListener());
            jrs.setCommand(sql);
            //O JdbcRowSet é configurado com a consulta SQL
            jrs.setString(1, String.format("%%%s%%", name));
            // o parâmetro name é passado usando setString.
            jrs.execute();
            // é chamado para executar a consulta.
            while(jrs.next()) {
                Producer producer = Producer.builder()
                        .id(jrs.getInt("id"))
                        .name(jrs.getString("name"))
                        .build();
                producers.add(producer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producers;
    }
//    public static void UpdateRowSet(Producer producer) {
//        String sql = "UPDATE `devdojo_maratona`.`producer`SET name= ? WHERE id=(?);";
//        try(JdbcRowSet jrs = ConnectionFactory.GetJdbcRowSet()) {
//            jrs.setCommand(sql);
//            //O JdbcRowSet é configurado com a consulta SQL
//            jrs.setString(1, producer.getName());
//            // o parâmetro name é passado usando setString.
//            jrs.setInt(2, producer.getId());
//            // o parâmetro ID é passado usando setInt.
//            jrs.execute();
//            // é chamado para executar a consulta.
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }
    public static void UpdateRowSet(Producer producer) {
        String sql = "SELECT * FROM devdojo_maratona.producer WHERE (`id` = ?); ";
        try(JdbcRowSet jrs = ConnectionFactory.GetJdbcRowSet()) {
            jrs.addRowSetListener(new CustomRowSetListener());
            // adiciono ao rowset um listener, que faz uns comandos, como mover o cursor,
            jrs.setCommand(sql);
            jrs.setInt(1, producer.getId());
            jrs.execute();
            if(!jrs.next()) return;
            jrs.updateString("name", producer.getName());
            // modifica o nome do id que eu passar
            jrs.updateRow();
            // atualiza a linha
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void UpdateCachedRowSet(Producer producer) {
        String sql = "SELECT * FROM producer WHERE (`id` = ?); ";
        try(CachedRowSet crs = ConnectionFactory.GetCachedRowSet();
            Connection connection = ConnectionFactory.GetConnection()) {
            connection.setAutoCommit(false);
            // O modo de autocommit da conexão é desativado
        // para garantir que as alterações só sejam confirmadas após a execução bem-sucedida de todas as operações.
            crs.setCommand(sql);
            // adiciono ao rowset um listener, que faz uns comandos, como mover o cursor
            crs.setInt(1, producer.getId());
            crs.execute(connection);
            //é chamado para executar a consulta usando a conexão fornecida.
            if(!crs.next()) return;
            crs.updateString("name", producer.getName());
            // modifica o nome do id que eu passar
            crs.updateRow();
            // atualiza a linha
            crs.acceptChanges();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}