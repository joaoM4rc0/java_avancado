package JAVA.jdbc.test;

import JAVA.jdbc.dominio.Producer;
import JAVA.jdbc.service.ProdutoService;
import JAVA.jdbc.service.ProdutoServiceRowSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ConnectionFactoryTest02 {
    private static final Logger log = LogManager.getLogger(ConnectionFactoryTest02.class);

    public static void main(String[] args) {
        Producer producer1 = Producer.builder().name("goku").build();
        Producer producer2 = Producer.builder().name("luffy").build();
        Producer producer3 = Producer.builder().name("gon").build();
        ProdutoService.saveTransaction(List.of(producer1, producer2, producer3));
    }
}
