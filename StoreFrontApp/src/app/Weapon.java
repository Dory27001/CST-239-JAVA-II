package app;

public class Weapon extends SalableProduct {
    private int damage;

    public Weapon(String name, String description, double price, int quantity, int damage) {
        super(name, description, price, quantity);
        this.damage = damage;
    }

    public int getDamage() { return damage; }
    public void setDamage(int damage) { this.damage = damage; }

    @Override
    public String toString() {
        return super.toString() + " (Damage: " + damage + ")";
    }
}
