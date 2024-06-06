package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralDestroyer;
import gigabank.gigabank.Entities.EntityAccount;
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

public class Prefab_DialogueDestroyAccount {
    Dialog<Void> dialog;
    EntityAccount account;

    public Prefab_DialogueDestroyAccount(EntityAccount account) {
        this.account = account;

        dialog = new Dialog<>();
        dialog.setTitle("Delete Account");
        dialog.setHeaderText("Are you sure you want to delete this account?");
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

        grid.add(new Label("Owner ID: " + account.getUser_id()), 0, 0);
        grid.add(new Label("Account Number: " + account.getAccount_number()), 0, 1);

        StackPane root = new StackPane(grid);
        root.setPrefSize(300, 100);
        root.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(root);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButtonType) {
                System.out.println("Deleting account: " + account.getAccount_number());

                DB_ProceduralDestroyer dbProceduralDestroyer = new DB_ProceduralDestroyer();
                try {
                    dbProceduralDestroyer.accountDestroy(account.getAccount_id());
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText(null);
                    alert.setContentText("Account successfully deleted.");
                    alert.showAndWait();
                } catch (SQLException e) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Cannot delete account");
                    alert.setContentText("This account cannot be deleted due to associated records.");
                    alert.showAndWait();
                }
            }
            return null;
        });
    }

    public void showDialog() {
        Optional<Void> result = dialog.showAndWait();
    }
}