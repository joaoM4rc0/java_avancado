package JAVA.CRUDJdbc.repository;

import JAVA.CRUDJdbc.conn.ConnectionFactory;
import JAVA.CRUDJdbc.dominio.Anime;
import JAVA.CRUDJdbc.dominio.Producer;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class AnimeRepository {
    public static List<Anime> FindByName(String name) {
        log.info("###### Anime by name {}", name);
        // usa um placeholder '?' que vai ser substituido pelo valor de 'nome'

        List<Anime> animes = new ArrayList<>();
        // get the connection
        try (Connection conn = ConnectionFactory.GetConnection();
             PreparedStatement ps = findByName(conn, name);
             // Ele define o valor do placeholder (?) na consulta SQL.
             ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                Producer producer = Producer.builder()
                        .name(rs.getString("producer_name"))
                        .id(rs.getInt("producer_id"))
                        .build();
                Anime anime = Anime
                        .builder()
                        .name(rs.getString("name"))
                        .id(rs.getInt("id"))
                        .episodes(rs.getInt("episodes"))
                        .producer(producer)
                        .build();
                animes.add(anime);
                // a cada iteração do while ele define um novo anime com os valores fornecidos,
                // e adiciona a uma lista
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return animes;
    }
    public static boolean FindById(int id) {
        List<Anime> animes = new ArrayList<>();
        try (Connection conn = ConnectionFactory.GetConnection();
             PreparedStatement ps = findById(conn, id);
             // Ele define o valor do placeholder (?) na consulta SQL.
             ResultSet rs = ps.executeQuery()) {
            while(rs.next()) {
                Anime anime = Anime
                        .builder()
                        .name(rs.getString("name"))
                        .id(rs.getInt("id"))
                        .build();
                animes.add(anime);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // eu itero sobre a lista, e depois verifico se o id do anime é igual ao id que eu passar
        // essa verificação impede que o usuario passe um id que nao tem no banco de dados
        for (Anime anime : animes) {
            if (anime.getId().equals(id)) {
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
    public static void save(Anime anime) {
        if (anime == null || anime.getName() == null || anime.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do anime não pode ser vazio");
        }
        try (Connection conn = JAVA.jdbc.conexao.ConnectionFactory.GetConnection();
             PreparedStatement ps = preparedStatemenSave(conn, anime) ) {
            ps.execute();
            log.info("####### salvando : {} #######",anime.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static PreparedStatement preparedStatemenSave(Connection conn, Anime anime) throws SQLException {

        String sql = "INSERT INTO devdojo_maratona.anime ( name, producer_id, episodes) VALUES( ?, ?, ?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        //cria um PreparedStatement com a consulta SQL fornecida.
        ps.setString(1, anime.getName());
        ps.setInt(2, anime.getProducer().getId());
        ps.setInt(3, anime.getEpisodes());
        return ps;
    }
    public static void Update(Anime anime) {
        if(anime == null || anime.getId() <= 0 || anime.getName() == null || anime.getName().trim().isEmpty() ) {
            throw new IllegalArgumentException("os dados passados são errados");
        }
        try (Connection conn = ConnectionFactory.GetConnection(); PreparedStatement ps = preparedStatemenUpdate(conn, anime) ) {
            ps.execute();
            log.info("####### atualizando : {} #######",anime.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static PreparedStatement preparedStatemenUpdate(Connection conn, Anime anime) throws SQLException {
        String sql = "UPDATE `devdojo_maratona`.`anime`SET name= ?, episodes = ? WHERE id=(?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        //cria um PreparedStatement com a consulta SQL fornecida.
        ps.setString(1, anime.getName());
        ps.setInt(2, anime.getEpisodes());
        ps.setInt(3, anime.getId());
        return ps;
    }
    private static PreparedStatement findByName(Connection conn, String name) throws SQLException {
        log.info("salvando anime");
        String sql = """
                SELECT a.id, a.name, a.episodes, a.producer_id,p.name as 'producer_name' FROM devdojo_maratona.anime a inner JOIN
                devdojo_maratona.producer p on a.producer_id = p.id
                where a.name like ?;
                """;
        PreparedStatement ps = conn.prepareStatement(sql);
        //cria um PreparedStatement com a consulta SQL fornecida.
        ps.setString(1, String.format("%%%s%%",name));
        //  adiciona % antes e depois do valor de name, transformando-o em um padrão de busca com LIKE.
        // por exemplo se name for 'mar' e tiver nomes como: maria, marcos, marcio, todos ele serão chamados
        // e irao para o objeto anime, e será adcionaasdo a uma lista
        return ps;
    }
    private static PreparedStatement findById(Connection conn, int id) throws SQLException {
        String sql = "SELECT * FROM devdojo_maratona.anime WHERE id like ?;";
        PreparedStatement ps = conn.prepareStatement(sql);
        //cria um PreparedStatement com a consulta SQL fornecida.
        ps.setString(1, String.format("%%%s%%",id));
        return ps;
    }
    private static PreparedStatement findByNameDelete(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM `devdojo_maratona`.`anime` WHERE id=(?);";
        PreparedStatement ps = conn.prepareStatement(sql);
        //cria um PreparedStatement com a consulta SQL fornecida.
        ps.setInt(1, id);
        return ps;
    }
}