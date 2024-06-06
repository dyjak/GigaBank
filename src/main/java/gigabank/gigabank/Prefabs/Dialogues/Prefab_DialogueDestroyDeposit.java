package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralDestroyer;
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

import java.sql.SQLException;
import java.util.Optional;

public class Prefab_DialogueDestroyDeposit {
    Dialog<Void> dialog;
    EntityDeposit deposit;

    public Prefab_DialogueDestroyDeposit(EntityDeposit deposit) {
        this.deposit = deposit;

        dialog = new Dialog<>();
        dialog.setTitle("Delete Deposit");
        dialog.setHeaderText("Are you sure you want to delete this deposit?");
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

        grid.add(new Label("Title: " + deposit.getTitle()), 0, 0);
        grid.add(new Label("Amount: " + deposit.getAmount()), 0, 1);
        grid.add(new Label("Currency ID: " + deposit.getCurrency_id()), 0, 2);
        grid.add(new Label("Interest Percentage: " + deposit.getInterest_percentage()), 0, 3);
        grid.add(new Label("Description: " + deposit.getDescription()), 0, 4);
        grid.add(new Label("Create Date: " + deposit.getCreate_date()), 0, 5);
        grid.add(new Label("Deadline: " + deposit.getDeadline()), 0, 6);
        grid.add(new Label("Status: " + deposit.getStatus()), 0, 7);

        StackPane root = new StackPane(grid);
        root.setPrefSize(400, 300);
        root.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(root);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButtonType) {
                System.out.println("Deleting deposit with ID " + deposit.getDeposit_id());

                DB_ProceduralDestroyer dbProceduralDestroyer = new DB_ProceduralDestroyer();
                try {
                    dbProceduralDestroyer.deleteDeposit(deposit.getDeposit_id());
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Deposit deleted successfully.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete deposit: " + e.getMessage());
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