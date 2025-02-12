package JAVA.jdbc.test;

import JAVA.jdbc.dominio.Producer;
import JAVA.jdbc.repository.ProducerRepository;
import JAVA.jdbc.repository.ProducerRepositoryRowSet;
import JAVA.jdbc.service.ProdutoService;

import java.util.List;

public class ConnectionFactoryTest01 {
    public static void main(String[] args) {
        List<Producer> producers = ProducerRepositoryRowSet.FindByNameRowSet("joao");
        System.out.println(producers);
    }
}
