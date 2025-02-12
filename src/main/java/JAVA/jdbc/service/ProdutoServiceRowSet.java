package JAVA.jdbc.service;

import JAVA.jdbc.dominio.Producer;
import JAVA.jdbc.repository.ProducerRepository;
import JAVA.jdbc.repository.ProducerRepositoryRowSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProdutoServiceRowSet {
    public static List<Producer> findByNameRowSet(String name) {
        List<Producer> producers = ProducerRepositoryRowSet.FindByNameRowSet(name);
        return producers;
    }
    public static void UpdateRowSet(Producer producer) {
        ProducerRepositoryRowSet.UpdateRowSet(producer);
    }
}
