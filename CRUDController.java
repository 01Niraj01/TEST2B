public class CrudController {
    @FXML private TextField idField, nameField, emailField, phoneField;
    @FXML private TableView<Contact> tableView;
    @FXML private TableColumn<Contact, Integer> colId;
    @FXML private TableColumn<Contact, String> colName, colEmail, colPhone;

    private ObservableList<Contact> contactList;
    private Connection connection;

    public void initialize() {
        connection = DBUtil.connect();
        contactList = FXCollections.observableArrayList();

        // Bind columns
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        loadData();
    }

    private void loadData() {
        contactList.clear();
        String sql = "SELECT * FROM contacts";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                contactList.add(new Contact(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone")
                ));
            }
            tableView.setItems(contactList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAdd() {
        String sql = "INSERT INTO contacts (name, email, phone) VALUES (?, ?, ?)";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, nameField.getText());
            pst.setString(2, emailField.getText());
            pst.setString(3, phoneField.getText());
            pst.executeUpdate();
            loadData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // handleUpdate, handleDelete, handleClear methods follow similar structure
}
