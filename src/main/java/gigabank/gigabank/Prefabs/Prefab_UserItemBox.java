package gigabank.gigabank.Prefabs;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.ControllerLoginPanel;
import gigabank.gigabank.Entities.DB_ClassicListBuilder;
import gigabank.gigabank.Entities.EntityAccount;
import gigabank.gigabank.Entities.EntityCurrency;
import gigabank.gigabank.Entities.EntityUser;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.SQLException;
import java.util.ArrayList;

public class Prefab_UserItemBox {

    public Prefab_UserItemBox() {
    }

    public void show(VBox usersPane, ArrayList<EntityUser> users_x) {

        for (EntityUser user : users_x) {
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
            Text idText = new Text("ID " + user.getUser_id());
            idText.getStyleClass().add("itemText");
            Text nameText = new Text(user.getName() + " " + user.getSurname());
            nameText.getStyleClass().add("itemText");
            ImageView icon_arrow_down = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/chevron_down.png")));
            icon_arrow_down.setFitWidth(20);
            icon_arrow_down.setFitHeight(20);
            ImageView icon_arrow_up = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/chevron_up.png")));
            icon_arrow_up.setFitWidth(20);
            icon_arrow_up.setFitHeight(20);
            Button expandButton = new Button();
            expandButton.setGraphic(icon_arrow_down);
            itemBox.add(idText, 0, 0);
            itemBox.add(nameText, 1, 0);
            itemBox.add(expandButton, 2, 0);
            VBox moreItemsBox = new VBox();
            expandButton.setOnAction(event -> {
                if (expanded[0]) {
                    expanded[0] = false;
                    expandButton.setGraphic(icon_arrow_down);
                    moreItemsBox.getChildren().clear();
                } else {
                    expanded[0] = true;
                    expandButton.setGraphic(icon_arrow_up);
                    String query = "SELECT * FROM accounts WHERE accounts.user_id = " + user.getUser_id();
                    ArrayList<EntityAccount> accounts_x = null;
                    try {
                        accounts_x = DB_ClassicListBuilder.accountListBuild(query);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    Prefab_AccountBox prefabAccountBox = new Prefab_AccountBox();
                    prefabAccountBox.show(moreItemsBox, accounts_x);
                }
            });

            VBox.setMargin(expandedItemBox, new Insets(10, 0, 10, 0));
            expandedItemBox.getChildren().addAll(itemBox, moreItemsBox);
            usersPane.getChildren().add(expandedItemBox);
        }
    }
}
