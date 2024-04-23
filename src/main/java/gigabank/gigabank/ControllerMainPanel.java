package gigabank.gigabank;

import gigabank.gigabank.Entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerMainPanel implements Initializable {

    //SCENE SWITCH
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToLoginPanel(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxmls/login-panel-view.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }




    //user db controls
    static String searchText = "";
    static boolean sortAsc = false;
    static boolean sortDesc = false;




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        loadResultsUsers();
        loadResultsCurrencies();

    }


    @FXML
    private VBox usersPane;
    @FXML
    private VBox currenciesPane;
    @FXML
    private TextField searchField;


    public void updateResults(ActionEvent event)
    {
        System.out.println("updateResults");
        searchText = searchField.getText().toUpperCase();
        loadResultsUsers();
        loadResultsCurrencies();
    }

    public void loadResultsUsers()
    {
        usersPane.getChildren().clear();

        DB_ProceduralListBuilder listBuilder = new DB_ProceduralListBuilder();
        ArrayList<EntityUser> users_x = null;
        try
        {
            users_x = listBuilder.userListBuild(searchText);
        } catch (SQLException e) {
            throw new RuntimeException(e);}

        for(EntityUser user : users_x)
        {
            VBox expandedItemBox = new VBox();
            expandedItemBox.getStyleClass().add("expandedItemBox");
            boolean[] expanded = {false};
            GridPane itemBox = new GridPane();
            itemBox.getStyleClass().add("itemBox");
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(20);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(60);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(20);
            itemBox.getColumnConstraints().addAll(col1, col2, col3);
            Text idText = new Text("ID "+user.getUser_id());
            idText.getStyleClass().add("itemText");
            Text nameText = new Text(user.getName() + " " + user.getSurname());
            nameText.getStyleClass().add("itemText");
            Button expandButton = new Button("Expand");
            itemBox.add(idText,0,0);
            itemBox.add(nameText,1,0);
            itemBox.add(expandButton,2,0);
            VBox moreItemsBox = new VBox();
                expandButton.setOnAction(event -> {
                    if(expanded[0]){
                        expanded[0] = false;
                        expandButton.setText("Expand");
                        moreItemsBox.getChildren().clear();
                    }
                    else
                    {
                        expanded[0] = true;
                        expandButton.setText("Collapse");
                        String query="SELECT * FROM accounts WHERE accounts.user_id = " + user.getUser_id();
                        ArrayList<EntityAccount> accounts_x = null;
                        try {
                            accounts_x = DB_ClassicListBuilder.accountListBuild(query);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        for(EntityAccount account : accounts_x)
                        {

                            Text accountIdText = new Text("ID: " + account.getAccount_id());
                            Text accountNumberText = new Text(account.getAccount_number());
                            Text accountBalanceText = new Text(String.valueOf(account.getBalance()));
                            Text accountCurrencyText = new Text("unknown");
                            try {
                                String queryCurr = "SELECT * FROM currencies WHERE currencies.currency_id = " + account.getCurrency_id();
                                System.out.println(queryCurr);
                                EntityCurrency currency = DB_ClassicListBuilder.currencyBuild(queryCurr);
                                accountCurrencyText.setText(currency.getCurrency());
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                            ColumnConstraints col_id = new ColumnConstraints();
                            col_id.setPercentWidth(20);
                            col_id.setFillWidth(false);
                            ColumnConstraints col_number = new ColumnConstraints();
                            col_number.setPercentWidth(40);
                            col_number.setFillWidth(false);
                            ColumnConstraints col_balance = new ColumnConstraints();
                            col_balance.setPercentWidth(25);
                            col_balance.setFillWidth(false);
                            ColumnConstraints col_currency = new ColumnConstraints();
                            col_currency.setPercentWidth(15);
                            col_currency.setFillWidth(false);
                            GridPane accountBox = new GridPane();
                            accountBox.setMaxSize(expandedItemBox.getPrefWidth(), expandedItemBox.getPrefHeight());
                            accountBox.getColumnConstraints().addAll(col_id, col_number, col_balance, col_currency);
                            accountBox.add(accountIdText,0,0);
                            accountBox.add(accountNumberText,1,0);
                            accountBox.add(accountBalanceText,2,0);
                            accountBox.add(accountCurrencyText,3,0);
                            accountBox.getStyleClass().add("accountBox");
                            VBox.setMargin(accountBox, new Insets(3,7,3,7));

                            moreItemsBox.getChildren().add(accountBox);
                        }
                        //                        //SOME TRANSITION?
                        //                                FadeTransition ft = new FadeTransition(Duration.millis(3000));
                        //                                ft.setFromValue(0);
                        //                                ft.setToValue(1);
                        //                                ScaleTransition st = new ScaleTransition(Duration.millis(3000));
                        //                                st.setFromY(0);
                        //                                st.setToY(1);
                        //                                //st.setByY(1.5f);
                        //                                SequentialTransition seqT = new SequentialTransition (expandedItemBox, ft,st);
                        //                                seqT.play();
                    }
                });
            VBox.setMargin(expandedItemBox,new Insets(10,0,10,0));
            expandedItemBox.getChildren().addAll(itemBox, moreItemsBox);
            usersPane.getChildren().add(expandedItemBox);
        }
    }







    public void loadResultsCurrencies()
    {
        currenciesPane.getChildren().clear();

        DB_ProceduralListBuilder listBuilder = new DB_ProceduralListBuilder();
        ArrayList<EntityCurrency> currencies_x = null;
        try
        {
            currencies_x = listBuilder.currencyListBuild(searchText);
        } catch (SQLException e) {
            throw new RuntimeException(e);}

        for(EntityCurrency currency : currencies_x)
        {
            VBox expandedItemBox = new VBox();
            expandedItemBox.getStyleClass().add("expandedItemBox");
            boolean[] expanded = {false};
            GridPane itemBox = new GridPane();
            itemBox.getStyleClass().add("itemBox");
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(20);
            ColumnConstraints col2 = new ColumnConstraints();
            col2.setPercentWidth(60);
            ColumnConstraints col3 = new ColumnConstraints();
            col3.setPercentWidth(20);
            itemBox.getColumnConstraints().addAll(col1, col2, col3);
            Text idText = new Text("ID "+currency.getCurrency_id());
            idText.getStyleClass().add("itemText");
            Text nameText = new Text("1 " + currency.getCurrency() + " = " + currency.getUsd_conversion() + "$");
            nameText.getStyleClass().add("itemText");
            Button expandButton = new Button("Expand");
            itemBox.add(idText,0,0);
            itemBox.add(nameText,1,0);
            itemBox.add(expandButton,2,0);

            VBox.setMargin(expandedItemBox,new Insets(10,0,10,0));
            expandedItemBox.getChildren().addAll(itemBox);
            currenciesPane.getChildren().add(expandedItemBox);
        }
    }

}
