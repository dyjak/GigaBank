package gigabank.gigabank.Entities;

import java.sql.*;
import java.util.ArrayList;

public class DB_ProceduralUpdater {

    //CONNECTION ESTABLISHMENT
    private static Connection DB_EstablishConnection() throws SQLException {
        String jdbcURL = "jdbc:oracle:thin:@//localhost:1521/freepdb1";
        String username = "hr";
        String password = "oracle";
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        return connection;
    }

    //UPDATE CURRENCY
    //CURRENCY BUILDER
    public void currencyUpdate(String argName, double argConversion, int argID) throws SQLException {
        Statement createStatement = DB_EstablishConnection().createStatement();

        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL update_currency(?, ?, ?)}");
        callableStatement.setString(1, argName);
        callableStatement.setDouble(2, argConversion);
        callableStatement.setInt(3, argID);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("SUCCESS!");
    }
}
