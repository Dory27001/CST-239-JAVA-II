package admin;

import java.util.Scanner;

public class AdminApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n==== Admin Menu ====");
            System.out.println("1. Send Update Inventory");
            System.out.println("2. Request Inventory");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("Enter path to JSON file: ");
                    String path = scanner.nextLine();
                    AdminClient.sendUpdateCommand(path);
                    break;
                case "2":
                    AdminClient.sendRetrieveCommand();
                    break;
                case "3":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid.");
            }
        }
        scanner.close();
    }
}