package gigabank.gigabank.Prefabs;

import gigabank.gigabank.Entities.DB_ProceduralListBuilder;
import gigabank.gigabank.Entities.EntityAccount;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.sql.SQLException;

public class Prefab_AccountInfoBox {

    public void show(VBox accountInfoBox, EntityAccount account) throws SQLException {
        DB_ProceduralListBuilder dbProceduralListBuilder = new DB_ProceduralListBuilder();
        String[] accountInfo = dbProceduralListBuilder.accountInfoBuild(account.getAccount_id());
        Text accountInfoLoginText = new Text (10, 20, "Login: " + accountInfo[0]);
        Text accountInfoPermissionText = new Text (10, 20, "Permission: " + accountInfo[1]);
        Text accountInfoAmountText = new Text (10, 20, "Amount: " + accountInfo[3] + " " + accountInfo[4]);
        Text accountInfoDepositCountText = new Text (10, 20, "Deposits: " + accountInfo[5]);
        Text accountInfoLoansCountText = new Text (10, 20, "Loans: " + accountInfo[6]);
        Text accountInfoDateText = new Text (10, 20, "Created at: " + accountInfo[7]);
        accountInfoBox.getChildren().add(accountInfoLoginText);
        accountInfoBox.getChildren().add(accountInfoPermissionText);
        accountInfoBox.getChildren().add(accountInfoAmountText);
        accountInfoBox.getChildren().add(accountInfoDepositCountText);
        accountInfoBox.getChildren().add(accountInfoLoansCountText);
        accountInfoBox.getChildren().add(accountInfoDateText);
//        for (int i = 0; i < accountInfo.length; i++) {
//            System.out.println(accountInfo[i]);
//        }

    }
}
