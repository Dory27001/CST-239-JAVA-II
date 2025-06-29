package app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class StoreUnitTests {

    @Test
    @DisplayName("SalableProduct getters and setters work correctly")
    public void testSalableProductGettersSetters() {
        SalableProduct p = new SalableProduct("Sword", "Sharp blade", 100.0, 5);

        assertEquals("Sword", p.getName());
        assertEquals("Sharp blade", p.getDescription());
        assertEquals(100.0, p.getPrice());
        assertEquals(5, p.getQuantity());

        p.setName("Axe");
        p.setDescription("Heavy axe");
        p.setPrice(80.0);
        p.setQuantity(3);

        assertEquals("Axe", p.getName());
        assertEquals("Heavy axe", p.getDescription());
        assertEquals(80.0, p.getPrice());
        assertEquals(3, p.getQuantity());
    }

    @Test
    @DisplayName("SalableProduct comparison uses price then name (case-insensitive)")
    public void testSalableProductComparison() {
        SalableProduct p1 = new SalableProduct("Sword", "A", 100.0, 2);
        SalableProduct p2 = new SalableProduct("sword", "B", 90.0, 2);

        assertTrue(p2.compareTo(p1) < 0); // p2 has lower price
    }

    @Test
    @DisplayName("InventoryManager add, remove, sort functions as expected")
    public void testInventoryManagerAddRemoveSort() {
        InventoryManager manager = new InventoryManager();

        SalableProduct sword = new SalableProduct("Sword", "A", 100.0, 5);
        SalableProduct axe = new SalableProduct("Axe", "B", 50.0, 3);

        manager.addProduct(sword);
        manager.addProduct(axe);

        assertEquals(2, manager.getInventory().size());

        manager.sortInventoryAscending();
        assertEquals("Axe", manager.getInventory().get(0).getName());

        manager.sortInventoryDescending();
        assertEquals("Sword", manager.getInventory().get(0).getName());

        manager.removeProduct(sword);
        assertEquals(1, manager.getInventory().size());
    }

    @Test
    @DisplayName("ShoppingCart adds/removes products and calculates total")
    public void testShoppingCartAddRemoveTotal() {
        ShoppingCart cart = new ShoppingCart();

        SalableProduct p1 = new SalableProduct("Potion", "Restores HP", 25.0, 10);
        SalableProduct p2 = new SalableProduct("Elixir", "Restores MP", 30.0, 5);

        cart.addToCart(p1);
        cart.addToCart(p2);

        assertEquals(55.0, cart.getTotal(), 0.001);

        cart.removeFromCart(p1);
        assertEquals(30.0, cart.getTotal(), 0.001);
    }

    @Test
    @DisplayName("FileService throws exception on invalid file path")
    public void testFileServiceInvalidPath() {
        Exception exception = assertThrows(CustomFileException.class, () -> {
            FileService.loadInventory("invalid_path.json");
        });

        assertTrue(exception.getMessage().contains("Error reading file"));
    }

    @Test
    @DisplayName("StoreFront loads inventory from file")
    public void testStoreFrontLoadInventory() {
        String testPath = "C:\\Users\\doryr_no8qyxh\\eclipse-workspace\\JAVA\\StoreFrontApp\\inventory\\inventory.json";
        
        StoreFront store = new StoreFront(testPath);
        
        assertNotNull(store);
    }
}