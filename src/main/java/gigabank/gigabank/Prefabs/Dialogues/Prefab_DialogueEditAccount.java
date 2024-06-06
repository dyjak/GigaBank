package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralUpdater;
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

public class Prefab_DialogueEditAccount {
    Dialog<EntityAccount> dialog;
    EntityAccount account;

    public Prefab_DialogueEditAccount(EntityAccount account) {
        this.account = account;

        dialog = new Dialog<>();
        dialog.setTitle("Edit Account");
        dialog.setHeaderText("Edit account details");
        ImageView icon_edit = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/edit.png")));
        icon_edit.setFitWidth(20);
        icon_edit.setFitHeight(20);
        dialog.setGraphic(icon_edit);
        dialog.setOnShown(event -> {
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/edit.png")));
        });

        ButtonType editButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(editButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();

        // Dodanie ograniczeń kolumn
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(40);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(60);
        grid.getColumnConstraints().addAll(col1, col2);

        TextField balanceField = new TextField(String.valueOf(account.getBalance()));
        TextField permissionField = new TextField(account.getPermission());

        grid.add(new Label("Balance:"), 0, 0);
        grid.add(balanceField, 1, 0);
        grid.add(new Label("Permission:"), 0, 1);
        grid.add(permissionField, 1, 1);

        StackPane root = new StackPane(grid);
        root.setPrefSize(300, 150);
        root.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(root);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == editButtonType) {
                double balance = Double.parseDouble(balanceField.getText());
                String permission = permissionField.getText();

                account.setBalance(balance);
                account.setPermission(permission);

                DB_ProceduralUpdater dbProceduralUpdater = new DB_ProceduralUpdater();
                try {
                    dbProceduralUpdater.accountUpdate(account);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Account updated successfully.");
                    return account;
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update account: " + e.getMessage());
                }
            }
            return null;
        });
    }

    public Optional<EntityAccount> showDialog() {
        return dialog.showAndWait();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}