package gigabank.gigabank.Prefabs;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ProceduralUpdater;
import gigabank.gigabank.Entities.EntityCurrency;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.sql.SQLException;
import java.util.Optional;

public class Prefab_DialogueEdit {
    Dialog<Pair<String, Double>> dialog;
    EntityCurrency currency;

    public Prefab_DialogueEdit(EntityCurrency currency)
    {
        this.currency = currency;

        dialog = new Dialog<>();
        dialog.setTitle("Edit Currency Data");
        dialog.setHeaderText("Edit Currency Data");

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
        col1.setPercentWidth(15);
        col2.setPercentWidth(15);
        grid.getColumnConstraints().addAll(col3, col4);

        TextField currencyName = new TextField(currency.getCurrency());
        currencyName.setPromptText("Enter a currency name");
        TextField currencyConversion1 = new TextField(""+(int) currency.getUsd_conversion());
        currencyConversion1.setPromptText("Conversion dollars (USD)");
        TextField currencyConversion2 = new TextField();
        currencyConversion2.setPromptText("Conversion pennies (USD)");

        double pennies = currency.getUsd_conversion() - (int)currency.getUsd_conversion();
        pennies *= Math.pow(10, currencyConversion2.getText().length()+1);
        if(pennies<10) pennies *= 10;
        currencyConversion2.setText(""+(int)pennies);

        gridInner.add(currencyConversion1,0,0);
        gridInner.add(currencyConversion2,1,0);

        grid.add(new Label("Name: "), 0, 0);
        grid.add(currencyName, 1, 0);
        grid.add(new Label("Conversion:"), 0, 1);
        grid.add(gridInner, 1, 1);

        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        currencyName.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() && currencyConversion1.getText().trim().isEmpty());
        });
        currencyConversion1.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() && currencyName.getText().trim().isEmpty());
        });
        currencyConversion2.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty() && currencyName.getText().trim().isEmpty());
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

        dialog.getDialogPane().setContent(grid);


        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                System.out.println(currencyName.getText());
                double currConv1 = Double.parseDouble(currencyConversion1.getText());
                double currConv2 = Double.parseDouble(currencyConversion2.getText());
                if(currConv2!=0) currConv2 /= currConv2 * Math.pow(10, currencyConversion2.getText().length());
                double finalConversion = currConv1 + currConv2;
                System.out.println(finalConversion);

                DB_ProceduralUpdater dbProceduralUpdater = new DB_ProceduralUpdater();
                try {
                    dbProceduralUpdater.currencyUpdate(currencyName.getText(), finalConversion, currency.getCurrency_id());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
//                ControllerAdministratorMainPanel controllerMainPanel = new ControllerAdministratorMainPanel();
//                controllerMainPanel.loadResultsCurrencies();
            }
            return null;
        });

    }

    public void showDialog()
    {
        Optional<Pair<String, Double>> result = dialog.showAndWait();
    }

}
