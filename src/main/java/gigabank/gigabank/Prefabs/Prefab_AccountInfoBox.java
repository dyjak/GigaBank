package gigabank.gigabank.Prefabs;

import gigabank.gigabank.Entities.DB_ProceduralListBuilder;
import gigabank.gigabank.Entities.EntityAccount;
import javafx.scene.layout.VBox;

import java.sql.SQLException;

public class Prefab_AccountInfoBox {

    public void show(VBox accountInfoBox, EntityAccount account) throws SQLException {
        DB_ProceduralListBuilder dbProceduralListBuilder = new DB_ProceduralListBuilder();
        String[] accountInfo = dbProceduralListBuilder.accountInfoBuild(account.getAccount_id());
        for(int i = 0; i < accountInfo.length; i++)
            System.out.println(accountInfo[i]);
    }
}
