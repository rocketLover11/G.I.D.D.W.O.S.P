import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteTest {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:sqlite:db/test.db";
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(jdbcUrl);
            System.out.println("Connection to SQLite established.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("Connection Closed");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
