package gigabank.gigabank.Prefabs;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;

public class Prefab_DialogueEdit {
    Dialog<Pair<String, Double>> dialog;

    public Prefab_DialogueEdit()
    {
        dialog = new Dialog<>();
        dialog.setTitle("Edit Dialogue");
        dialog.setHeaderText("Edit Dialogue");

        ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        TextField username = new TextField();
        username.setPromptText("Username");
        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(username, 1, 0);
        grid.add(new Label("Password:"), 0, 1);
        grid.add(password, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        username.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);



    }

    public void showDialog()
    {
        Optional<Pair<String, Double>> result = dialog.showAndWait();
        System.out.println("JEST OKKEJ");
    }
}
