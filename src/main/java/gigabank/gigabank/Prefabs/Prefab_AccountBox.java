package gigabank.gigabank.Prefabs;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ClassicListBuilder;
import gigabank.gigabank.Entities.EntityAccount;
import gigabank.gigabank.Entities.EntityCurrency;
import gigabank.gigabank.Prefabs.Dialogues.Prefab_DialogueCreateTransaction;
import gigabank.gigabank.Prefabs.Dialogues.Prefab_DialogueDestroyAccount;
import gigabank.gigabank.Prefabs.Dialogues.Prefab_DialogueEditAccount;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

public class Prefab_AccountBox {


    public void show(VBox userBox, ArrayList<EntityAccount> accounts_x) {

        for (EntityAccount account : accounts_x) {
            VBox expandedBox = new VBox();
            expandedBox.getStyleClass().add("expandedItemBox2");
            VBox moreItemsBox = new VBox();
            moreItemsBox.setPrefSize(999,0); moreItemsBox.setVisible(false);
            moreItemsBox.getStyleClass().add("contentBox");
            Text accountIdText = new Text("   ID: " + account.getAccount_id());
            accountIdText.setFont(new Font(9));
            Text accountNumberText = new Text(account.getAccount_number());
            //Text accountBalanceText = new Text(String.valueOf(account.getBalance()));
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
                        moreItemsBox.setVisible(true);

                        //CONTROLS
                        ImageView icon_destroy = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/trash.png")));
                        ImageView icon_edit = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/edit.png")));
                        icon_edit.setFitHeight(20); icon_edit.setFitWidth(20); icon_destroy.setFitWidth(20); icon_destroy.setFitHeight(20);
                        Button buttonModify = new Button();
                        Button buttonDestroy = new Button();
                        buttonModify.setGraphic(icon_edit);
                        buttonDestroy.setGraphic(icon_destroy);
                        HBox accountControlsBox = new HBox(buttonModify, buttonDestroy);
                        accountControlsBox.setPrefWidth(999); buttonModify.setPrefWidth(999); buttonDestroy.setPrefWidth(999);
                            buttonModify.setOnAction(event->{
                                Prefab_DialogueEditAccount prefabDialogueEditAccount = null;
                                prefabDialogueEditAccount = new Prefab_DialogueEditAccount(account);
                                prefabDialogueEditAccount.showDialog();
                            });
                            buttonDestroy.setOnAction(event->{
                                Prefab_DialogueDestroyAccount prefabDialogueDestroyAccount = new Prefab_DialogueDestroyAccount(account);
                                prefabDialogueDestroyAccount.showDialog();
                            });


                        //ACCOUNT-INFO
                        VBox accountInfoBox = new VBox();
                        Prefab_AccountInfoBox prefab_accountInfoBox = new Prefab_AccountInfoBox();
                        try {
                            prefab_accountInfoBox.show(accountInfoBox, account);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }

                        //TRANSACTIONS
                        TitledPane accountTransactionsPane = new TitledPane();
                        accountTransactionsPane.setText("Transactions"); accountTransactionsPane.setExpanded(false);
                        accountTransactionsPane.getStyleClass().add("contentBox");
                        VBox transactionsBox = new VBox();
                        ImageView icon_create = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/plus.png")));
                        icon_create.setFitHeight(20); icon_create.setFitWidth(20);
                        Button buttonCreateTransaction = new Button();
                        buttonCreateTransaction.setGraphic(icon_create);
                        buttonCreateTransaction.setPrefWidth(999);
                            buttonCreateTransaction.setOnAction(event->{
                                Prefab_DialogueCreateTransaction prefabDialogueCreateTransaction = new Prefab_DialogueCreateTransaction(account.getAccount_id());
                                prefabDialogueCreateTransaction.showDialog();
                            });
                        transactionsBox.getChildren().add(buttonCreateTransaction);
                        Prefab_TransactionsBox prefabTransactionsBox = new Prefab_TransactionsBox();
                        try {
                            prefabTransactionsBox.show(transactionsBox, account);
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                        accountTransactionsPane.setContent(transactionsBox);



                        moreItemsBox.getChildren().addAll(accountControlsBox, accountInfoBox, accountTransactionsPane);
                    }
                    else {
                        expanded[0] = false;
                        expandButton.setGraphic(icon_arrow_down);
                        moreItemsBox.getChildren().clear();
                        moreItemsBox.setVisible(false);
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

            VBox accountIdBox = new VBox(accountIdText); accountIdBox.setPrefWidth(99); accountIdBox.setAlignment(Pos.CENTER);
            VBox accountNumberBox = new VBox(accountNumberText); accountNumberBox.setPrefWidth(99); accountNumberBox.setAlignment(Pos.CENTER);
            VBox accountCurrencyBox = new VBox(accountCurrencyText); accountCurrencyBox.setPrefWidth(99); accountCurrencyBox.setAlignment(Pos.CENTER);
            GridPane accountBox = new GridPane();
            accountBox.add(accountIdBox, 0, 0);
            accountBox.add(accountNumberBox, 1, 0);
            accountBox.add(accountCurrencyBox, 2, 0);
            accountBox.add(expandButton, 3, 0);
            accountBox.getStyleClass().add("particularBox");
            VBox.setMargin(accountBox, new Insets(5, 5, 0, 5));
            VBox.setMargin(expandedBox, new Insets(5, 0, 5, 0));

            expandedBox.getChildren().addAll(accountBox, moreItemsBox);

            userBox.getChildren().add(expandedBox);
        }
    }
}
