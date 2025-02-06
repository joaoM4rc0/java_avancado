package JAVA.jdbc.test;

import JAVA.jdbc.conexao.ConnectionFactory;
import dominio.Producer;
import repository.ProducerRepository;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionFactoryTest01 {
    public static void main(String[] args) {
        Producer producer = Producer.builder().name("clarice").build();
        ProducerRepository.save(producer);
    }
}
