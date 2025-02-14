package JAVA.CRUDJdbc.service;

import JAVA.CRUDJdbc.dominio.Producer;
import JAVA.CRUDJdbc.repository.ProducerRepository;

import java.util.List;
import java.util.Scanner;

public class ProducerService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void BuildMenu(int op) {
        switch (op) {
            case 1: FindByName();
                    break;
            case 2: delete();
                    break;
            default:
                throw new IllegalArgumentException("esse numero nao é permitido");
        }
    }
    private static void FindByName() {
        System.out.println("digite o nome que voce procura");
        String name = SCANNER.nextLine();
        List<Producer> producers = ProducerRepository.FindByName(name);
        for (int i = 0; i < producers.size(); i++) {
            Producer producer = producers.get(i);
            System.out.printf("[%d] %d - %s%n", i,producer.getId(), producer.getName());
        }
    }

    private static void delete() {
        System.out.println("digite o id que voce quer deletar");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("deseja mesmo deletar S/N");
        String choice = SCANNER.nextLine();
        if ("s".equalsIgnoreCase(choice)) {
            ProducerRepository.Delete(id);
        }
    }
}
