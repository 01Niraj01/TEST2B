public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label messageLabel;

    private Connection connection;

    public void initialize() {
        connection = DBUtil.connect();  // Custom utility class for DB connection
    }

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, password); // In production: use hashed passwords!
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                messageLabel.setText("Login successful!");
                SceneManager.switchToCrudScene(); // Your scene swap logic
            } else {
                messageLabel.setText("Invalid credentials!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
