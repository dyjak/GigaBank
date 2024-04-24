package gigabank.gigabank.Prefabs;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ClassicListBuilder;
import gigabank.gigabank.Entities.EntityAccount;
import gigabank.gigabank.Entities.EntityCurrency;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

public class Prefab_AccountBox {


    public void show(VBox userBox, ArrayList<EntityAccount> accounts_x) {
        for (EntityAccount account : accounts_x) {

            VBox expandedBox = new VBox();
            VBox moreItemsBox = new VBox();
            moreItemsBox.setPrefWidth(999);
            moreItemsBox.getStyleClass().add("otherBox");
            Text accountIdText = new Text("   ID: " + account.getAccount_id());
            accountIdText.setFont(new Font(11));
            Text accountNumberText = new Text(account.getAccount_number());
            Text accountBalanceText = new Text(String.valueOf(account.getBalance()));
            Text accountCurrencyText = new Text("unknown");
            ImageView icon_arrow_down = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/chevron_down.png")));
            icon_arrow_down.setFitWidth(20);
            icon_arrow_down.setFitHeight(20);
            ImageView icon_arrow_up = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/chevron_up.png")));
            icon_arrow_up.setFitWidth(20);
            icon_arrow_up.setFitHeight(20);
            Button expandButton = new Button();
            expandButton.setGraphic(icon_arrow_down);
            boolean[] expanded ={false};
                expandButton.setOnAction(e -> {
                    if(!expanded[0])
                    {
                        expanded[0] = true;
                        expandButton.setGraphic(icon_arrow_up);
                        TitledPane accountInfoPane = new TitledPane(); ;
                        accountInfoPane.setText("Account Info"); accountInfoPane.setExpanded(false);
                        TitledPane accountTransactionsPane = new TitledPane();
                        accountTransactionsPane.setText("Transactions"); accountTransactionsPane.setExpanded(false);
                        TitledPane accountDepositsPane = new TitledPane();
                        accountDepositsPane.setText("Deposits"); accountDepositsPane.setExpanded(false);
                        TitledPane accountLoansPane = new TitledPane();
                        accountLoansPane.setText("Loans"); accountLoansPane.setExpanded(false);
                        moreItemsBox.getChildren().addAll(accountInfoPane, accountTransactionsPane, accountDepositsPane, accountLoansPane);
                    }
                    else {
                        expanded[0] = false;
                        expandButton.setGraphic(icon_arrow_down);
                        moreItemsBox.getChildren().clear();
                    }

                });
            try {
                String query = "SELECT * FROM currencies WHERE currencies.currency_id = " + account.getCurrency_id();
                System.out.println(query);
                EntityCurrency currency = DB_ClassicListBuilder.currencyBuild(query);
                accountCurrencyText.setText(currency.getCurrency());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            ColumnConstraints col_id = new ColumnConstraints();
            col_id.setPercentWidth(15); col_id.setFillWidth(false);
            ColumnConstraints col_number = new ColumnConstraints();
            col_number.setPercentWidth(35); col_number.setFillWidth(false);
            ColumnConstraints col_balance = new ColumnConstraints();
            col_balance.setPercentWidth(20);    col_balance.setFillWidth(false);
            ColumnConstraints col_currency = new ColumnConstraints();
            col_currency.setPercentWidth(15);   col_currency.setFillWidth(false);
            ColumnConstraints col_button = new ColumnConstraints();
            col_button.setPercentWidth(15);   col_button.setFillWidth(false);
            GridPane accountBox = new GridPane();

            accountBox.getColumnConstraints().addAll(col_id, col_number, col_balance, col_currency, col_button);
            accountBox.add(accountIdText, 0, 0);
            accountBox.add(accountNumberText, 1, 0);
            accountBox.add(accountBalanceText, 2, 0);
            accountBox.add(accountCurrencyText, 3, 0);
            accountBox.add(expandButton, 4, 0);
            accountBox.getStyleClass().add("accountBox");
            VBox.setMargin(expandedBox, new Insets(3, 3, 3, 3));
            expandedBox.getChildren().addAll(accountBox, moreItemsBox);

            userBox.getChildren().add(expandedBox);
        }
    }
}
