package JAVA.jdbc.test;

import JAVA.jdbc.dominio.Producer;
import JAVA.jdbc.repository.ProducerRepository;
import JAVA.jdbc.service.ProdutoService;

import java.util.List;

public class ConnectionFactoryTest01 {
    public static void main(String[] args) {
//        ProdutoService.ShowTypeScrollInsensitive();
//        ProdutoService.FindByNameAndUpdate("marcos");
//        ProdutoService.FindByNameAndInsertWhenNotFound("davi");
//        ProdutoService.FindByNameAndInsertDelete("m");
        List<Producer> producers = ProdutoService.FindByNamePreparedStatement("joao");
        System.out.println(producers);
    }
}
