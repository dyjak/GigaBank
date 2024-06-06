package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralUpdater;
import gigabank.gigabank.Entities.EntityUser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Optional;

public class Prefab_DialogueEditUser {
    Dialog<Void> dialog;
    EntityUser user;

    public Prefab_DialogueEditUser(EntityUser user) {
        this.user = user;

        dialog = new Dialog<>();
        dialog.setTitle("Edit User Data");
        dialog.setHeaderText("Edit User Data");
        ImageView icon_edit = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/edit.png")));
        icon_edit.setFitWidth(20);
        icon_edit.setFitHeight(20);
        dialog.setGraphic(icon_edit);
        dialog.setOnShown(event -> {
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/edit.png")));
        });

        ButtonType commitButtonType = new ButtonType("Commit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(commitButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(50);
        col2.setPercentWidth(50);
        grid.getColumnConstraints().addAll(col1, col2);

        TextField nameField = new TextField(user.getName());
        nameField.setPromptText("Name");
        TextField surnameField = new TextField(user.getSurname());
        surnameField.setPromptText("Surname");
        DatePicker birthDatePicker = new DatePicker(convertToLocalDate((Date) user.getBirthdate()));
        ComboBox<String> sexComboBox = new ComboBox<>();
        sexComboBox.getItems().addAll("Male", "Female");
        sexComboBox.setValue(user.getSex());
        TextField loginField = new TextField(user.getLogin());
        loginField.setPromptText("Login");
        PasswordField pinField = new PasswordField();
        pinField.setText(user.getPin());
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

        Node commitButton = dialog.getDialogPane().lookupButton(commitButtonType);
        commitButton.setDisable(true);

        ChangeListener<String> listener = (observable, oldValue, newValue) -> {
            commitButton.setDisable(
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
            if (dialogButton == commitButtonType) {
                String name = nameField.getText();
                String surname = surnameField.getText();
                LocalDate birthDateLocal = birthDatePicker.getValue();
                java.sql.Date birthDate = java.sql.Date.valueOf(birthDateLocal);
                String sex = sexComboBox.getValue();
                String login = loginField.getText();
                String pin = pinField.getText();
                System.out.println("Updating user: " + name + " " + surname);

                DB_ProceduralUpdater dbProceduralUpdater = new DB_ProceduralUpdater();
                try {
                    dbProceduralUpdater.userUpdate(user.getUser_id(), name, surname, birthDate, sex, login, pin);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("User data successfully updated.");
                    alert.showAndWait();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });
    }

    public void showDialog() {
        dialog.showAndWait();
    }

    private LocalDate convertToLocalDate(java.sql.Date dateToConvert) {
        return dateToConvert.toLocalDate();
    }
}
