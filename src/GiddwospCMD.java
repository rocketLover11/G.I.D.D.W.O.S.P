import java.util.Scanner;
import java.io.IOException;

public class GiddwospCMD {
    static Scanner sc = new Scanner(System.in);
    final static String os = System.getProperty("os.name");
    static String username;
    
    public static void restartProgram() {
        try {
            ProcessBuilder pb = new ProcessBuilder("java", "-cp", "'.;lib/SQLite-Java.jar'", "GiddwospCMD");
            pb.start();
            System.exit(0);
        } catch (IOException e) {
            System.out.println("Error restarting the program: " + e.getMessage());
        }
    }

    public static void cls() {
        try {
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (IOException || InterruptedException e) {
            System.out.println("Error clearing the screen: " + e.getMessage());
        }
    }
    
    public static void usersMenu() {
        cls();

        System.out.println("Users Menu\n");
        System.out.println("Enter an Option:\n1. Reset Password\n2. Delete User\n3. Back to Main Menu\n");

        boolean validInput;
        do {
            System.out.print("Enter a Number: >> ");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    cls();
                    System.out.println("Reset Password:\n");
                    System.out.println("Note: You will need to answer your security question to reset your password.\n");
                    UserDBTools.resetPassword(username);
                    validInput = true;
                    break;
                case 2:
                    UserDBTools.deleteUser(username);
                    validInput = true;
                    break;
                case 3:
                    cmd();
                    validInput = true;
                    break;
                default:
                    System.out.println("Invalid Input.");
                    validInput = false;
            }
        } while (validInput == false);
    }

    public static void appsMenu() {

    }

    public static void cmd() {
        cls();
        System.out.println("Welcome to the G.I.D.D.W.O.S.P Command Interface!\n");
        
        String password;
        do {
            System.out.println("Login:");

            System.out.print("Enter Username or Type 'CreateUser': >> ");
            username = sc.nextLine();

            if (username.equalsIgnoreCase("createuser")) {
                cls();
                System.out.println("New User:\n");
                System.out.print("Enter new Username: >> ");
                String newUsername = sc.nextLine();
                System.out.print("\nEnter new Password: >> ");
                String newPassword = sc.nextLine();
                System.out.print("\nEnter new Security Question: >> ");
                String newSecQ = sc.nextLine();
                System.out.print("\nEnter new Security Answer: >> ");
                String newSecA = sc.nextLine();
                UserDBTools.createUser(newUsername, newPassword, newSecQ, newSecA);
                restartProgram();
            }

            System.out.print("\nEnter Password: >> ");
            password = sc.nextLine();
        } while (!UserDBTools.loginCorrect(username, password));

        cls();
        System.out.println("Hello, " + username + "!\n");        
        
        System.out.println("Choose a menu:\n1. Users\n2. Applications\n3. Exit\n");
        boolean validInput;
        do {
            System.out.println("Enter a Number: >> ");
            int option = sc.nextInt();
            switch (option) {
                case 1:
                    usersMenu();
                    validInput = true;
                    break;
                case 2:
                    appsMenu();
                    validInput = true;
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    validInput = true;
                default:
                    System.out.println("Invalid Input.");
                    validInput = false;
            }
        } while (validInput == false);
    }

    public static void main(String[] args) {
        cmd();
    }
}