package gigabank.gigabank.Prefabs.Dialogues;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralUpdater;
import gigabank.gigabank.Entities.EntityCurrency;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.Optional;

public class Prefab_DialogueEditCurrency {
    Dialog<Pair<String, Double>> dialog;
    EntityCurrency currency;

    public Prefab_DialogueEditCurrency(EntityCurrency currency) {
        this.currency = currency;

        dialog = new Dialog<>();
        dialog.setTitle("Edit Currency Data");
        dialog.setHeaderText("Edit Currency Data");
        ImageView icon_edit = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/edit.png")));
        icon_edit.setFitWidth(20);
        icon_edit.setFitHeight(20);
        dialog.setGraphic(icon_edit);
        dialog.setOnShown(event -> {
            Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/edit.png")));
        });

        ButtonType loginButtonType = new ButtonType("Commit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        col1.setPercentWidth(70);
        col2.setPercentWidth(30);
        grid.getColumnConstraints().addAll(col1, col2);

        GridPane gridInner = new GridPane();
        ColumnConstraints col3 = new ColumnConstraints();
        ColumnConstraints col4 = new ColumnConstraints();
        col3.setPercentWidth(50);
        col4.setPercentWidth(50);
        gridInner.getColumnConstraints().addAll(col3, col4);

        TextField currencyName = new TextField(currency.getCurrency());
        currencyName.setPromptText("Enter a currency name");
        TextField currencyConversion1 = new TextField("" + (int) currency.getUsd_conversion());
        currencyConversion1.setPromptText("Conversion dollars (USD)");
        TextField currencyConversion2 = new TextField();
        currencyConversion2.setPromptText("Conversion pennies (USD)");

        double pennies = (currency.getUsd_conversion() - (int) currency.getUsd_conversion()) * 100;
        currencyConversion2.setText(String.format("%02d", (int) pennies));

        gridInner.add(currencyConversion1, 0, 0);
        gridInner.add(currencyConversion2, 1, 0);

        grid.add(new Label("Name: "), 0, 0);
        grid.add(currencyName, 1, 0);
        grid.add(new Label("Conversion:"), 0, 1);
        grid.add(gridInner, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        currencyName.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || currencyConversion1.getText().trim().isEmpty());
        });
        currencyConversion1.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || currencyName.getText().trim().isEmpty());
        });
        currencyConversion2.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() || currencyName.getText().trim().isEmpty());
        });

        currencyConversion1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    currencyConversion1.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        currencyConversion2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    currencyConversion2.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

        StackPane root = new StackPane(grid);
        root.setPrefSize(300, 100);
        root.setAlignment(Pos.CENTER);
        dialog.getDialogPane().setContent(root);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                System.out.println(currencyName.getText());
                double currConv1 = Double.parseDouble(currencyConversion1.getText());
                double currConv2 = Double.parseDouble(currencyConversion2.getText()) / 100;
                double finalConversion = currConv1 + currConv2;
                System.out.println(finalConversion);

                DB_ProceduralUpdater dbProceduralUpdater = new DB_ProceduralUpdater();
                try {
                    dbProceduralUpdater.currencyUpdate(currencyName.getText(), finalConversion, currency.getCurrency_id());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            return null;
        });
    }

    public void showDialog() {
        Optional<Pair<String, Double>> result = dialog.showAndWait();
    }
}