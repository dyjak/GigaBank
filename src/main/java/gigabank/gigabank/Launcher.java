package gigabank.gigabank;

import gigabank.gigabank.Entities.EntityUser;
import gigabank.gigabank.Entities.DB_ListBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class Launcher extends Application {


    @Override
    public void start(Stage stage) throws IOException, SQLException {

        //TEST QUERY
        String theQuery = "SELECT * FROM users";
        ArrayList<EntityUser> users_x = DB_ListBuilder.userListBuild(theQuery);
        System.out.println(users_x);





        //  ###   FIRTS SCENE : LOGIN PANEL   ###
        //FXML LOADER
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("fxmls/login-panel-view.fxml"));
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