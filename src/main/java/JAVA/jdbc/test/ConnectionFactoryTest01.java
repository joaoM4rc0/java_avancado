package JAVA.jdbc.test;

import JAVA.jdbc.dominio.Producer;
import JAVA.jdbc.repository.ProducerRepository;
import JAVA.jdbc.repository.ProducerRepositoryRowSet;
import JAVA.jdbc.service.ProdutoService;
import JAVA.jdbc.service.ProdutoServiceRowSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ConnectionFactoryTest01 {
    private static final Logger log = LogManager.getLogger(ConnectionFactoryTest01.class);

    public static void main(String[] args) {
        Producer producer = Producer.builder().name("lucas").id(14).build();
        ProdutoServiceRowSet.UpdateRowSet(producer);
        log.info("--------");
        List<Producer> byNameRowSet = ProdutoServiceRowSet.findByNameRowSet("");
        log.info(byNameRowSet);
    }
}
