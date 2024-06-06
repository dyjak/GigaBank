package gigabank.gigabank.Prefabs;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ClassicListBuilder;
import gigabank.gigabank.Entities.EntityAccount;
import gigabank.gigabank.Entities.EntityCurrency;
import gigabank.gigabank.Entities.EntityDeposit;
import gigabank.gigabank.Prefabs.Dialogues.Prefab_DialogueDestroyCurrency;
import gigabank.gigabank.Prefabs.Dialogues.Prefab_DialogueDestroyDeposit;
import gigabank.gigabank.Prefabs.Dialogues.Prefab_DialogueEditCurrency;
import gigabank.gigabank.Prefabs.Dialogues.Prefab_DialogueEditDeposit;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

            Text depositIdText = new Text("   ID: " + deposit.getDeposit_id());
            depositIdText.setFont(new Font(13));
            Text depositTitleText = new Text(deposit.getTitle());
            Text depositAmountText = new Text(String.valueOf(deposit.getAmount()));
            Text depositCurrencyText = new Text("unknown");

            Text depositInterestText = new Text("Interest: " + deposit.getInterest_percentage()+"%");
            Text depositDescriptionText = new Text(deposit.getDescription());
            depositDescriptionText.setFont(new Font(13));
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

            VBox depositIdBox = new VBox(depositIdText); depositIdBox.setPrefWidth(100); depositIdBox.setAlignment(Pos.CENTER);
            VBox depositTitleBox = new VBox(depositTitleText); depositTitleBox.setPrefWidth(100); depositTitleBox.setAlignment(Pos.CENTER);
            VBox depositAmountBox = new VBox(depositAmountText); depositAmountBox.setPrefWidth(100); depositAmountBox.setAlignment(Pos.CENTER);
            VBox depositCurrencyBox = new VBox(depositCurrencyText); depositCurrencyBox.setPrefWidth(100); depositCurrencyBox.setAlignment(Pos.CENTER);
            VBox depositInterestBox = new VBox(depositInterestText); depositInterestBox.setPrefWidth(100); depositInterestBox.setAlignment(Pos.CENTER);
            VBox depositDescriptionBox = new VBox(depositDescriptionText); depositDescriptionBox.setPrefWidth(100); depositDescriptionBox.setAlignment(Pos.CENTER);
            GridPane depositBox = new GridPane();
            depositBox.add(depositIdBox, 0, 0);
            depositBox.add(depositTitleBox, 1, 0);
            depositBox.add(depositAmountBox, 2, 0);
            depositBox.add(depositCurrencyBox, 3, 0);
            depositBox.getStyleClass().add("expandedItemBox2");
            VBox.setMargin(expandedBox, new Insets(3, 3, 3, 3));

            ImageView icon_edit = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/edit.png")));
            icon_edit.setFitWidth(20);  icon_edit.setFitHeight(20);
            Button editButton = new Button(); editButton.setGraphic(icon_edit);
            editButton.setOnAction(event->{
                Prefab_DialogueEditDeposit prefabDialogueEditDeposit = new Prefab_DialogueEditDeposit(deposit);
                prefabDialogueEditDeposit.showDialog();
            });
            ImageView icon_destroy = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/trash.png")));
            icon_destroy.setFitWidth(20);  icon_destroy.setFitHeight(20);
            Button destroyButton = new Button(); destroyButton.setGraphic(icon_destroy);
            destroyButton.setOnAction(event->{
                Prefab_DialogueDestroyDeposit prefabDialogueDestroyDeposit = new Prefab_DialogueDestroyDeposit(deposit);
                prefabDialogueDestroyDeposit.showDialog();
            });
            HBox controlBox = new HBox(editButton, destroyButton);
            controlBox.setPrefWidth(999); editButton.setPrefWidth(999); destroyButton.setPrefWidth(999);

            moreItemsBox.getChildren().addAll(depositInterestText, depositDescriptionText, depositCreateDateText, depositDeadLineText, depositStatusText);

            expandedBox.getChildren().addAll(depositBox, moreItemsBox, controlBox);
            depositsBox.getChildren().add(expandedBox);
        }
    }
}
