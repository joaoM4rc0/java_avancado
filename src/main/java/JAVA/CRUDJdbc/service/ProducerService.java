package JAVA.CRUDJdbc.service;

import JAVA.CRUDJdbc.dominio.Producer;
import JAVA.CRUDJdbc.repository.ProducerRepository;

import java.util.List;
import java.util.Scanner;

public class ProducerService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void BuildMenu(int op) {
        switch (op) {
            case 1 -> FindByName();
            case 2 -> delete();
            case 3 -> save();
            default -> throw new IllegalArgumentException("esse numero nao é permitido");
        }
    }
    private static void FindByName() {
        System.out.println("digite o nome que voce procura");
        String name = SCANNER.nextLine();
        ProducerRepository.FindByName(name)
                .forEach(p -> System.out.printf("[%d]  %s%n", p.getId(), p.getName()));
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
    private static void save() {
        System.out.println("digite o nome que vc quer salvar");
        String name = SCANNER.nextLine();
        Producer producer = Producer.builder().name(name).build();
        ProducerRepository.save(producer);
    }
}
