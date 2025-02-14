package JAVA.CRUDJdbc.service;

import JAVA.CRUDJdbc.dominio.Producer;
import JAVA.CRUDJdbc.repository.ProducerRepository;

import java.util.List;
import java.util.Scanner;

public class ProducerService {
    private static Scanner scanner = new Scanner(System.in);

    public static void BuildMenu(int op) {
        switch (op) {
            case 1: FindByName();
        }
    }
    private static void FindByName() {
        System.out.println("digite o nome que voce procura");
        String name = scanner.nextLine();
        List<Producer> producers = ProducerRepository.FindByName(name);
        for (int i = 0; i < producers.size(); i++) {
            System.out.printf("[%d] - %s%n", i, producers.get(i).getName());
        }
    }

}
