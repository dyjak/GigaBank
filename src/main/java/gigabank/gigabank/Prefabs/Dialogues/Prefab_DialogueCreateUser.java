package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralCreator;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.Optional;

public class Prefab_DialogueCreateUser {
    Dialog<Pair<String, String>> dialog;

    public Prefab_DialogueCreateUser() {
        dialog = new Dialog<>();
        dialog.setTitle("Create New User");
        dialog.setHeaderText("Create New User");
        ImageView icon_create = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/plus.png")));
        icon_create.setFitWidth(20);
        icon_create.setFitHeight(20);
        dialog.setGraphic(icon_create);
        dialog.setOnShown(event -> {
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/plus.png")));
        });

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField surnameField = new TextField();
        surnameField.setPromptText("Surname");
        DatePicker birthDatePicker = new DatePicker();
        ComboBox<String> sexComboBox = new ComboBox<>();
        sexComboBox.getItems().addAll("Male", "Female");
        TextField loginField = new TextField();
        loginField.setPromptText("Login");
        PasswordField pinField = new PasswordField();
        pinField.setPromptText("PIN");

        grid.add(new Label("Name:"), 0, 0);
        grid.add(nameField, 1, 0);
        grid.add(new Label("Surname:"), 0, 1);
        grid.add(surnameField, 1, 1);
        grid.add(new Label("Birthdate:"), 0, 2);
        grid.add(birthDatePicker, 1, 2);
        grid.add(new Label("Sex:"), 0, 3);
        grid.add(sexComboBox, 1, 3);
        grid.add(new Label("Login:"), 0, 4);
        grid.add(loginField, 1, 4);
        grid.add(new Label("PIN:"), 0, 5);
        grid.add(pinField, 1, 5);

        Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
        createButton.setDisable(true);

        ChangeListener<String> listener = (observable, oldValue, newValue) -> {
            createButton.setDisable(
                    nameField.getText().trim().isEmpty() ||
                            surnameField.getText().trim().isEmpty() ||
                            birthDatePicker.getValue() == null ||
                            sexComboBox.getValue() == null ||
                            loginField.getText().trim().isEmpty() ||
                            pinField.getText().trim().isEmpty()
            );
        };

        nameField.textProperty().addListener(listener);
        surnameField.textProperty().addListener(listener);
        birthDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> listener.changed(null, null, null));
        sexComboBox.valueProperty().addListener((observable, oldValue, newValue) -> listener.changed(null, null, null));
        loginField.textProperty().addListener(listener);
        pinField.textProperty().addListener(listener);

        StackPane root = new StackPane(grid);
        root.setPrefSize(400, 300);
        root.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(root);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                String birthDate = birthDatePicker.getValue().toString();
                String sex = sexComboBox.getValue();
                String login = loginField.getText();
                String pin = pinField.getText();
                System.out.println("Creating user: " + name + " " + surname);

                DB_ProceduralCreator dbProceduralCreater = new DB_ProceduralCreator();
                try {
                    dbProceduralCreater.userCreate(name, surname, birthDate, sex, login, pin);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("User successfully created.");
                    alert.showAndWait();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });
    }

    public void showDialog() {
        Optional<Pair<String, String>> result = dialog.showAndWait();
    }
}