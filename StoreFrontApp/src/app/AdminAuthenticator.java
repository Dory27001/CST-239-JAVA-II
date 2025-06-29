package app;

import java.util.Scanner;

public class AdminAuthenticator {

    private static final String ADMIN_USERNAME = "superadmin";  // New admin username
    private static final String ADMIN_PASSWORD = "securePass456";  // New admin password
    
    public static boolean authenticate(Scanner scanner) {
        System.out.print("Enter Admin Username: ");
        String username = scanner.nextLine();
 
        System.out.print("Enter Admin Password: "); 
        String password = scanner.nextLine();

        if (username.equals(ADMIN_USERNAME) && password.equals(ADMIN_PASSWORD)) {
            System.out.println("Authentication successful.");
            return true;  // Admin authenticated successfully
        } else { 
            System.out.println("Authentication failed. Incorrect username or password.");
            return false;  // Authentication failed
        }
    }
}
