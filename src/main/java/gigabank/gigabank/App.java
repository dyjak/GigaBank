package gigabank.gigabank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //FXML LOADERS
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxmls/login-panel-view.fxml"));

        System.out.println("HEllo");

        int width = 405; int height = 720;
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}