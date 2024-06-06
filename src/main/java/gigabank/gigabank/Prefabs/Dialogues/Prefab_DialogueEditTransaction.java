package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralUpdater;
import gigabank.gigabank.Entities.EntityTransaction;
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
import java.util.Optional;

public class Prefab_DialogueEditTransaction {
    Dialog<Void> dialog;
    EntityTransaction transaction;

    public Prefab_DialogueEditTransaction(EntityTransaction transaction) {
        this.transaction = transaction;

        dialog = new Dialog<>();
        dialog.setTitle("Edit Transaction Data");
        dialog.setHeaderText("Edit Transaction Data");
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

        TextField accountIdField = new TextField(String.valueOf(transaction.getAccount_id()));
        accountIdField.setEditable(false);
        TextField amountField = new TextField(String.valueOf(transaction.getAmount()));
        amountField.setPromptText("Amount");
        TextField contractorAccountNumField = new TextField(transaction.getContractor_account_number());
        contractorAccountNumField.setPromptText("Contractor Account Number");
        TextArea descriptionArea = new TextArea(transaction.getDescription());
        descriptionArea.setPromptText("Description");


        grid.add(new Label("Amount:"), 0, 1);
        grid.add(amountField, 1, 1);
        grid.add(new Label("Contractor Account Number:"), 0, 2);
        grid.add(contractorAccountNumField, 1, 2);
        grid.add(new Label("Description:"), 0, 3);
        grid.add(descriptionArea, 1, 3);

        Node commitButton = dialog.getDialogPane().lookupButton(commitButtonType);
        commitButton.setDisable(true);

        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            commitButton.setDisable(newValue.trim().isEmpty());
        });
        contractorAccountNumField.textProperty().addListener((observable, oldValue, newValue) -> {
            commitButton.setDisable(newValue.trim().isEmpty());
        });
        descriptionArea.textProperty().addListener((observable, oldValue, newValue) -> {
            commitButton.setDisable(newValue.trim().isEmpty());
        });

        StackPane root = new StackPane(grid);
        root.setPrefSize(400, 300);
        root.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(root);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == commitButtonType) {
                int accountId = transaction.getAccount_id();
                double amount = Double.parseDouble(amountField.getText());
                String contractorAccountNum = contractorAccountNumField.getText();
                String description = descriptionArea.getText();
                System.out.println("Updating transaction with ID " + transaction.getTransaction_id());

                DB_ProceduralUpdater dbProceduralUpdater = new DB_ProceduralUpdater();
                try {
                    dbProceduralUpdater.transactionUpdate(transaction.getTransaction_id(), accountId, amount, contractorAccountNum, description);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Transaction data successfully updated.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update transaction: " + e.getMessage());
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