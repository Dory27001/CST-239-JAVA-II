package app;

import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<SalableProduct> cart = new ArrayList<>();

    // Add product to cart
    public void addToCart(SalableProduct product) {
        cart.add(product);
    }

    // Remove product from cart
    public void removeFromCart(SalableProduct product) {
        cart.remove(product);
    }

    // Get total cost of the cart
    public double getTotal() {
        double total = 0;
        for (SalableProduct product : cart) {
            total += product.getPrice();
        }
        return total;
    }

    // Display cart contents
    public void displayCart() {
        for (SalableProduct product : cart) {
            System.out.println(product.getName() + ": $" + product.getPrice());
        }
    }
}
