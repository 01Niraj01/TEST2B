// LoginScene.java
public class LoginScene {
    public static Scene createLoginScene(Stage primaryStage) {
        Label userLabel = new Label("Username:");
        TextField userField = new TextField();
        
        Label passLabel = new Label("Password:");
        PasswordField passField = new PasswordField();
        
        Button loginButton = new Button("Login");
        Button clearButton = new Button("Clear");
        
        // Layout
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(10);
        
        grid.add(userLabel, 0, 0);
        grid.add(userField, 1, 0);
        grid.add(passLabel, 0, 1);
        grid.add(passField, 1, 1);
        grid.add(loginButton, 0, 2);
        grid.add(clearButton, 1, 2);
        
        return new Scene(grid, 350, 200);
    }
}
