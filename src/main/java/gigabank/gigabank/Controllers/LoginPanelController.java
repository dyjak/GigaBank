package gigabank.gigabank.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginPanelController {

    @FXML
    Button btn1;

    public void handlebtn1() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fxmls/main-panel-view.fxml"));

        Stage window = (Stage) btn1.getScene().getWindow();
        window.setScene(new Scene(root, 777,200));
    }
}
