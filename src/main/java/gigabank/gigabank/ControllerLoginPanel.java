package gigabank.gigabank;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class ControllerLoginPanel {

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToMainPanel(ActionEvent event) throws IOException{

        System.out.println("Switching to main panel");

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/main-panel-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
