package gigabank.gigabank.Prefabs;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ClassicListBuilder;
import gigabank.gigabank.Entities.EntityAccount;
import gigabank.gigabank.Entities.EntityCurrency;
import gigabank.gigabank.Entities.EntityDeposit;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
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

public class Prefab_DepositBox {


    public void show(VBox depositsBox, ArrayList<EntityDeposit> deposits_x) {

        for (EntityDeposit deposit : deposits_x) {
            VBox expandedBox = new VBox();
            VBox moreItemsBox = new VBox();
            moreItemsBox.setPrefWidth(999);
            moreItemsBox.getStyleClass().add("otherBox");

            ImageView icon_create = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/plus.png")));
            icon_create.setFitHeight(20); icon_create.setFitWidth(20);
            Button buttonCreateDeposit = new Button();
            buttonCreateDeposit.setGraphic(icon_create);
            buttonCreateDeposit.setPrefWidth(999);
            depositsBox.getChildren().add(buttonCreateDeposit);

            Text depositIdText = new Text("   ID: " + deposit.getDeposit_id());
            depositIdText.setFont(new Font(9));
            Text depositTitleText = new Text(deposit.getTitle());
            Text depositAmountText = new Text(String.valueOf(deposit.getAmount()));
            Text depositCurrencyText = new Text("unknown");

            Text depositInterestText = new Text("Interest: " + deposit.getInterest_percentage()+"%");
            Text depositDescriptionText = new Text("Description: " + deposit.getDescription());
            Text depositCreateDateText = new Text("Created at: " + deposit.getCreate_date());
            Text depositDeadLineText = new Text("Deadline: " + deposit.getDeadline());
            Text depositStatusText = new Text("Status: " + deposit.getStatus());


            try {
                String query = "SELECT * FROM currencies WHERE currencies.currency_id = " + deposit.getCurrency_id();
                System.out.println(query);
                EntityCurrency currency = DB_ClassicListBuilder.currencyBuild(query);
                depositCurrencyText.setText(currency.getCurrency());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            GridPane depositBox = new GridPane();
            depositBox.add(depositIdText, 0, 0);
            depositBox.add(depositTitleText, 1, 0);
            depositBox.add(depositAmountText, 2, 0);
            depositBox.add(depositCurrencyText, 3, 0);
            depositBox.getStyleClass().add("particularBox");
            VBox.setMargin(expandedBox, new Insets(3, 3, 3, 3));

            moreItemsBox.getChildren().addAll(depositInterestText, depositDescriptionText, depositCreateDateText, depositDeadLineText, depositStatusText);

            expandedBox.getChildren().addAll(depositBox, moreItemsBox);
            depositsBox.getChildren().add(expandedBox);
        }
    }
}
