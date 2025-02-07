package JAVA.jdbc.service;

import JAVA.jdbc.dominio.Producer;
import JAVA.jdbc.repository.ProducerRepository;

import java.util.List;

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

    public static void RequiredValidId(Integer id) {
        if (id == null || id <=0) {
            throw new IllegalArgumentException("invalid value for id");
        }
    }
    public static void FindAll() {
        List<Producer> producers = ProducerRepository.FindAll();
        for (Producer producer1 : producers) {
            System.out.printf("nome do produtor: '%s' id do produtor '%d'\n", producer1.getName(), producer1.getId());
        }
    }
}
