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

    //DESTROY USER
    public void userDestroy(int argID) throws SQLException {
        Statement createStatement = DB_EstablishConnection().createStatement();
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL delete_user(?)}");
        callableStatement.setInt(1, argID);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("SUCCESS!");
    }

    //DESTROY ACCOUNT
    public void accountDestroy(int accountID) throws SQLException {
        try (Connection connection = DB_EstablishConnection();
             CallableStatement callableStatement = connection.prepareCall("{CALL delete_account(?)}")) {
            callableStatement.setInt(1, accountID);
            callableStatement.execute();
        }
    }

    //DESTROY TRANSACTION
    public void transactionDestroy(int transactionId) throws SQLException {
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL delete_transaction(?)}");
        callableStatement.setInt(1, transactionId);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("SUCCESS!");
    }

    //DESTROY DEPOSIT
    public void deleteDeposit(int depositId) throws SQLException {
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL delete_deposit(?)}");
        callableStatement.setInt(1, depositId);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("Deposit deleted successfully!");
    }

    //DESTROY LOAN
    public void deleteLoan(int loanId) throws SQLException {
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL delete_loan(?)}");
        callableStatement.setInt(1, loanId);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("Loan deleted successfully!");
    }
}
