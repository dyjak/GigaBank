package gigabank.gigabank;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerAdministratorAccountsPanel implements Initializable{

    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToMainPanel(ActionEvent event) throws IOException {

        System.out.println("Switching to main panel");

        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/main-administrator-panel-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("SUCCESFULLY INITIALIZED");
    }

}
