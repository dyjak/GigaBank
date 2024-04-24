package gigabank.gigabank.Prefabs;

import gigabank.gigabank.ControllerMainPanel;
import gigabank.gigabank.Entities.EntityCurrency;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Path;
import javafx.scene.text.Text;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class Prefab_CurrencyItemBox {

    public Prefab_CurrencyItemBox(){}

    public void show(VBox currenciesPane, ArrayList<EntityCurrency> currencies_x)
    {
        for(EntityCurrency currency : currencies_x)
        {
            VBox expandedItemBox = new VBox();
            expandedItemBox.getStyleClass().add("expandedItemBox");
            boolean[] expanded = {false};
            GridPane itemBox = new GridPane();
            itemBox.getStyleClass().add("itemBox");
            ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(20);
            ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(50);
            ColumnConstraints col3 = new ColumnConstraints(); col3.setPercentWidth(15);
            ColumnConstraints col4 = new ColumnConstraints(); col4.setPercentWidth(15);
            itemBox.getColumnConstraints().addAll(col1, col2, col3, col4);

            Text idText = new Text("ID "+currency.getCurrency_id());
            idText.getStyleClass().add("itemText");
            Text nameText = new Text("1 " + currency.getCurrency() + " = " + currency.getUsd_conversion() + "$");
            nameText.getStyleClass().add("itemText");

            ImageView icon_edit = new ImageView(new Image(ControllerMainPanel.class.getResourceAsStream("canvas/icons/edit.png")));
            icon_edit.setFitWidth(20);  icon_edit.setFitHeight(20);
            Button editButton = new Button(); editButton.setGraphic(icon_edit);
            editButton.setOnAction(event->{
                Prefab_DialogueEdit prefabDialogueEdit = new Prefab_DialogueEdit();
                prefabDialogueEdit.showDialog();
            });

            ImageView icon_destroy = new ImageView(new Image(ControllerMainPanel.class.getResourceAsStream("canvas/icons/trash.png")));
            icon_destroy.setFitWidth(20);  icon_destroy.setFitHeight(20);
            Button destroyButton = new Button(); destroyButton.setGraphic(icon_destroy);

            itemBox.add(idText,0,0);
            itemBox.add(nameText,1,0);
            itemBox.add(editButton,2,0);
            itemBox.add(destroyButton,3,0);

            VBox.setMargin(expandedItemBox,new Insets(10,0,10,0));
            expandedItemBox.getChildren().addAll(itemBox);
            currenciesPane.getChildren().add(expandedItemBox);
        }
    }
}
