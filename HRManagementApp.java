import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class HRManagementApp extends Application {

    private final String URL = "jdbc:mysql://localhost:3306/hrdb?useSSL=false&serverTimezone=UTC";
    private final String USER = "root";
    private final String PASSWORD = "";

    // Login UI components
    private TextField emailField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Label loginMsg = new Label();

    // CRUD UI components
    private TableView<Employee> table = new TableView<>();
    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    private TextField idField = new TextField();
    private TextField nameField = new TextField();
    private TextField positionField = new TextField();
    private Label crudMsg = new Label();

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("HR Management System");

        Scene loginScene = createLoginScene();
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    // --------- VIEW METHODS ---------

    private Scene createLoginScene() {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));

        emailField.setPromptText("Email");
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        loginBtn.setOnAction(e -> handleLogin());

        loginMsg.setStyle("-fx-text-fill: red;");

        layout.getChildren().addAll(
                new Label("Login Page"),
                emailField,
                passwordField,
                loginBtn,
                loginMsg
        );

        return new Scene(layout, 300, 200);
    }

    private Scene createCrudScene() {
        // Table Columns
        TableColumn<Employee, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        TableColumn<Employee, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);

        TableColumn<Employee, String> posCol = new TableColumn<>("Position");
        posCol.setCellValueFactory(new PropertyValueFactory<>("position"));
        posCol.setPrefWidth(200);

        table.getColumns().setAll(idCol, nameCol, posCol);
        table.setItems(employees);

        // When row selected, fill input fields
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                idField.setText(String.valueOf(newSelection.getId()));
                nameField.setText(newSelection.getName());
                positionField.setText(newSelection.getPosition());
                crudMsg.setText("");
            }
        });

        // Input fields with prompt text
        idField.setPromptText("ID (for update/delete)");
        idField.setDisable(true);  // ID is auto-generated, disable editing

        nameField.setPromptText("Name");
        positionField.setPromptText("Position");

        Button addBtn = new Button("Add");
        addBtn.setOnAction(e -> handleAdd());

        Button updateBtn = new Button("Update");
        updateBtn.setOnAction(e -> handleUpdate());

        Button deleteBtn = new Button("Delete");
        deleteBtn.setOnAction(e -> handleDelete());

        HBox form = new HBox(10, nameField, positionField, addBtn, updateBtn, deleteBtn);
        form.setPadding(new Insets(10));

        crudMsg.setStyle("-fx-text-fill: green;");

        VBox layout = new VBox(10, new Label("Employee CRUD Operations"), table, form, crudMsg);
        layout.setPadding(new Insets(10));

        return new Scene(layout, 600, 400);
    }

    // --------- CONTROLLER METHODS ---------

    private void handleLogin() {
        loginMsg.setText("");
        String email = emailField.getText().trim();
        String password = passwordField.getText().trim();

        if (email.isEmpty() || password.isEmpty()) {
            loginMsg.setText("Email and password required.");
            return;
        }

        if (validateLogin(email, password)) {
            loadEmployees();
            primaryStage.setScene(createCrudScene());
        } else {
            loginMsg.setText("Login failed. Invalid credentials.");
        }
    }

    private boolean validateLogin(String email, String password) {
        String sql = "SELECT * FROM login WHERE email = ? AND password = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void loadEmployees() {
        employees.clear();
        String sql = "SELECT * FROM employees";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleAdd() {
        String name = nameField.getText().trim();
        String position = positionField.getText().trim();
        crudMsg.setText("");

        if (name.isEmpty() || position.isEmpty()) {
            crudMsg.setStyle("-fx-text-fill: red;");
            crudMsg.setText("Name and Position are required.");
            return;
        }

        String sql = "INSERT INTO employees (name, position) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, position);
            stmt.executeUpdate();
            crudMsg.setStyle("-fx-text-fill: green;");
            crudMsg.setText("Employee added successfully.");
            clearFields();
            loadEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
            crudMsg.setStyle("-fx-text-fill: red;");
            crudMsg.setText("Error adding employee.");
        }
    }

    private void handleUpdate() {
        Employee selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            crudMsg.setStyle("-fx-text-fill: red;");
            crudMsg.setText("Select an employee to update.");
            return;
        }

        String name = nameField.getText().trim();
        String position = positionField.getText().trim();
        if (name.isEmpty() || position.isEmpty()) {
            crudMsg.setStyle("-fx-text-fill: red;");
            crudMsg.setText("Name and Position are required.");
            return;
        }

        String sql = "UPDATE employees SET name = ?, position = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, position);
            stmt.setInt(3, selected.getId());
            stmt.executeUpdate();
            crudMsg.setStyle("-fx-text-fill: green;");
            crudMsg.setText("Employee updated successfully.");
            clearFields();
            loadEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
            crudMsg.setStyle("-fx-text-fill: red;");
            crudMsg.setText("Error updating employee.");
        }
    }

    private void handleDelete() {
        Employee selected = table.getSelectionModel().getSelectedItem();
        if (selected == null) {
            crudMsg.setStyle("-fx-text-fill: red;");
            crudMsg.setText("Select an employee to delete.");
            return;
        }

        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, selected.getId());
            stmt.executeUpdate();
            crudMsg.setStyle("-fx-text-fill: green;");
            crudMsg.setText("Employee deleted successfully.");
            clearFields();
            loadEmployees();
        } catch (SQLException e) {
            e.printStackTrace();
            crudMsg.setStyle("-fx-text-fill: red;");
            crudMsg.setText("Error deleting employee.");
        }
    }

    private void clearFields() {
        idField.clear();
        nameField.clear();
        positionField.clear();
        table.getSelectionModel().clearSelection();
    }

    // --------- MODEL ---------
    public static class Employee {
        private final SimpleIntegerProperty id;
        private final SimpleStringProperty name;
        private final SimpleStringProperty position;

        public Employee(int id, String name, String position) {
            this.id = new SimpleIntegerProperty(id);
            this.name = new SimpleStringProperty(name);
            this.position = new SimpleStringProperty(position);
        }

        public int getId() {
            return id.get();
        }

        public String getName() {
            return name.get();
        }

        public String getPosition() {
            return position.get();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
