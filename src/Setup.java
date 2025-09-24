import java.sql.*;
import java.util.Scanner;

public class Setup {
    static Scanner sc = new Scanner(System.in);
    static String jdbcUrl = UserDBTools.jdbc(); 
    public static void main(String[] args) {
        UserDBTools.createTable();
        System.out.println("Setting up default admin user.");
        System.out.print("Enter admin password: >> ");
        String adminPwd = sc.nextLine();
        UserDBTools.createUser("admin", adminPwd, "Default Admin Question", "Default Admin Answer");
        boolean validInput;
        do {
            System.out.println("Do you want to create a personal user? (y/n): >> ");
            String pu = sc.nextLine();
            if (pu.equalsIgnoreCase("y")) {
                System.out.print("Enter Username: >> ");
                String newUsername = sc.nextLine();
                System.out.print("\nEnter Password: >> ");
                String newPassword = sc.nextLine();
                System.out.print("\nEnter Security Question: >> ");
                String newSecQ = sc.nextLine();
                System.out.print("\nEnter Answer to Security Question: >> ");
                String newSecA = sc.nextLine();
                UserDBTools.createUser(newUsername, newPassword, newSecQ, newSecA);
                validInput = true;
            } else if (pu.equalsIgnoreCase("n")) {
                validInput = true;
            } else {
                validInput = false;
                System.out.println("Invalid Input.");
            }
        } while (validInput == false);

        System.out.println("Setup Complete.");
        Connection c = null;
        Statement s = null;

        try {
            c = DriverManager.getConnection(jdbcUrl);
            s = c.createStatement();

            s.executeUpdate("");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}