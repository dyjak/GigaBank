package gigabank.gigabank.Entities;

import java.sql.*;

public class DB_ProceduralCreater {

    //CONNECTION ESTABLISHMENT
    private static Connection DB_EstablishConnection() throws SQLException {
        String jdbcURL = "jdbc:oracle:thin:@//localhost:1521/freepdb1";
        String username = "hr";
        String password = "oracle";
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        return connection;
    }

    //CREATE CURRENCY
    public void currencyCreate(String argName, double argConversion) throws SQLException {
        Statement createStatement = DB_EstablishConnection().createStatement();
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL add_currency(?, ?)}");
        callableStatement.setString(1, argName);
        callableStatement.setDouble(2, argConversion);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("SUCCESS!");
    }
}
