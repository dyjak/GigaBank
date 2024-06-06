package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralDestroyer;
import gigabank.gigabank.Entities.EntityUser;
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

public class Prefab_DialogueDestroyUser {
    Dialog<Void> dialog;
    EntityUser user;

    public Prefab_DialogueDestroyUser(EntityUser user) {
        this.user = user;

        dialog = new Dialog<>();
        dialog.setTitle("Delete User");
        dialog.setHeaderText("Are you sure you want to delete this user?");
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

        grid.add(new Label("User: " + user.getName() + " " + user.getSurname()), 0, 0);
        grid.add(new Label("Login: " + user.getLogin()), 0, 1);

        StackPane root = new StackPane(grid);
        root.setPrefSize(300, 100);
        root.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(root);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == deleteButtonType) {
                System.out.println("Deleting user: " + user.getName() + " " + user.getSurname());

                DB_ProceduralDestroyer dbProceduralDestroyer = new DB_ProceduralDestroyer();
                try {
                    dbProceduralDestroyer.userDestroy(user.getUser_id());
                    showAlert(Alert.AlertType.INFORMATION, "Success", "User deleted successfully.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete user: " + e.getMessage());
                }
            }
            return null;
        });
    }

    public void showDialog() {
        Optional<Void> result = dialog.showAndWait();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}