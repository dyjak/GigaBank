package gigabank.gigabank;

import gigabank.gigabank.Entities.EntityUser;
import gigabank.gigabank.Entities.DB_ClassicListBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Launcher extends Application {


    @Override
    public void start(Stage stage) throws IOException, SQLException {

        //ATLANTA-FX

        //TEST QUERY
        String theQuery = "SELECT * FROM users";
        ArrayList<EntityUser> users_x = DB_ClassicListBuilder.userListBuild(theQuery);
        System.out.println(users_x);

        //IMAGES
        Image image = new Image(Launcher.class.getResourceAsStream("canvas/logo/gigabank-logo-favicon.png"));
        stage.getIcons().add(image);




        //  ###   FIRTS SCENE : LOGIN PANEL   ###
        //FXML LOADER
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("fxmls/main-administrator-panel-view.fxml"));
        //SCENE
        Scene scene = new Scene(fxmlLoader.load(), ApplicationManager.WIDTH, ApplicationManager.HEIGHT);
        stage.setScene(scene);
        //STAGE LOAD
        stage.setTitle(ApplicationManager.TITLE);
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }

}