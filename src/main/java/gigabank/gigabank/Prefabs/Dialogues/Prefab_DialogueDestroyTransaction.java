package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralDestroyer;
import gigabank.gigabank.Entities.EntityTransaction;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Optional;

public class Prefab_DialogueDestroyTransaction {
    Dialog<Void> dialog;
    EntityTransaction transaction;

    public Prefab_DialogueDestroyTransaction(EntityTransaction transaction) {
        this.transaction = transaction;

        dialog = new Dialog<>();
        dialog.setTitle("Delete Transaction");
        dialog.setHeaderText("Are you sure you want to delete this transaction?");
        ImageView icon_delete = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/trash.png")));
        icon_delete.setFitWidth(20);
        icon_delete.setFitHeight(20);
        dialog.setGraphic(icon_delete);
        dialog.setOnShown(event -> {
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/trash.png")));
        });

        ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(100);
        grid.getColumnConstraints().add(col1);

        grid.add(new Label("Amount: " + transaction.getAmount()), 0, 0);
        grid.add(new Label("Contractor Account: " + transaction.getContractor_account_number()), 0, 1);
        grid.add(new Label("Execute Date: " + transaction.getExecute_date()), 0, 2);
        grid.add(new Label("Description: " + transaction.getDescription()), 0, 3);

        StackPane root = new StackPane(grid);
        root.setPrefSize(300, 100);
        root.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(root);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButtonType) {
                DB_ProceduralDestroyer dbProceduralDestroyer = new DB_ProceduralDestroyer();
                try {
                    dbProceduralDestroyer.transactionDestroy(transaction.getTransaction_id());
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Transaction deleted successfully.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete transaction: " + e.getMessage());
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