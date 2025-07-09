// CrudScene.java
public class CrudScene {
    public static Scene createCrudScene(Stage primaryStage) {
        Label idLabel = new Label("ID:");
        TextField idField = new TextField();
        
        Label nameLabel = new Label("Name:");
        TextField nameField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label phoneLabel = new Label("Phone:");
        TextField phoneField = new TextField();
        
        Button addButton = new Button("Add");
        Button updateButton = new Button("Update");
        Button deleteButton = new Button("Delete");
        Button clearButton = new Button("Clear");
        
        TableView<MyModel> tableView = new TableView<>();
        // Add columns to tableView here
        
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.addRow(0, idLabel, idField);
        form.addRow(1, nameLabel, nameField);
        form.addRow(2, emailLabel, emailField);
        form.addRow(3, phoneLabel, phoneField);
        form.addRow(4, addButton, updateButton);
        form.addRow(5, deleteButton, clearButton);

        VBox layout = new VBox(20, form, tableView);
        layout.setPadding(new Insets(20));

        return new Scene(layout, 600, 500);
    }
}
