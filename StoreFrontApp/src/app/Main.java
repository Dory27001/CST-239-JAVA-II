package app;

public class Main {
    public static void main(String[] args) {
        String jsonFilePath = "C:\\Users\\doryr_no8qyxh\\Desktop\\Milestone6\\inventory\\inventory.json\\";

        StoreFront store = new StoreFront(jsonFilePath);
        store.runStore(); // Sorted inventory will be displayed
    }
}
