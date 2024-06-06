package gigabank.gigabank.Prefabs;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ClassicListBuilder;
import gigabank.gigabank.Entities.EntityLoan;
import gigabank.gigabank.Entities.EntityCurrency;
import gigabank.gigabank.Prefabs.Dialogues.Prefab_DialogueDestroyLoan;
import gigabank.gigabank.Prefabs.Dialogues.Prefab_DialogueEditLoan;
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

public class Prefab_LoanBox {


    public void show(VBox loansBox, ArrayList<EntityLoan> loans_x) {

        for (EntityLoan loan : loans_x) {
            VBox expandedBox = new VBox();
            VBox moreItemsBox = new VBox();
            moreItemsBox.setPrefWidth(999);
            moreItemsBox.getStyleClass().add("otherBox");

            Text loanIdText = new Text("   ID: " + loan.getLoan_id());
            loanIdText.setFont(new Font(9));
            Text loanTitleText = new Text(loan.getTitle());
            Text loanAmountText = new Text(String.valueOf(loan.getAmount()));
            Text loanCurrencyText = new Text("unknown");

            Text loanInterestText = new Text("Interest: " + loan.getInterest_percentage()+"%");
            Text loanDescriptionText = new Text(loan.getDescription());
            loanDescriptionText.setFont(new Font(13));
            Text loanCreateDateText = new Text("Created at: " + loan.getCreate_date());
            Text loanDeadLineText = new Text("Deadline: " + loan.getDeadline());
            Text loanStatusText = new Text("Status: " + loan.getStatus());


            try {
                String query = "SELECT * FROM currencies WHERE currencies.currency_id = " + loan.getCurrency_id();
                System.out.println(query);
                EntityCurrency currency = DB_ClassicListBuilder.currencyBuild(query);
                loanCurrencyText.setText(currency.getCurrency());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            VBox loanIdBox = new VBox(loanIdText); loanIdBox.setPrefWidth(100); loanIdBox.setAlignment(Pos.CENTER);
            VBox loanTitleBox = new VBox(loanTitleText); loanTitleBox.setPrefWidth(100); loanTitleBox.setAlignment(Pos.CENTER);
            VBox loanAmountBox = new VBox(loanAmountText); loanAmountBox.setPrefWidth(100); loanAmountBox.setAlignment(Pos.CENTER);
            VBox loanCurrencyBox = new VBox(loanCurrencyText); loanCurrencyBox.setPrefWidth(100); loanCurrencyBox.setAlignment(Pos.CENTER);
            GridPane loanBox = new GridPane();
            loanBox.add(loanIdBox, 0, 0);
            loanBox.add(loanTitleBox, 1, 0);
            loanBox.add(loanAmountBox, 2, 0);
            loanBox.add(loanCurrencyBox, 3, 0);
            loanBox.getStyleClass().add("expandedItemBox2");
            VBox.setMargin(expandedBox, new Insets(3, 3, 3, 3));

            ImageView icon_edit = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/edit.png")));
            icon_edit.setFitWidth(20);  icon_edit.setFitHeight(20);
            Button editButton = new Button(); editButton.setGraphic(icon_edit);
            editButton.setOnAction(event->{
                Prefab_DialogueEditLoan prefabDialogueEditLoan = new Prefab_DialogueEditLoan(loan);
                prefabDialogueEditLoan.showDialog();
            });
            ImageView icon_destroy = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/trash.png")));
            icon_destroy.setFitWidth(20);  icon_destroy.setFitHeight(20);
            Button destroyButton = new Button(); destroyButton.setGraphic(icon_destroy);
            destroyButton.setOnAction(event->{
                Prefab_DialogueDestroyLoan prefabDialogueDestroyLoan = new Prefab_DialogueDestroyLoan(loan);
                prefabDialogueDestroyLoan.showDialog();
            });
            HBox controlBox = new HBox(editButton, destroyButton);
            controlBox.setPrefWidth(999); editButton.setPrefWidth(999); destroyButton.setPrefWidth(999);

            moreItemsBox.getChildren().addAll(loanInterestText, loanDescriptionText, loanCreateDateText, loanDeadLineText, loanStatusText);

            expandedBox.getChildren().addAll(loanBox, moreItemsBox, controlBox);
            loansBox.getChildren().add(expandedBox);
        }
    }
}