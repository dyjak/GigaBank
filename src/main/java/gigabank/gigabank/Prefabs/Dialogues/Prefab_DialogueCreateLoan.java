package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralCreator;
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

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class Prefab_DialogueCreateLoan {
    Dialog<Void> dialog;

    public Prefab_DialogueCreateLoan(int userId) {
        dialog = new Dialog<>();
        dialog.setTitle("Create New Loan");
        dialog.setHeaderText("Create New Loan");
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

        TextField titleField = new TextField();
        titleField.setPromptText("Title");
        TextField amountField = new TextField();
        amountField.setPromptText("Amount");
        TextField currencyIdField = new TextField();
        currencyIdField.setPromptText("Currency ID");
        TextField interestPercentageField = new TextField();
        interestPercentageField.setPromptText("Interest Percentage");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description");
        DatePicker createDateField = new DatePicker(LocalDate.now());
        createDateField.setPromptText("Create Date");
        DatePicker deadlineField = new DatePicker();
        deadlineField.setPromptText("Deadline");
        TextField statusField = new TextField();
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

        Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
        createButton.setDisable(true);

        amountField.textProperty().addListener((observable, oldValue, newValue) -> {
            createButton.setDisable(newValue.trim().isEmpty());
        });

        StackPane root = new StackPane(grid);
        root.setPrefSize(400, 400);
        root.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(root);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                String title = titleField.getText();
                double amount = Double.parseDouble(amountField.getText());
                int currencyId = Integer.parseInt(currencyIdField.getText());
                double interestPercentage = Double.parseDouble(interestPercentageField.getText());
                String description = descriptionArea.getText();
                LocalDate createDate = createDateField.getValue();
                LocalDate deadline = deadlineField.getValue();
                double status = Double.parseDouble(statusField.getText());

                System.out.println("Creating new loan: " + title);

                DB_ProceduralCreator dbProceduralCreator = new DB_ProceduralCreator();
                try {
                    dbProceduralCreator.loanCreate(title, amount, currencyId, interestPercentage, description, userId, Date.valueOf(createDate), Date.valueOf(deadline), status);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Loan successfully created.");
                } catch (SQLException e) {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to create loan: " + e.getMessage());
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
