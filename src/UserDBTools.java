import java.sql.*;
import java.util.Scanner;

public class UserDBTools {
    static String jdbcUrl = "jdbc:sqlite:db\\userDB.db";
    static Scanner sc = new Scanner(System.in);

    public static void createTable() {
        Connection cct = null;
        Statement sct = null;
        try {
            cct = DriverManager.getConnection(jdbcUrl);
            sct = cct.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS users " +
                         "(id INTEGER PRIMARY KEY, " + 
                         "username TEXT NOT NULL, " +
                         " password TEXT NOT NULL, " +
                         " secQ TEXT NOT NULL, " +
                         " secA TEXT NOT NULL);";
            sct.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (sct != null) sct.close();
                if (cct != null) cct.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static void createUser(String username, String pwd, String secQ, String secA) {
        try (Connection ccu = DriverManager.getConnection(jdbcUrl); Statement scu = c.createStatement()) {
            scu.executeUpdate("INSERT INTO users (username, password, secQ, secA) VALUES ('" + username + "', '" + pwd + "', '" + secQ + "', '" + secA + "');");

        } catch (SQLException ecu) {
            ecu.printStackTrace();
        } finally {
            try {
                if (scu != null) scu.close();
                if (ccu != null) ccu.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean loginCorrect(String username, String inputPassword) {
        Connection cvl = null;
        Statement svl = null;
        ResultSet rsvl = null;

        try {
            cvl = DriverManager.getConnection(jdbcUrl);
            svl = cvl.createStatement();
            rsvl = svl.executeQuery("SELECT password FROM users WHERE username = '" + username + "';");

            if (rsvl.next()) {
                String correctPassword = rsvl.getString("password");
                return inputPassword.equals(correctPassword);
            } else {
                return false;
            }
        } catch (SQLException evl) {
            evl.printStackTrace();
            return false;
        } finally {
            try {
                if (rsvl != null) rsvl.close();
                if (svl != null) svl.close();
                if (cvl != null) cvl.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void login() {
        do {
            System.out.print("Enter Username: >> ");
            String username = sc.nextLine();
            System.out.print("\nEnter Password: >> ");
            String password = sc.nextLine();
        } while (!loginCorrect(username, password));
    }

    public static resetPassword(String username, String newPassword, String secA) {
        Connection crp = null;
        Statement srp = null;
        ResultSet rrp = null;

        try {
            crp = DriverManager.getConnection(jdbcUrl);
            srp = crp.createStatement();
            rrp = srp.executeQuery("SELECT secQ, secA FROM users WHERE username = '" + username + "';");

            if (rrp.next()) {
                String secQ = rrp.getString("secQ");
                String correctSecA = rrp.getString("secA");
            } else {
                System.out.println("Username not found.");
                return;
            }

            if (secA.equals(correctSecA)) {
                srp.executeUpdate("UPDATE users SET password = '" + newPassword + "' WHERE username = '" + username + "';");
                System.out.println("Password successfully reset.");
            } else {
                System.out.println("Incorrect security answer. Password reset failed.");
            }
        } catch (SQLException erp) {
            erp.printStackTrace();
        } finally {
            try {
                if (rrp != null) rrp.close();
                if (srp != null) srp.close();
                if (crp != null) crp.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void deleteUser(String username, String inputPassword) {
        Connection cdu = null;
        Statement sdu = null;

        login();

        try {
            cdu = DriverManager.getConnection(jdbcUrl);
            sdu = cdu.createStatement();

            sdu.executeUpdate("DELETE FROM users WHERE username = '" + username + "';");
            System.out.println("User " + username + " Successfully Deleted.");
        } catch (SQLException edu) {
            edu.printStackTrace();
        } finally {
            try {
                if (sdu != null) sdu.close();
                if (cdu != null) cdu.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}