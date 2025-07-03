// ===== CRUDScene.java =====
import javafx.collections.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import java.sql.*;

public class CRUDScene {
    private static TableView<Student> tableView = new TableView<>();
    private static ObservableList<Student> studentList = FXCollections.observableArrayList();
    private static TextField idField = new TextField();
    private static TextField nameField = new TextField();
    private static TextField emailField = new TextField();

    private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static Scene getCrudScene() {
        TableColumn<Student, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Student, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Student, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        tableView.getColumns().addAll(idCol, nameCol, emailCol);

        idField.setPromptText("ID");
        nameField.setPromptText("Name");
        emailField.setPromptText("Email");

        HBox inputFields = new HBox(10, idField, nameField, emailField);

        Button viewBtn = new Button("View");
        viewBtn.setOnAction(e -> loadData());

        Button insertBtn = new Button("Insert");
        insertBtn.setOnAction(e -> insertStudent());

        Button updateBtn = new Button("Update");
        updateBtn.setOnAction(e -> updateStudent());

        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> deleteStudent());

        HBox buttons = new HBox(10, viewBtn, insertBtn, updateBtn, deleteBtn);
        Label footer = new Label("Name: Niraj Bhandari | ID: 23093760 | Date: 03-July-2025");

        VBox layout = new VBox(15, tableView, inputFields, buttons, footer);
        layout.setStyle("-fx-padding: 20; -fx-alignment: center;");

        return new Scene(layout, 700, 400);
    }

    private static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    private static void loadData() {
        studentList.clear();
        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM students")) {
            while (rs.next()) {
                studentList.add(new Student(rs.getInt("id"), rs.getString("name"), rs.getString("email")));
            }
            tableView.setItems(studentList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void insertStudent() {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO students (name, email) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText());
            stmt.setString(2, emailField.getText());
            stmt.executeUpdate();
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateStudent() {
        try (Connection conn = connect()) {
            String sql = "UPDATE students SET name = ?, email = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nameField.getText());
            stmt.setString(2, emailField.getText());
            stmt.setInt(3, Integer.parseInt(idField.getText()));
            stmt.executeUpdate();
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void deleteStudent() {
        try (Connection conn = connect()) {
            String sql = "DELETE FROM students WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, Integer.parseInt(idField.getText()));
            stmt.executeUpdate();
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}