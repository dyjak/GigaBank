package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.Entities.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class Prefab_DialogueCreateAccount {
    Dialog<Void> dialog;
    EntityUser user;

    public Prefab_DialogueCreateAccount(EntityUser user) throws SQLException {
        this.user = user;

        dialog = new Dialog<>();
        dialog.setTitle("Create Account");
        dialog.setHeaderText("Please provide account details");

        ButtonType createButtonType = new ButtonType("Create", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        Label currencyLabel = new Label("Currency:");
        ChoiceBox<String> currencyChoiceBox = new ChoiceBox<>();
        DB_ClassicListBuilder dbClassicListBuilder = new DB_ClassicListBuilder();
        ArrayList<EntityCurrency> currencies = dbClassicListBuilder.allCurrenciesBuild();
        for(EntityCurrency currency : currencies) {
            currencyChoiceBox.getItems().add(currency.getCurrency());
        }
        currencyChoiceBox.getSelectionModel().selectFirst();
        grid.add(currencyLabel, 0, 0);
        grid.add(currencyChoiceBox, 1, 0);

        Label balanceLabel = new Label("Initial Balance:");
        TextField balanceTextField = new TextField();
        grid.add(balanceLabel, 0, 1);
        grid.add(balanceTextField, 1, 1);

        Label accountNumberLabel = new Label("Account Number:");
        TextField accountNumberTextField = new TextField();
        grid.add(accountNumberLabel, 0, 2);
        grid.add(accountNumberTextField, 1, 2);

        Label permissionLabel = new Label("Permission:");
        TextField permissionTextField = new TextField();
        grid.add(permissionLabel, 0, 3);
        grid.add(permissionTextField, 1, 3);

        StackPane root = new StackPane(grid);
        root.setPrefSize(300, 200);
        root.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(root);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                try {
                    double initialBalance = Double.parseDouble(balanceTextField.getText());
                    EntityCurrency currency = dbClassicListBuilder.currencyBuild("SELECT * FROM currencies WHERE currency LIKE '"+currencyChoiceBox.getValue()+"'");
                    String accountNumber = accountNumberTextField.getText();
                    String permission = permissionTextField.getText();
                    createAccount(user, currency, initialBalance, accountNumber, permission);
                } catch (NumberFormatException e) {
                    showAlert("Error", "Invalid balance value");
                } catch (SQLException e) {
                    showAlert("Error", "Database error: " + e.getMessage());
                    System.out.println(e.getMessage());
                }
            }
            return null;
        });
    }

    private void createAccount(EntityUser user, EntityCurrency currency, double initialBalance, String accountNumber, String permission) throws SQLException {
        DB_ProceduralCreator dbProceduralCreator = new DB_ProceduralCreator();
        dbProceduralCreator.accountCreate(user.getUser_id(), currency.getCurrency_id(), initialBalance, accountNumber, permission);
        showAlert("Success", "Account created successfully.");
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void showDialog() {
        Optional<Void> result = dialog.showAndWait();
    }
}