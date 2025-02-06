package JAVA.jdbc.service;

import JAVA.jdbc.dominio.Producer;
import JAVA.jdbc.repository.ProducerRepository;

public class ProdutoService {
    public static void save(Producer producer) {
        ProducerRepository.save(producer);
    }
    public static void Delete(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("nao é permitido id menor ou igual a '0'");
        }
            ProducerRepository.Delete(id);
    }
}
