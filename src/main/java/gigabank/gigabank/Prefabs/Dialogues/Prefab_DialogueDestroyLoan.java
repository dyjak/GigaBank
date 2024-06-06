package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralDestroyer;
import gigabank.gigabank.Entities.EntityLoan;
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

public class Prefab_DialogueDestroyLoan {
    Dialog<Void> dialog;
    EntityLoan loan;

    public Prefab_DialogueDestroyLoan(EntityLoan loan) {
        this.loan = loan;

        dialog = new Dialog<>();
        dialog.setTitle("Delete Loan");
        dialog.setHeaderText("Are you sure you want to delete this loan?");
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

        grid.add(new Label("Title: " + loan.getTitle()), 0, 0);
        grid.add(new Label("Amount: " + loan.getAmount()), 0, 1);
        grid.add(new Label("Currency ID: " + loan.getCurrency_id()), 0, 2);
        grid.add(new Label("Interest Percentage: " + loan.getInterest_percentage()), 0, 3);
        grid.add(new Label("Description: " + loan.getDescription()), 0, 4);
        grid.add(new Label("Create Date: " + loan.getCreate_date()), 0, 5);
        grid.add(new Label("Deadline: " + loan.getDeadline()), 0, 6);
        grid.add(new Label("Status: " + loan.getStatus()), 0, 7);

        StackPane root = new StackPane(grid);
        root.setPrefSize(400, 300);
        root.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(root);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButtonType) {
                System.out.println("Deleting loan with ID " + loan.getLoan_id());

                DB_ProceduralDestroyer dbProceduralDestroyer = new DB_ProceduralDestroyer();
                try {
                    dbProceduralDestroyer.deleteLoan(loan.getLoan_id());
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Loan deleted successfully.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete loan: " + e.getMessage());
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
