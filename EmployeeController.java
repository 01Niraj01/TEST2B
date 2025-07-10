import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class EmployeeController {

    private final String URL = "jdbc:mysql://localhost:3306/hrdb?useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "";

    // Fetch all employees from database
    public ObservableList<Employee> getAllEmployees() {
        ObservableList<Employee> employees = FXCollections.observableArrayList();

        String sql = "SELECT * FROM employees";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Employee emp = new Employee(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("position")
                );
                employees.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    // Insert a new employee
    public boolean addEmployee(Employee emp) {
        String sql = "INSERT INTO employees (name, position) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getPosition());

            int affected = pstmt.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update existing employee
    public boolean updateEmployee(Employee emp) {
        String sql = "UPDATE employees SET name = ?, position = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, emp.getName());
            pstmt.setString(2, emp.getPosition());
            pstmt.setInt(3, emp.getId());

            int affected = pstmt.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete employee by ID
    public boolean deleteEmployee(int employeeId) {
        String sql = "DELETE FROM employees WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, employeeId);

            int affected = pstmt.executeUpdate();
            return affected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
