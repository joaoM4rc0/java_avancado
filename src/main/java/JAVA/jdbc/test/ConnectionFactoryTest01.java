package JAVA.jdbc.test;

import JAVA.jdbc.dominio.Producer;
import JAVA.jdbc.repository.ProducerRepository;
import JAVA.jdbc.service.ProdutoService;

public class ConnectionFactoryTest01 {
    public static void main(String[] args) {
        Producer producer = Producer.builder().name("matheus").build();
//        ProdutoService.save(producer);
        ProdutoService.Delete(5);
    }
}
