package gigabank.gigabank.Prefabs;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ClassicListBuilder;
import gigabank.gigabank.Entities.EntityAccount;
import gigabank.gigabank.Entities.EntityCurrency;
import gigabank.gigabank.Entities.EntityTransaction;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

public class Prefab_TransactionsBox {

    public Prefab_TransactionsBox() {}

    public void show(VBox itemsBox, EntityAccount account) throws SQLException {

        ArrayList<EntityTransaction> transactions_x = DB_ClassicListBuilder.transactionListBuild("SELECT * FROM transactions WHERE account_id = "+account.getAccount_id());
        for(EntityTransaction transaction : transactions_x)
        {
//            ColumnConstraints colID = new ColumnConstraints(); colID.setPercentWidth(30);
//            ColumnConstraints colDate = new ColumnConstraints(); colDate.setPercentWidth(70);
            GridPane grid = new GridPane();
//            grid.getColumnConstraints().addAll(colID,colDate);
            grid.add(new Text(" ID: "+transaction.getTransaction_id()),0,0);
            grid.add(new Text(transaction.getExecute_date()), 1, 0);
            EntityCurrency currency = DB_ClassicListBuilder.currencyBuild("SELECT * FROM currencies WHERE currency_id = "+account.getCurrency_id());
            grid.add(new Text(transaction.getAmount() + " " + currency.getCurrency()), 0, 1);
            String contractorText = "";
            if(transaction.getAmount() > 0) contractorText += "FROM " + transaction.getContractor_account_number();
            else contractorText += "TO " + transaction.getContractor_account_number();
            grid.add(new Text(contractorText), 1, 1);
//            ColumnConstraints colDesc = new ColumnConstraints(); colDesc.setPercentWidth(33);
//            ColumnConstraints colButtonM = new ColumnConstraints(); colButtonM.setPercentWidth(33);
//            ColumnConstraints colButtonD = new ColumnConstraints(); colButtonD.setPercentWidth(33);
            GridPane grid2 = new GridPane();
//            grid2.getColumnConstraints().addAll(colDesc,colButtonM,colButtonD);
            ImageView icon_destroy = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/trash.png")));
            ImageView icon_edit = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/edit.png")));
            icon_edit.setFitHeight(20); icon_edit.setFitWidth(20); icon_destroy.setFitWidth(20); icon_destroy.setFitHeight(20);
            Button buttonModify = new Button();
            Button buttonDestroy = new Button();
            buttonModify.setGraphic(icon_edit);
            buttonDestroy.setGraphic(icon_destroy);
            grid2.add(new Text(transaction.getDescription()), 0, 0);
            grid2.add(buttonModify, 1, 0);
            grid2.add(buttonDestroy, 2, 0);
            VBox transactionBox = new VBox(grid,grid2);
            StackPane root = new StackPane(transactionBox);
            StackPane.setMargin(transactionBox, new Insets(10,0,10,0));
            transactionBox.getStyleClass().add("itemBox");
            itemsBox.getChildren().add(root);
        }

    }
}
