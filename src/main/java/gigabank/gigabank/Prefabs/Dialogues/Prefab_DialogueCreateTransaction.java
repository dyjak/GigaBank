package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralCreator;
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
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Optional;

public class Prefab_DialogueCreateTransaction {
    Dialog<Void> dialog;
    int accountId;

    public Prefab_DialogueCreateTransaction(int accountId) {
        this.accountId = accountId;

        dialog = new Dialog<>();
        dialog.setTitle("Create New Transaction");
        dialog.setHeaderText("Create New Transaction");
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

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");
        TextField contractorAccountField = new TextField();
        contractorAccountField.setPromptText("Contractor Account");
        DatePicker executeDatePicker = new DatePicker();
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description");

        grid.add(new Label("Amount:"), 0, 0);
        grid.add(amountField, 1, 0);
        grid.add(new Label("Contractor Account:"), 0, 1);
        grid.add(contractorAccountField, 1, 1);
        grid.add(new Label("Execute Date:"), 0, 2);
        grid.add(executeDatePicker, 1, 2);
        grid.add(new Label("Description:"), 0, 3);
        grid.add(descriptionArea, 1, 3);

        Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
        createButton.setDisable(true);

        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            createButton.setDisable(newValue.trim().isEmpty());
        });

        StackPane root = new StackPane(grid);
        root.setPrefSize(400, 300);
        root.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(root);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                String amountStr = amountField.getText();
                double amount = Double.parseDouble(amountStr);
                String contractorAccount = contractorAccountField.getText();
                LocalDate executeDateLocal = executeDatePicker.getValue();
                Timestamp executeDate = Timestamp.valueOf(executeDateLocal.atStartOfDay());
                String description = descriptionArea.getText();

                DB_ProceduralCreator dbProceduralCreater = new DB_ProceduralCreator();
                try {
                    dbProceduralCreater.transactionCreate(accountId, amount, contractorAccount, executeDate, description);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Transaction successfully created.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to create transaction: " + e.getMessage());
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
    }
}