package JAVA.CRUDJdbc.test;

import JAVA.CRUDJdbc.service.AnimeService;
import JAVA.CRUDJdbc.service.ProducerService;

import java.util.Scanner;

public class CrudTest02 {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int op;
        while(true) {
            Menu();
            op = Integer.parseInt(scanner.nextLine());
            if (op == 0) break;
            AnimeService.BuildMenu(op);
        }
    }
    private static void Menu() {
        System.out.println("digite o numero da sua operação");
        System.out.println("1. procurar por anime (recomendado escolhe-lo antes dos outros)");
        System.out.println("2. deletar anime");
        System.out.println("3. Salvar anime ");
        System.out.println("4. atualizar o anime ");
        System.out.println("0. Exit");
    }
}
