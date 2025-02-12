package JAVA.jdbc.repository;

import JAVA.jdbc.conexao.ConnectionFactory;
import JAVA.jdbc.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import javax.sql.rowset.JdbcRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProducerRepositoryRowSet {
    public static List<Producer> FindByNameRowSet(String name) {
        String sql = "Select * FROM devdojo_maratona.producer where name like ?;";
        List<Producer> producers = new ArrayList<>();
        try(JdbcRowSet jrs = ConnectionFactory.GetJdbcRowSet()) {
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
}