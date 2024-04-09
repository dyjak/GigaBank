package gigabank.gigabank;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {

        //FXML LOADER
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("fxmls/login-panel-view.fxml"));


        String jdbcURL = "jdbc:oracle:thin:@//localhost:1521/freepdb1";
        String username = "hr"; String password = "oracle";

        Connection connection = DriverManager.getConnection(jdbcURL,username,password);

        String theQuery = "SELECT * FROM users";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(theQuery);

        while(resultSet.next())
        {
            int id = resultSet.getInt("user_id");
            String name = resultSet.getString("name");
            System.out.println(id + " " + name);
        }


        //STAGE LOAD
        int width = 405; int height = 720;
        Scene scene = new Scene(fxmlLoader.load(), width, height);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}