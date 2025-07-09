import java.sql.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DatabaseService {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/JavaFXAppDB";
    private static final String USER = "root";
    private static final String PASS = "yourPassword";

    // Connect to DB
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // 🟢 LOGIN operation
    public static boolean validateLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, password); // Use hashed passwords in production
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 🟢 CREATE operation
    public static void addContact(String name, String email, String phone) {
        String sql = "INSERT INTO contacts (name, email, phone) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🟡 READ operation
    public static ObservableList<Contact> getAllContacts() {
        ObservableList<Contact> list = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(new Contact(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 🟠 UPDATE operation
    public static void updateContact(int id, String name, String email, String phone) {
        String sql = "UPDATE contacts SET name = ?, email = ?, phone = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setInt(4, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 🔴 DELETE operation
    public static void deleteContact(int id) {
        String sql = "DELETE FROM contacts WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
