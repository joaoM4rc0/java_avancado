package JAVA.CRUDJdbc.repository;

import JAVA.CRUDJdbc.conn.ConnectionFactory;
import JAVA.CRUDJdbc.dominio.Producer;
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
    public static boolean FindById(int id) {
        List<Producer> producers = new ArrayList<>();
        try (Connection conn = ConnectionFactory.GetConnection();
             PreparedStatement ps = findById(conn, id);
             // Ele define o valor do placeholder (?) na consulta SQL.
             ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                Producer producer = Producer
                        .builder()
                        .name(rs.getString("name"))
                        .id(rs.getInt("id"))
                        .build();
                producers.add(producer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // eu itero sobre a lista, e depois verifico se o id do producer é igual ao id que eu passar
        // isso impede que o usuario passe um id que nao tem no banco de daods
        for (Producer producer : producers) {
            if (producer.getId().equals(id)) {
                return true;
            }
        }
        return false;
    }
    public static void Delete(int id) {
        try (Connection conn = ConnectionFactory.GetConnection();
             PreparedStatement ps = findByNameDelete(conn, id)) {
           ps.execute();
            log.info("####### deletando id: {}, #######",id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void save(Producer producer) {
        if (producer == null || producer.getName() == null || producer.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do producer não pode ser vazio");
        }
        try (Connection conn = JAVA.jdbc.conexao.ConnectionFactory.GetConnection();
             PreparedStatement ps = preparedStatemenSave(conn, producer) ) {
            ps.execute();
            log.info("####### salvando : {} #######",producer.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static PreparedStatement preparedStatemenSave(Connection conn, Producer producer) throws SQLException {
        String sql = "INSERT INTO `devdojo_maratona`.`producer` (`name`) VALUES(?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        //cria um PreparedStatement com a consulta SQL fornecida.
        ps.setString(1, producer.getName());
        return ps;
    }
    public static void Update(Producer producer) {
        if(producer == null || producer.getId() <= 0 || producer.getName() == null || producer.getName().trim().isEmpty() ) {
            throw new IllegalArgumentException("os dados passados são errados");
        }
        try (Connection conn = ConnectionFactory.GetConnection(); PreparedStatement ps = preparedStatemenUpdate(conn, producer) ) {
            ps.execute();
            log.info("####### atualizando : {} #######",producer.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static PreparedStatement preparedStatemenUpdate(Connection conn, Producer producer) throws SQLException {
        String sql = "UPDATE `devdojo_maratona`.`producer`SET name= ? WHERE id=(?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        //cria um PreparedStatement com a consulta SQL fornecida.
        ps.setString(1, producer.getName());
        ps.setInt(2, producer.getId());
        return ps;
    }
    private static PreparedStatement findByName(Connection conn, String name) throws SQLException {
        log.info("salvando producer");
        String sql = "SELECT * FROM devdojo_maratona.producer WHERE name like ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        //cria um PreparedStatement com a consulta SQL fornecida.
        ps.setString(1, String.format("%%%s%%",name));
        //  adiciona % antes e depois do valor de name, transformando-o em um padrão de busca com LIKE.
        // por exemplo se name for 'mar' e tiver nomes como: maria, marcos, marcio, todos ele serão chamados
        // e irao para o objeto producer, e será adcionaasdo a uma lista
        return ps;
    }
    private static PreparedStatement findById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM devdojo_maratona.producer WHERE id like ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        //cria um PreparedStatement com a consulta SQL fornecida.
        ps.setString(1, String.format("%%%s%%",id));
        return ps;
    }
    private static PreparedStatement findByNameDelete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM `devdojo_maratona`.`producer` WHERE id=(?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        //cria um PreparedStatement com a consulta SQL fornecida.
        ps.setInt(1, id);
        return ps;
    }
}