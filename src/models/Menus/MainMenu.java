package models.Menus;

import utils.InputUtils;

import java.util.Scanner;

public class MainMenu {
    private final Scanner scanner;

    public MainMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    public void runMainMenu() {
        int option;
        do {
            System.out.println("\n=== MAIN MENU ===");
            System.out.println("1. Enter Store System");
            System.out.println("2. Exit");
            option = InputUtils.getIntInput(scanner, "Choose option");

            switch(option) {
                case 1:
                    StoreMenu storeMenu = new StoreMenu(scanner);
                    storeMenu.RunStoreMenu();
                    break;
                case 2:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (option != 2);
    }
}
