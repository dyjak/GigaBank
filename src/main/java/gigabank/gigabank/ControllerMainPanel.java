package gigabank.gigabank;

import gigabank.gigabank.Entities.DB_ListBuilder;
import gigabank.gigabank.Entities.EntityUser;
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
import javafx.scene.layout.RowConstraints;
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

        loadResults();

    }


    @FXML
    private VBox usersPane;
    @FXML
    private TextField searchField;


    public void updateResults(ActionEvent event)
    {
        System.out.println("updateResults");
        searchText = searchField.getText().toUpperCase();
        loadResults();
    }

    public void loadResults()
    {
        usersPane.getChildren().clear();

        String sqlQuery = "SELECT * FROM users";
        if(searchText.length() > 0) sqlQuery += " WHERE UPPER(name) LIKE '%" + searchText + "%'" +
                                                    " OR UPPER(surname) LIKE '%" + searchText + "%'" +
                                                    " OR UPPER(login) LIKE '%" + searchText + "%'";
        System.out.println(sqlQuery);
        ArrayList<EntityUser> users_x = null;
        try {
            users_x = DB_ListBuilder.userListBuild(sqlQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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
                        moreItemsBox.getChildren().clear();
                    }
                    else
                    {
                        expanded[0] = true;
                        Button b1 = new Button("Expand");
                        Button b2 = new Button("Expand");
                        Button b3 = new Button("Expand");
                        moreItemsBox.getChildren().addAll(b1, b2, b3);
                    }
                });
            VBox.setMargin(expandedItemBox,new Insets(10,0,10,0));
            expandedItemBox.getChildren().addAll(itemBox, moreItemsBox);
            usersPane.getChildren().add(expandedItemBox);
        }
    }
}
