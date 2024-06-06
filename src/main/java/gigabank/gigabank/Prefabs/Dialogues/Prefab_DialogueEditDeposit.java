package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralUpdater;
import gigabank.gigabank.Entities.EntityDeposit;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class Prefab_DialogueEditDeposit {
    Dialog<Void> dialog;
    EntityDeposit deposit;

    public Prefab_DialogueEditDeposit(EntityDeposit deposit) {
        this.deposit = deposit;

        dialog = new Dialog<>();
        dialog.setTitle("Edit Deposit Data");
        dialog.setHeaderText("Edit Deposit Data");
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

        TextField titleField = new TextField(deposit.getTitle());
        titleField.setPromptText("Title");
        TextField amountField = new TextField(String.valueOf(deposit.getAmount()));
        amountField.setPromptText("Amount");
        TextField currencyIdField = new TextField(String.valueOf(deposit.getCurrency_id()));
        currencyIdField.setPromptText("Currency ID");
        TextField interestPercentageField = new TextField(String.valueOf(deposit.getInterest_percentage()));
        interestPercentageField.setPromptText("Interest Percentage");
        TextArea descriptionArea = new TextArea(deposit.getDescription());
        descriptionArea.setPromptText("Description");
        DatePicker createDateField = new DatePicker(convertToLocalDate((Date)deposit.getCreate_date()));
        DatePicker deadlineField = new DatePicker(convertToLocalDate((Date)deposit.getDeadline()));
        TextField statusField = new TextField(String.valueOf(deposit.getStatus()));
        statusField.setPromptText("Status");

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountField, 1, 1);
        grid.add(new Label("Currency ID:"), 0, 2);
        grid.add(currencyIdField, 1, 2);
        grid.add(new Label("Interest Percentage:"), 0, 3);
        grid.add(interestPercentageField, 1, 3);
        grid.add(new Label("Description:"), 0, 4);
        grid.add(descriptionArea, 1, 4);
        grid.add(new Label("Create Date:"), 0, 5);
        grid.add(createDateField, 1, 5);
        grid.add(new Label("Deadline:"), 0, 6);
        grid.add(deadlineField, 1, 6);
        grid.add(new Label("Status:"), 0, 7);
        grid.add(statusField, 1, 7);

        Node commitButton = dialog.getDialogPane().lookupButton(commitButtonType);
        commitButton.setDisable(true);

        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            commitButton.setDisable(newValue.trim().isEmpty());
        });
        currencyIdField.textProperty().addListener((observable, oldValue, newValue) -> {
            commitButton.setDisable(newValue.trim().isEmpty());
        });
        statusField.textProperty().addListener((observable, oldValue, newValue) -> {
            commitButton.setDisable(newValue.trim().isEmpty());
        });
        interestPercentageField.textProperty().addListener((observable, oldValue, newValue) -> {
            commitButton.setDisable(newValue.trim().isEmpty());
        });
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            commitButton.setDisable(newValue.trim().isEmpty());
        });

        StackPane root = new StackPane(grid);
        root.setPrefSize(400, 400);
        root.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(root);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == commitButtonType) {
                String title = titleField.getText();
                double amount = Double.parseDouble(amountField.getText());
                int currencyId = Integer.parseInt(currencyIdField.getText());
                double interestPercentage = Double.parseDouble(interestPercentageField.getText());
                String description = descriptionArea.getText();
                LocalDate createDate = createDateField.getValue();
                LocalDate deadline = deadlineField.getValue();
                double status = Double.parseDouble(statusField.getText());

                System.out.println("Updating deposit with ID " + deposit.getDeposit_id());

                DB_ProceduralUpdater dbProceduralUpdater = new DB_ProceduralUpdater();
                try {
                    dbProceduralUpdater.depositUpdate(deposit.getDeposit_id(), title, amount, currencyId, interestPercentage, description, Date.valueOf(createDate), Date.valueOf(deadline), status);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Deposit data successfully updated.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update deposit: " + e.getMessage());
                }
            }
            return null;
        });
    }

    public void showDialog() {
        dialog.showAndWait();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
        System.out.println(message);
    }

    private LocalDate convertToLocalDate(java.sql.Date dateToConvert) {
        return dateToConvert.toLocalDate();
    }
}