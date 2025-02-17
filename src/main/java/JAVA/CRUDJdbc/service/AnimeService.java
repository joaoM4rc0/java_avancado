package JAVA.CRUDJdbc.service;

import JAVA.CRUDJdbc.dominio.Anime;
import JAVA.CRUDJdbc.dominio.Producer;
import JAVA.CRUDJdbc.repository.AnimeRepository;
import JAVA.CRUDJdbc.repository.ProducerRepository;
import JAVA.CRUDJdbc.test.CrudTest01;

import java.util.Scanner;

public class AnimeService {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static void BuildMenu() {
        int op;
        while (true) {
            AnimeMenu();
            op = Integer.parseInt(SCANNER.nextLine());
            if (op == 0 ) break;
            switch (op) {
                case 1 -> FindByName();
                case 2 -> delete();
                case 3 -> save();
                case 4 -> update();
                default -> throw new IllegalArgumentException("esse numero nao é permitido");
            }
        }
    }
    private static void FindByName() {
        System.out.println("digite o nome que voce procura");
        String name = SCANNER.nextLine();
        AnimeRepository.FindByName(name)
                .forEach(p -> System.out.printf("[%d]  %s%n", p.getId(), p.getName()));
    }

    private static void delete() {
        System.out.println("digite o id que voce quer deletar");
        int id = Integer.parseInt(SCANNER.nextLine());
        System.out.println("deseja mesmo deletar S/N");
        String choice = SCANNER.nextLine();
        if ("s".equalsIgnoreCase(choice)) {
            AnimeRepository.Delete(id);
        }
    }
    private static void save() {
        System.out.println("id's dos produtores");
        System.out.println("digite o nome que vc quer salvar");
        String name = SCANNER.nextLine();
        System.out.println("digite o n de eps do anime");
        int eps = Integer.parseInt(SCANNER.nextLine());
        System.out.println("digite o id do produtor");
        int producer_id = Integer.parseInt(SCANNER.nextLine());
        boolean b = ProducerRepository.FindById(producer_id);
        if (!b) {
            System.out.println("o id desse produtor nao existe");
            return;
        }
        Anime anime = Anime.builder()
                .name(name)
                .episodes(eps)
                .producer(Producer.builder().id(producer_id).build())
                .build();
        AnimeRepository.save(anime);
    }
    private static void update() {
        System.out.println("digite o nome e o id, o nome vai substituir o nome do id que vc escolher");
        System.out.println("nome: ");
        String name = SCANNER.nextLine();
        System.out.println("id:");
        int id = Integer.parseInt(SCANNER.nextLine());
        boolean b = AnimeRepository.FindById(id);
        if (!b) {
            System.out.println("o id que vc passou nao tem no banco de dados");
            return;
        }
        System.out.println("atualize o numero de eps");
        int eps = Integer.parseInt(SCANNER.nextLine());

        Anime anime = Anime.builder().name(name).id(id).episodes(eps).build();
        AnimeRepository.Update(anime);
    }
    private static void AnimeMenu() {
        System.out.println("digite o numero da sua operação");
        System.out.println("1. procurar por anime (recomendado escolhe-lo antes dos outros)");
        System.out.println("2. deletar anime");
        System.out.println("3. Salvar anime ");
        System.out.println("4. atualizar o anime ");
        System.out.println("0. Exit");
    }
}
