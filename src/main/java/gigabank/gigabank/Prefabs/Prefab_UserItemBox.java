package gigabank.gigabank.Prefabs;

import gigabank.gigabank.ControllerAdministratorMainPanel;
import gigabank.gigabank.ControllerLoginPanel;
import gigabank.gigabank.Entities.*;
import gigabank.gigabank.Prefabs.Dialogues.*;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
//            HBox itemBox = new HBox();
            VBox moreItemsBox = new VBox();
            expandButton.setOnAction(event -> {
                if (expanded[0]) {
                    expanded[0] = false;
                    expandButton.setGraphic(icon_arrow_down);
                    moreItemsBox.getChildren().clear();
                } else {
                    expanded[0] = true;
                    expandButton.setGraphic(icon_arrow_up);

                    //CONTROLS
                    ImageView icon_edit = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/edit.png")));
                    icon_edit.setFitWidth(20);    icon_edit.setFitHeight(20);
                    Button editButton = new Button();   editButton.setPrefWidth(999); editButton.setGraphic(icon_edit);
                    editButton.setOnAction(event2-> {
                        Prefab_DialogueEditUser prefabDialogueEditUser = new Prefab_DialogueEditUser(user);
                        prefabDialogueEditUser.showDialog();
                    });

                    ImageView icon_destroy = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/trash.png")));
                    icon_destroy.setFitWidth(20);    icon_destroy.setFitHeight(20);
                    Button destroyButton = new Button();   destroyButton.setPrefWidth(999); destroyButton.setGraphic(icon_destroy);
                    destroyButton.setOnAction(event2-> {
                        Prefab_DialogueDestroyUser prefabDialogueDestroyUser = new Prefab_DialogueDestroyUser(user);
                        prefabDialogueDestroyUser.showDialog();
                    });
                    HBox controlsBox = new HBox(editButton, destroyButton);
                    controlsBox.getStyleClass().add("expandedItemBox2");
                    moreItemsBox.getChildren().add(controlsBox);


                    //ACCOUNTS
                    TitledPane accountsPane = new TitledPane();
                    accountsPane.setText("Accounts"); accountsPane.setExpanded(false);
                    moreItemsBox.getChildren().addAll(accountsPane);
                    String query1 = "SELECT * FROM accounts WHERE accounts.user_id = " + user.getUser_id();
                    ArrayList<EntityAccount> accounts_x = null;
                    try {
                        accounts_x = DB_ClassicListBuilder.accountListBuild(query1);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    ImageView icon_create = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/plus.png")));
                    ImageView icon_create2 = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/plus.png")));
                    ImageView icon_create3 = new ImageView(new Image(ControllerAdministratorMainPanel.class.getResourceAsStream("canvas/icons/plus.png")));
                    icon_create.setFitHeight(20); icon_create.setFitWidth(20); icon_create2.setFitWidth(20); icon_create2.setFitHeight(20); icon_create3.setFitWidth(20); icon_create3.setFitHeight(20);
                    Button buttonCreateAccount = new Button();
                    buttonCreateAccount.setGraphic(icon_create);
                    buttonCreateAccount.setPrefWidth(999);
                    VBox accountsBox = new VBox(buttonCreateAccount);
                        buttonCreateAccount.setOnAction(e->{
                            Prefab_DialogueCreateAccount prefabDialogueCreateAccount = null;
                            try {
                                prefabDialogueCreateAccount = new Prefab_DialogueCreateAccount(user);
                            } catch (SQLException ex) {
                                throw new RuntimeException(ex);
                            }
                            prefabDialogueCreateAccount.showDialog();
                        });
                    Prefab_AccountBox prefabAccountBox = new Prefab_AccountBox();
                    prefabAccountBox.show(accountsBox, accounts_x);
                    accountsPane.setContent(accountsBox);


                    //DEPOSITS
                    TitledPane depositsPane = new TitledPane();
                    depositsPane.setText("Deposits"); depositsPane.setExpanded(false);
                    moreItemsBox.getChildren().addAll(depositsPane);

                    Button buttonCreateDeposit = new Button();
                    buttonCreateDeposit.setGraphic(icon_create2);
                    buttonCreateDeposit.setPrefWidth(999);
                        buttonCreateDeposit.setOnAction(e->{
                            Prefab_DialogueCreateDeposit prefabDialogueCreateDeposit = new Prefab_DialogueCreateDeposit(user.getUser_id());
                            prefabDialogueCreateDeposit.showDialog();
                        });

                    String query2 = "SELECT * FROM deposits WHERE deposits.user_id = " + user.getUser_id();
                    ArrayList<EntityDeposit> deposits_x = null;
                    try {
                        deposits_x = DB_ClassicListBuilder.depositListBuild(query2);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    VBox depositsBox = new VBox();
                    Prefab_DepositBox prefabDepositBox = new Prefab_DepositBox();
                    prefabDepositBox.show(depositsBox, deposits_x);
                    VBox depositsPaneBox = new VBox(buttonCreateDeposit, depositsBox);
                    depositsPane.setContent(depositsPaneBox);


                    //LOANS
                    TitledPane loansPane = new TitledPane();
                    loansPane.setText("Loans"); loansPane.setExpanded(false);
                    moreItemsBox.getChildren().addAll(loansPane);

                    Button buttonCreateLoan = new Button();
                    buttonCreateLoan.setGraphic(icon_create3);
                    buttonCreateLoan.setPrefWidth(999);
                    buttonCreateLoan.setOnAction(e->{
                        Prefab_DialogueCreateLoan prefabDialogueCreateLoan = new Prefab_DialogueCreateLoan(user.getUser_id());
                        prefabDialogueCreateLoan.showDialog();
                    });

                    String query3 = "SELECT * FROM loans WHERE loans.user_id = " + user.getUser_id();
                    ArrayList<EntityLoan> loans_x = null;
                    try {
                        loans_x = DB_ClassicListBuilder.loanListBuild(query3);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    VBox loansBox = new VBox();
                    Prefab_LoanBox prefabLoanBox = new Prefab_LoanBox();
                    prefabLoanBox.show(loansBox, loans_x);
                    VBox loansPaneBox = new VBox(buttonCreateLoan, loansBox);
                    loansPane.setContent(loansPaneBox);
                }
            });

            VBox.setMargin(expandedItemBox, new Insets(10, 0, 10, 0));
            expandedItemBox.getChildren().addAll(itemBox, moreItemsBox);
            usersPane.getChildren().add(expandedItemBox);
        }
    }
}
