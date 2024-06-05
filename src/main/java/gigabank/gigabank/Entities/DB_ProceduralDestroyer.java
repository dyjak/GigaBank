package gigabank.gigabank.Entities;

import java.sql.*;

public class DB_ProceduralDestroyer {

    //CONNECTION ESTABLISHMENT
    private static Connection DB_EstablishConnection() throws SQLException {
        String jdbcURL = "jdbc:oracle:thin:@//localhost:1521/freepdb1";
        String username = "hr";
        String password = "oracle";
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        return connection;
    }

    //DESTROY CURRENCY
    public void currencyDestroy(int argID) throws SQLException {
        Statement createStatement = DB_EstablishConnection().createStatement();
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL delete_currency(?)}");
        callableStatement.setInt(1, argID);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("SUCCESS!");
    }
}
