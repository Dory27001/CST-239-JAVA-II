package app;

import java.util.Scanner;

public class StoreFront {
    private InventoryManager inventoryManager;
    private ShoppingCart cart = new ShoppingCart();
    private boolean isAdmin = false;  // To track if the user is admin

    public StoreFront(String jsonFilePath) {
        inventoryManager = new InventoryManager();

        try {
            inventoryManager.getInventory().addAll(FileService.loadInventory(jsonFilePath));
        } catch (CustomFileException e) {
            System.out.println("Error loading inventory: " + e.getMessage());
        }
        StoreServer server = new StoreServer(8080, inventoryManager);
        new Thread(server).start();
    }

    public void runStore() {
        Scanner scanner = new Scanner(System.in);

        // Admin authentication step
        isAdmin = AdminAuthenticator.authenticate(scanner);

        boolean running = true;
        while (running) {
            System.out.println("\n==== Store Menu ====");
            System.out.println("1. View and Sort Inventory");
            System.out.println("2. Add to Cart");
            System.out.println("3. View Cart");
            System.out.println("4. Checkout");
            System.out.println("5. Exit");

            // Only show "Add Product" option for Admin
            if (isAdmin) {
                System.out.println("6. Add Product to Inventory");
            }

            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    inventoryManager.displaySortMenu(scanner);
                    break;

                case "2":
                    inventoryManager.displayInventory();
                    System.out.print("Enter the name of the product to add to cart: ");
                    String productName = scanner.nextLine();
                    SalableProduct productToAdd = findProductByName(productName);
                    if (productToAdd != null && productToAdd.getQuantity() > 0) {
                        cart.addToCart(productToAdd);
                        productToAdd.setQuantity(productToAdd.getQuantity() - 1);
                        System.out.println("Added to cart.");
                    } else {
                        System.out.println("Product not found or out of stock.");
                    }
                    break;

                case "3":
                    System.out.println("\nYour Cart:");
                    cart.displayCart();
                    System.out.println("Total: $" + cart.getTotal());
                    break;

                case "4":
                    System.out.println("Checking out...");
                    cart.displayCart();
                    System.out.println("Total: $" + cart.getTotal());
                    running = false;
                    break;

                case "5":
                    System.out.println("Exiting the store.");
                    running = false;
                    break;

                case "6":
                    if (isAdmin) {
                        addProductToInventory(scanner);  // Only for Admin
                    } else {
                        System.out.println("Access Denied: You must be logged in as Admin.");
                    }
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        scanner.close();
    }

    private void addProductToInventory(Scanner scanner) {
        System.out.println("\n==== Add New Product to Inventory ====");
        System.out.print("Enter product type (Weapon/Armor/Health): ");
        String productType = scanner.nextLine();

        System.out.print("Enter product name: ");
        String name = scanner.nextLine();

        System.out.print("Enter product description: ");
        String description = scanner.nextLine();

        System.out.print("Enter product price: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter product quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        SalableProduct newProduct = null;

        // Based on the product type, create the appropriate object
        switch (productType.toLowerCase()) {
            case "weapon":
                System.out.print("Enter weapon damage: ");
                int damage = Integer.parseInt(scanner.nextLine());
                newProduct = new Weapon(name, description, price, quantity, damage);
                break;

            case "armor":
                System.out.print("Enter armor defense: ");
                int defense = Integer.parseInt(scanner.nextLine());
                newProduct = new Armor(name, description, price, quantity, defense);
                break;

            case "health":
                System.out.print("Enter healing amount: ");
                int healingAmount = Integer.parseInt(scanner.nextLine());
                newProduct = new Health(name, description, price, quantity, healingAmount);
                break;

            default:
                System.out.println("Invalid product type.");
                return;
        }

        // Add the new product to the inventory
        if (newProduct != null) {
            inventoryManager.addProduct(newProduct);
            System.out.println("Product added to inventory: " + newProduct);
        }
    }

    private SalableProduct findProductByName(String name) {
        for (SalableProduct product : inventoryManager.getInventory()) {
            if (product.getName().equalsIgnoreCase(name)) {
                return product;
            }
        }
        return null;   
    }
}
