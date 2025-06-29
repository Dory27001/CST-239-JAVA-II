package admin;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import com.google.gson.*;

public class AdminClient {
    private static final String HOST = "localhost";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        if (args.length > 0) {
            String command = args[0];
            if ("update".equalsIgnoreCase(command) && args.length == 2) {
                sendUpdateCommand(args[1]);
            } else if ("retrieve".equalsIgnoreCase(command)) {
                sendRetrieveCommand();
            } else {
                System.out.println("Usage: java AdminClient <command> [jsonFilePath]");
                System.out.println("Commands: update <jsonFilePath>, retrieve");
            }
        } else {
            System.out.println("No command provided. Please specify a command.");
        }
    }   

    public static void sendUpdateCommand(String jsonFilePath) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(jsonFilePath)));

            // Basic validation of JSON format
            if (!isValidJson(json)) {
                System.out.println("Invalid JSON format.");
                return;
            }

            String response = sendRequest("U", json);
            if (response != null) {
                System.out.println("Server response: " + response);
            }
        } catch (IOException e) {
            System.out.println("Failed to read JSON file: " + e.getMessage());
        }
    }

    public static void sendRetrieveCommand() {
        String response = sendRequest("R", null);
        if (response != null) {
            Gson gson = new Gson();
            ArrayList<?> list = gson.fromJson(response, ArrayList.class);
            System.out.println("\n-- Store Inventory --");
            for (Object item : list) {
                System.out.println(item.toString());
            }
        }
    }

    private static String sendRequest(String command, String jsonData) {
        try (Socket socket = new Socket(HOST, PORT);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            out.println(command);  // First line is the command: "U" or "R"

            if (jsonData != null) {
                out.println(jsonData); // Second line is the JSON data (if updating)
            }

            return in.readLine(); // Response from server
        } catch (IOException e) {
            System.out.println("Connection error: " + e.getMessage());
        }
        return null;
    }

    private static boolean isValidJson(String json) {
        try {
            JsonParser parser = new JsonParser();
            parser.parse(json);
            return true;
        } catch (JsonSyntaxException e) {
            return false;
        }
    }
}
