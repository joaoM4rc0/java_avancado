package JAVA.CRUDJdbc.repository;

import JAVA.CRUDJdbc.dominio.Producer;
import JAVA.jdbc.conexao.ConnectionFactory;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ProducerRepository {
    public static List<Producer> FindByName(String name) {
        log.info("###### Producer by name {}", name);
        // usa um placeholder '?' que vai ser substituido pelo valor de 'nome'

        List<Producer> producers = new ArrayList<>();
        // get the connection
        try (Connection conn = ConnectionFactory.GetConnection();
             PreparedStatement ps = findByName(conn, name);
             // Ele define o valor do placeholder (?) na consulta SQL.
             ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                Producer producer = Producer
                        .builder()
                        .name(rs.getString("name"))
                        .id(rs.getInt("id"))
                        .build();
                producers.add(producer);
                // a cada iteração do while ele define um novo producer com os valores fornecidos,
                // e adiciona a uma lista
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producers;
    }
    private static PreparedStatement findByName(Connection conn, String name) throws SQLException {
        String sql = "SELECT id, name FROM devdojo_maratona.producer WHERE name like ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        //cria um PreparedStatement com a consulta SQL fornecida.
        ps.setString(1, String.format("%%%s%%",name));
        //  adiciona % antes e depois do valor de name, transformando-o em um padrão de busca com LIKE.
        // por exemplo se name for 'mar' e tiver nomes como: maria, marcos, marcio, todos ele serão chamados
        // e irao para o objeto producer, e será adcionado a uma lista
        return ps;
    }

}