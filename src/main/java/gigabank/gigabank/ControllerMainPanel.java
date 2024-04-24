package gigabank.gigabank;

import gigabank.gigabank.Entities.*;
import gigabank.gigabank.Prefabs.Prefab_CurrencyItemBox;
import gigabank.gigabank.Prefabs.Prefab_DialogueEdit;
import gigabank.gigabank.Prefabs.Prefab_UserItemBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
        loadResultsUsers();
        loadResultsCurrencies();
    }


    @FXML
    private VBox usersPane;
    @FXML
    private VBox currenciesPane;
    @FXML
    private TextField searchField;


    public void updateResults(ActionEvent event) {
        System.out.println("updateResults");
        searchText = searchField.getText().toUpperCase();
        loadResultsUsers();
        loadResultsCurrencies();
    }



    public void loadResultsUsers()
    {
        usersPane.getChildren().clear();

        ImageView icon_create = new ImageView(new Image(getClass().getResourceAsStream("canvas/icons/plus.png"))); icon_create.setFitWidth(20);    icon_create.setFitHeight(20);
        Button createButton = new Button();   createButton.setPrefWidth(999); createButton.setGraphic(icon_create);
        usersPane.getChildren().add(createButton);

        DB_ProceduralListBuilder listBuilder = new DB_ProceduralListBuilder();
        ArrayList<EntityUser> users_x = null;
        try
        {
            users_x = listBuilder.userListBuild(searchText);
        } catch (SQLException e) {
            throw new RuntimeException(e);}

        Prefab_UserItemBox prefabUserItemBox = new Prefab_UserItemBox();
        prefabUserItemBox.show(usersPane, users_x);
    }







    public void loadResultsCurrencies() {
        //CLEARING
        currenciesPane.getChildren().clear();

        //CREATE BUTTON
        ImageView icon_create = new ImageView(new Image(getClass().getResourceAsStream("canvas/icons/plus.png"))); icon_create.setFitWidth(20);    icon_create.setFitHeight(20);
        Button createButton = new Button();   createButton.setPrefWidth(999); createButton.setGraphic(icon_create);
        currenciesPane.getChildren().add(createButton);

        //CURRENCIES LIST
        DB_ProceduralListBuilder listBuilder = new DB_ProceduralListBuilder();
        ArrayList<EntityCurrency> currencies_x = null;
        try
        {
            currencies_x = listBuilder.currencyListBuild(searchText);
        } catch (SQLException e) {
            throw new RuntimeException(e);}

        Prefab_CurrencyItemBox prefabCurrencyItemBox = new Prefab_CurrencyItemBox();
        prefabCurrencyItemBox.show(currenciesPane, currencies_x);
    }

}
