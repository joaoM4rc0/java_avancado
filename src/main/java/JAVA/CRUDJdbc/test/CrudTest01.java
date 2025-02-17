package JAVA.CRUDJdbc.test;

import JAVA.CRUDJdbc.service.AnimeService;
import JAVA.CRUDJdbc.service.ProducerService;

import java.util.Scanner;

public class CrudTest01 {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int op;
        while(true) {
            menu();
            op = Integer.parseInt(scanner.nextLine());
            if (op == 0) break;
            switch (op) {
                case 1 -> ProducerService.BuildMenu();
                case 2 -> AnimeService.BuildMenu();
            }
        }
    }
    private static void menu() {
        System.out.println("digite o numero da sua operação");
        System.out.println("1. producer");
        System.out.println("2. anime");
        System.out.println("0. exit");
    }
}
