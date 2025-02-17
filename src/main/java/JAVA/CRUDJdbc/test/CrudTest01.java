package JAVA.CRUDJdbc.test;

import JAVA.CRUDJdbc.service.ProducerService;

import java.util.Scanner;

public class CrudTest01 {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int op;
        while(true) {
            Menu();
            op = Integer.parseInt(scanner.nextLine());
            if (op == 0) break;
            ProducerService.BuildMenu(op);
        }
    }
    private static void Menu() {
        System.out.println("digite o numero da sua operação");
        System.out.println("1. procurar por produto (recomendado escolhe-lo antes dos outros)");
        System.out.println("2. deletar produto");
        System.out.println("3. Salvar produto ");
        System.out.println("4. atualizar o produto ");
        System.out.println("0. Exit");
    }
}
