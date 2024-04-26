package gigabank.gigabank.Prefabs;

import gigabank.gigabank.Entities.DB_ClassicListBuilder;
import gigabank.gigabank.Entities.EntityAccount;
import gigabank.gigabank.Entities.EntityCurrency;
import gigabank.gigabank.Entities.EntityTransaction;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

public class Prefab_TransactionsBox {

    public Prefab_TransactionsBox() {}

    public void show(VBox itemsBox, EntityAccount account) throws SQLException {

        ArrayList<EntityTransaction> transactions_x = DB_ClassicListBuilder.transactionListBuild("SELECT * FROM transactions WHERE account_id = "+account.getAccount_id());
        for(EntityTransaction transaction : transactions_x)
        {
            GridPane grid = new GridPane();
            grid.add(new Text(" ID: "+transaction.getTransaction_id()),0,0);
            grid.add(new Text(" At "+transaction.getExecute_date()), 1, 0);
            EntityCurrency currency = DB_ClassicListBuilder.currencyBuild("SELECT * FROM currencies WHERE currency_id = "+account.getCurrency_id());
            grid.add(new Text(transaction.getAmount() + " " + currency.getCurrency()), 0, 1);
            String contractorText = "";
            if(transaction.getAmount() > 0) contractorText += "FROM " + transaction.getContractor_account_number();
            else contractorText += "TO " + transaction.getContractor_account_number();
            grid.add(new Text(contractorText), 1, 1);
            HBox boxDescription = new HBox(new Text(transaction.getDescription()));
            VBox transactionBox = new VBox(grid,boxDescription);
            transactionBox.getStyleClass().add("itemBox");
            itemsBox.getChildren().add(transactionBox);
        }

    }
}
