package JAVA.jdbc.test;

import JAVA.jdbc.dominio.Producer;
import JAVA.jdbc.repository.ProducerRepository;
import JAVA.jdbc.service.ProdutoService;

import java.util.List;

public class ConnectionFactoryTest01 {
    public static void main(String[] args) {
        ProdutoService.FindByName("cla");
        ProdutoService.ShowMetaData();
    }
}
