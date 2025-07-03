import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        Scene loginScene = LoginScene.getLoginScene(primaryStage, this::showCrudScene);
        primaryStage.setTitle("Test 2 - Login");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    private void showCrudScene() {
        Scene crudScene = CRUDScene.getCrudScene();
        primaryStage.setScene(crudScene);
    }

    public static void main(String[] args) {
        launch();
    }
}