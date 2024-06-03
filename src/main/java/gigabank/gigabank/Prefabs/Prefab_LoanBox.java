package gigabank.gigabank.Prefabs;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.Entities.DB_ClassicListBuilder;
import gigabank.gigabank.Entities.EntityCurrency;
import gigabank.gigabank.Entities.EntityLoan;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
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

            ImageView icon_create = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/plus.png")));
            icon_create.setFitHeight(20); icon_create.setFitWidth(20);
            Button buttonCreateLoan = new Button();
            buttonCreateLoan.setGraphic(icon_create);
            buttonCreateLoan.setPrefWidth(999);
            loansBox.getChildren().add(buttonCreateLoan);

            Text loanIdText = new Text("   ID: " + loan.getLoan_id());
            loanIdText.setFont(new Font(9));
            Text loanTitleText = new Text(loan.getTitle());
            Text loanAmountText = new Text(String.valueOf(loan.getAmount()));
            Text loanCurrencyText = new Text("unknown");

            Text loanInterestText = new Text("Interest: " + loan.getInterest_percentage()+"%");
            Text loanDescriptionText = new Text("Description: " + loan.getDescription());
            Text loanCreateDateText = new Text("Created at: " + loan.getCreate_date());
            Text loanDeadLineText = new Text("Deadline: " + loan.getDeadline());
            Text loanstatusText = new Text("Status: " + loan.getStatus());


            try {
                String query = "SELECT * FROM currencies WHERE currencies.currency_id = " + loan.getCurrency_id();
                System.out.println(query);
                EntityCurrency currency = DB_ClassicListBuilder.currencyBuild(query);
                loanCurrencyText.setText(currency.getCurrency());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

            GridPane loanBox = new GridPane();
            loanBox.add(loanIdText, 0, 0);
            loanBox.add(loanTitleText, 1, 0);
            loanBox.add(loanAmountText, 2, 0);
            loanBox.add(loanCurrencyText, 3, 0);
            loanBox.getStyleClass().add("particularBox");
            VBox.setMargin(expandedBox, new Insets(3, 3, 3, 3));

            moreItemsBox.getChildren().addAll(loanInterestText, loanDescriptionText, loanCreateDateText, loanDeadLineText, loanstatusText);

            expandedBox.getChildren().addAll(loanBox, moreItemsBox);
            loansBox.getChildren().add(expandedBox);
        }
    }
}
