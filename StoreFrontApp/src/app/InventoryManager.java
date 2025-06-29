package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class InventoryManager {
    private ArrayList<SalableProduct> inventory = new ArrayList<>();

    // Add a product to the inventory
    public void addProduct(SalableProduct product) {
        inventory.add(product);
    }

    // Remove a product from the inventory
    public void removeProduct(SalableProduct product) {
        inventory.remove(product);
    }

    // Get the current inventory
    public ArrayList<SalableProduct> getInventory() {
        return inventory;
    }

    // Sort inventory in ascending order
    public void sortInventoryAscending() {
        Collections.sort(inventory);
    }

    // Sort inventory in descending order
    public void sortInventoryDescending() {
        inventory.sort(Collections.reverseOrder());
    }

    // Display the inventory
    public void displayInventory() {
        System.out.println("\n🛒✨ Available Products ✨🛒");
        for (SalableProduct product : inventory) {
            System.out.println("🔹 " + product);
        }
    }

    // Sort Menu
    public void displaySortMenu(Scanner scanner) {
        boolean sorting = true;

        while (sorting) {
            System.out.println("\n📦 -- Inventory Menu --");
            System.out.println("1. View All (Ascending)");
            System.out.println("2. View All (Descending)");
            System.out.println("3. Filter by Type");
            System.out.println("4. Back to Main Menu");
            System.out.print("🎮 Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    sortInventoryAscending();
                    displayInventory();
                    break;
                case "2":
                    sortInventoryDescending();
                    displayInventory();
                    break;
                case "3":
                    displayFilterMenu(scanner);
                    break;
                case "4":
                    sorting = false;
                    break;
                default:
                    System.out.println("⚠️ Invalid option. Try again.");
            }
        }
    }

    // Filter by product type
    private void displayFilterMenu(Scanner scanner) {
        boolean filtering = true;

        while (filtering) {
            System.out.println("\n🧪 -- Filter by Product Type --");
            System.out.println("1. Weapons 🗡️");
            System.out.println("2. Armor 🛡️");
            System.out.println("3. Health 🧪");
            System.out.println("4. Back");
            System.out.print("🔎 Choose a filter: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    displayFilteredInventory(Weapon.class);
                    break;
                case "2":
                    displayFilteredInventory(Armor.class);
                    break;
                case "3":
                    displayFilteredInventory(Health.class);
                    break;
                case "4":
                    filtering = false;
                    break;
                default:
                    System.out.println("⚠️ Invalid filter. Try again.");
            }
        }
    }

    // Display only specific type of products
    private void displayFilteredInventory(Class<?> type) {
        System.out.println("\n📂 Filtered Products:");
        for (SalableProduct product : inventory) {
            if (type.isInstance(product)) {
                System.out.println("🔸 " + product);
            }
        }
    }
}
