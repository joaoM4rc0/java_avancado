package JAVA.jdbc.service;

import JAVA.jdbc.dominio.Producer;
import JAVA.jdbc.repository.ProducerRepository;

public class ProdutoService {
    public static void save(Producer producer) {
        ProducerRepository.save(producer);
    }
    public static void Delete(int id) {
        RequiredValidId(id);
        ProducerRepository.Delete(id);
    }
    public static void Update(Producer producer) {
        RequiredValidId(producer.getId());
        ProducerRepository.Update(producer);
    }

    private static void RequiredValidId(Integer id) {
        if (id == null || id <=0) {
            throw new IllegalArgumentException("invalid value for id");
        }
    }
}
