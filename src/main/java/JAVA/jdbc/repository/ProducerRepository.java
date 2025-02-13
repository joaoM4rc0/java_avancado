package JAVA.jdbc.repository;

import JAVA.jdbc.conexao.ConnectionFactory;
import JAVA.jdbc.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
        try (Connection conn = ConnectionFactory.GetConnection(); PreparedStatement stmt = preparedStatemenUpdate(conn, producer) ) {
            int rowsAffected = stmt.executeUpdate();
            log.info("####### atualizando : {}, linhas afetadas {} #######",producer.getId(), rowsAffected);
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
    public static List<Producer> FindAll() {
        log.info("###### FindALL produces");
        String sql = "SELECT id, name FROM devdojo_maratona.producer";
        List<Producer> producers = new ArrayList<>();
        try (Connection conn = ConnectionFactory.GetConnection(); Statement stmt = conn.createStatement() ) {
            ResultSet rs = stmt.executeQuery(sql);
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
        return producers;
    }
    public static List<Producer> FindByName(String name) {
        log.info("###### FindByname produces");
        List<Producer> producers = new ArrayList<>();
        String sql = "SELECT * FROM devdojo_maratona.producer WHERE name like'%%%s%%';"
                .formatted(name);
        try (Connection conn = ConnectionFactory.GetConnection(); Statement stmt = conn.createStatement() ) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Producer producer = Producer
                        .builder()
                        .name(rs.getString("name"))
                        .build();
                producers.add(producer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producers;
    }
    public static void ShowProducerMetaData() {
        log.info("###### MetaData");
        String sql = "SELECT * FROM devdojo_maratona.producer";
        try (Connection conn = ConnectionFactory.GetConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            rs.next();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                log.info("nome da tabela '{}'", metaData.getTableName(i));
                log.info("nome da coluna '{}'", metaData.getColumnName(i));
                log.info("nome da base de dados '{}'", metaData.getCatalogName(i));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void ShowDriverMetaData() {
        log.info("###### Driver MetaData");
        try (Connection conn = ConnectionFactory.GetConnection();) {
            DatabaseMetaData metaData = conn.getMetaData();
            if (metaData.supportsTransactions()) {
                log.info("o banco de dados suporta transacoes");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void showTypeScrollInsensitive() {
        log.info("###### FindByname produces");
        String sql = "SELECT * FROM devdojo_maratona.producer;";
        try (Connection conn = ConnectionFactory.GetConnection();
             Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE) ) {
            ResultSet rs = stmt.executeQuery(sql);
            log.info("last row? '{}'", rs.last());
            log.info(Producer.builder().name(rs.getString("name")).id(rs.getInt("id")).build());
            System.out.println("------------------------------------");
            log.info("absolut row? '{}'", rs.absolute(2));
            log.info(Producer.builder().name(rs.getString("name")).id(rs.getInt("id")).build());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static List<Producer> FindByNameAndUpdate(String name) {
        log.info("###### FindByname produces");
        List<Producer> producers = new ArrayList<>();
        String sql = "SELECT * FROM devdojo_maratona.producer WHERE name like'%%%s%%';"
                .formatted(name);
        try (Connection conn = ConnectionFactory.GetConnection(); Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE) ) {
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                rs.updateString("name", rs.getString("name").toUpperCase());
//                rs.cancelRowUpdates();
                rs.updateRow();
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
        return producers;
    }

    public static List<Producer> FindByNameAndInsertWhenNotFound(String name) {
        log.info("###### FindByname produces");
        List<Producer> producers = new ArrayList<>();
        String sql = "SELECT * FROM devdojo_maratona.producer WHERE name like'%%%s%%';"
                .formatted(name);
        try (Connection conn = ConnectionFactory.GetConnection(); Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE) ) {
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) return producers;
            insertNewProducer(name, rs);
            getProducer(rs, producers);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return producers;
    }

    public static void FindByNameAndDelete(String name) {
        log.info("###### FindByname produces");
        String sql = "SELECT * FROM devdojo_maratona.producer WHERE name like'%%%s%%';"
                .formatted(name);
        try (Connection conn = ConnectionFactory.GetConnection(); Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE) ) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                rs.deleteRow();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void insertNewProducer(String name, ResultSet rs) throws SQLException {
        rs.moveToInsertRow();
        rs.updateString("name", name);
        rs.insertRow();
    }

    public static List<Producer> FindByNamePreparedStatement(String name) {
        log.info("###### Find Statement produces");
        // usa um placeholder '?' que vai ser substituido pelo valor de 'nome'

        List<Producer> producers = new ArrayList<>();
        // get the connection
        try (Connection conn = ConnectionFactory.GetConnection();
             PreparedStatement ps = preparedStatementByName(conn, name);
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
    private static PreparedStatement preparedStatementByName(Connection conn, String name) throws SQLException {
        String sql = "SELECT id, name FROM devdojo_maratona.producer WHERE name like ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        //cria um PreparedStatement com a consulta SQL fornecida.
        ps.setString(1, String.format("%%%s%%",name));
        //  adiciona % antes e depois do valor de name, transformando-o em um padrão de busca com LIKE.
        // por exemplo se name for 'mar' e tiver nomes como: maria, marcos, marcio, todos ele serão chamados
        // e irao para o objeto producer, e será adcionado a uma lista
        return ps;
    }

    //---------------------------------------
    public static void saveTransaction(List<Producer> producers) {
        try (Connection conn = ConnectionFactory.GetConnection()) {
            conn.setAutoCommit(false);
            preparedStatementSave(conn, producers);
            conn.commit();
        }
    catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static void preparedStatementSave(Connection conn, List<Producer> producers) throws SQLException {
        String sql = "INSERT INTO `devdojo_maratona`.`producer` (`name`) VALUES( ? );";
        boolean shoudRollBack = false;
        for (Producer p : producers) {
            try(PreparedStatement ps = conn.prepareStatement(sql)) {
                log.info("salvando producer {}", p.getName());
                ps.setString(1, p.getName());
                if (p.getName().equals("goku")) throw new SQLException("nome nao permitido encontrado");
                ps.execute();
            } catch (SQLException e) {
                shoudRollBack = true;
                throw new RuntimeException(e);
            }
        }
        if (shoudRollBack) conn.rollback();
    }
    private static void getProducer(ResultSet rs, List<Producer> producers) throws SQLException {
        rs.beforeFirst();
        rs.next();
        Producer producer = Producer
                .builder()
                .name(rs.getString("name"))
                .id(rs.getInt("id"))
                .build();
        producers.add(producer);
    }
}
// comentario só para teste