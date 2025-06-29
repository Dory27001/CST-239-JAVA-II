package app;

public class Health extends SalableProduct {
    private int healingAmount;

    public Health(String name, String description, double price, int quantity, int healingAmount) {
        super(name, description, price, quantity);
        this.healingAmount = healingAmount;
    }

    public int getHealingAmount() { return healingAmount; }
    public void setHealingAmount(int healingAmount) { this.healingAmount = healingAmount; }

    @Override
    public String toString() {
        return super.toString() + " (Healing: " + healingAmount + ")";
    }
}
