package com.example;

import com.example.adapters.ConsoleInputAdapter;
import com.example.commands.ExitCommand;
import com.example.commands.ICommand;
import com.example.core.Session;
import com.example.core.SmartOfficeHub;
import com.example.services.BookingMonitorService;
import com.example.services.UserAuthenticator;
import com.example.strategies.FiveMinuteTimeoutStrategy;
import com.example.utilities.CommandExecutionException;
import com.example.utilities.Logger;
import java.util.Scanner;

public class Main {
    
    private static final Logger logger = Logger.getInstance();
    private static final UserAuthenticator authenticator = new UserAuthenticator();
    private static final Session session = new Session(); // Session state
    
    public static void main(String[] args) {
        
        SmartOfficeHub hub = SmartOfficeHub.getInstance(); 

        ConsoleInputAdapter adapter = new ConsoleInputAdapter(session); 
        
        BookingMonitorService monitorService = 
            new BookingMonitorService(new FiveMinuteTimeoutStrategy());
            monitorService.startMonitoring(); 

        Scanner scanner = new Scanner(System.in);
        
        System.out.println("==========================================");
        System.out.println("      SMART OFFICE FACILITY MANAGER       ");
        System.out.println("==========================================");
        
        if (!handleLogin(scanner)) {
             System.err.println("Login failed. Shutting down.");
             monitorService.stopMonitoring();
             return;
        }

        ICommand command = null;
        System.out.println("\nLogin successful. Type 'help' for commands or 'exit' to quit.");

        do {
            System.out.print("\n> ");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("help")) {
                printHelp();
                continue;
            }

           try {
                command = adapter.parse(input);
                
                String output = command.execute();
                System.out.println(output);

            } catch (CommandExecutionException e) {
                logger.logError("User Command Failed: " + input, e);
                System.err.println(e.getMessage());
                
                command = null; 
            } catch (Exception e) {
                logger.logError("Fatal Application Error: " + input, e);
                
                // Print a generic, neat error to the user
                System.err.println("A critical system error occurred. See logs for details.");
                command = null;
            }
            
        } while (!(command instanceof ExitCommand));

        // 3. Cleanup
        monitorService.stopMonitoring();
        scanner.close();
        System.out.println("Application shutting down. Goodbye!");
    }
    
    // --- LOGIN HANDLER METHOD ---
    private static boolean handleLogin(Scanner scanner) {
        System.out.println("\n--- INITIAL LOGIN ---");
        System.out.print("Access Type (admin/user): ");
        String type = scanner.nextLine().trim().toLowerCase();
        
        if (type.equals("admin")) {
            System.out.print("Admin Username: ");
            String user = scanner.nextLine();
            System.out.print("Admin Password: ");
            String pass = scanner.nextLine();
            
            if (authenticator.authenticateAdmin(user, pass)) {
                session.loginAdmin(user, pass);
                return true;
            }
        } else if (type.equals("user")) {
            System.out.print("Phone Number (10 digits): ");
            String phone = scanner.nextLine();
            
            if (authenticator.authenticateUser(phone)) {
                // Store a token to represent the user session
                session.loginAdmin("user:" + phone, "GUEST"); 
                return true;
            }
        }
        
        return false;
    }

    private static void printHelp() {
        System.out.println("\n============== COMMAND MENU ==============");
        System.out.println("SECURED COMMANDS (requires Admin login):");
        System.out.println("Config room count [N]             ? Setup number of rooms");
        System.out.println("Block room [ID] [HH:mm] [Min]     ? Book a room");
        System.out.println("Cancel room [ID]                  ? Cancel a booking");
        System.out.println("------------------------------------------");
        System.out.println("UNSECURED COMMANDS (available to all):");
        System.out.println("Add occupant [ID] [Count]         ? Simulate occupancy");
        System.out.println("stats                             ? Show room usage stats");
        System.out.println("exit                              ? Exit the application");
        System.out.println("==========================================");
        
        if (session.isAuthenticated() && session.getUsername().equals("admin")) {
            System.out.println("\nSTATUS: Logged in as ADMIN. All commands are available.");
        } else if (session.isAuthenticated()) {
            System.out.println("\nSTATUS: Logged in as USER. Only unsecured commands are functional.");
        }
    }
}