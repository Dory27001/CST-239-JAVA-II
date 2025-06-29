package app;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class StoreServer implements Runnable {
    private static final int PORT = 8080;  // Port to listen on
    private InventoryManager inventoryManager;

    // Constructor
    public StoreServer(int port, InventoryManager inventoryManager) {
        this.inventoryManager = inventoryManager;
    }

    @Override 
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("StoreFront server started on port " + PORT);
            
            // Continuously accept new client connections
            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Client connected: " + clientSocket.getInetAddress());
                    handleClient(clientSocket);
                } catch (IOException e) {
                    System.out.println("Error handling client connection: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
        }
    }

    // Handle communication with the connected client
    private void handleClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String command = in.readLine();  // First line: command

            if (command != null) {
                switch (command) {
                    case "U":
                        String jsonData = in.readLine();  // Second line: JSON data
                        updateInventory(jsonData);
                        out.println("Inventory updated successfully.");
                        break;

                    case "R":
                        String inventoryJson = inventoryManagerToJson();
                        out.println(inventoryJson);
                        break;

                    default:
                        out.println("Invalid command.");
                }
            }

        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }

    //  Deserialize incoming inventory JSON and update it
    private void updateInventory(String jsonData) {
        try {
            Gson gson = new GsonBuilder()
                .registerTypeAdapter(SalableProduct.class, new SalableProductDeserializer())
                .create();

            ArrayList<SalableProduct> updatedInventory = gson.fromJson(
                jsonData, new TypeToken<ArrayList<SalableProduct>>() {}.getType()
            );

            inventoryManager.getInventory().clear();
            inventoryManager.getInventory().addAll(updatedInventory);
            System.out.println("Inventory successfully updated via server.");
        } catch (Exception e) {
            System.out.println("Error updating inventory: " + e.getMessage());
        }
    }

    // Convert inventory to JSON string for sending to client
    private String inventoryManagerToJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(inventoryManager.getInventory());
    }
}
