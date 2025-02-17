package JAVA.CRUDJdbc.service;

import JAVA.CRUDJdbc.dominio.Producer;
import JAVA.CRUDJdbc.repository.ProducerRepository;
import lombok.extern.log4j.Log4j2;

import java.util.List;
import java.util.Scanner;

public class ProducerService {
    private static final Scanner SCANNER = new Scanner(System.in);

    public static void BuildMenu(int op) {
        switch (op) {
            case 1 -> FindByName();
            case 2 -> delete();
            case 3 -> save();
            case 4 -> update();
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
    private static void update() {
        System.out.println("digite o nome e o id, o nome vai substituir o nome do id que vc escolher");
        System.out.println("nome: ");
        String name = SCANNER.nextLine();
        System.out.println("id:");
        int id = Integer.parseInt(SCANNER.nextLine());
        boolean b = ProducerRepository.FindById(id);
        if (!b) {
            System.out.println("o id que vc passou nao tem no banco de dados");
            return;
        }
        Producer producer = Producer.builder().name(name).id(id).build();
        ProducerRepository.Update(producer);
    }
}
