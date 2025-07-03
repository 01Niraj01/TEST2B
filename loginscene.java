public import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class loginscene {
    public static Scene getLoginScene(Stage stage, Runnable onSuccess) {
        Label userLabel = new Label("Username:");
        TextField usernameField = new TextField();
        Label passLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button loginButton = new Button("Login");
        Label message = new Label();

        loginButton.setOnAction(e -> {
            String user = usernameField.getText();
            String pass = passwordField.getText();
            if (LoginService.authenticate(user, pass)) {
                onSuccess.run();
            } else {
                message.setText("Login failed. Try again.");
            }
        });

        VBox layout = new VBox(10, userLabel, usernameField, passLabel, passwordField, loginButton, message);
        layout.setStyle("-fx-padding: 30; -fx-alignment: center;");
        return new Scene(layout, 400, 300);
    }
} {
    
}
