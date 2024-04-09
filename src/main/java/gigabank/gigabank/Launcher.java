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



        //FXML LOADER
        FXMLLoader fxmlLoader = new FXMLLoader(Launcher.class.getResource("fxmls/login-panel-view.fxml"));

        //SCENE
        int width = 408; int height = 720;
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        goTo_LOGIN_PANEL(stage, scene);

        //STAGE LOAD
        stage.setTitle("Hello!");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }




    public void goTo_LOGIN_PANEL(Stage stage, Scene scene)
    {
        stage.setScene(scene);
    }

}