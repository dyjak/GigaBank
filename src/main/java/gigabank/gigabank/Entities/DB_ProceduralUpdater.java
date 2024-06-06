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

    // UPDATE USER
    public void userUpdate(int userId, String name, String surname, java.sql.Date birthDate, String sex, String login, String pin) throws SQLException {
        Statement createStatement = DB_EstablishConnection().createStatement();
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL update_user(?, ?, ?, ?, ?, ?, ?)}");
        callableStatement.setInt(1, userId);
        callableStatement.setString(2, name);
        callableStatement.setString(3, surname);
        callableStatement.setDate(4, birthDate);
        callableStatement.setString(5, sex);
        callableStatement.setString(6, login);
        callableStatement.setString(7, pin);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("User updated successfully!");
    }

    //UPDATE ACCOUNT
    public void accountUpdate(EntityAccount account) throws SQLException {
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL update_account(?, ?, ?, ?, ?, ?, ?)}");
        callableStatement.setInt(1, account.getAccount_id());
        callableStatement.setInt(2, account.getUser_id());
        callableStatement.setInt(3, account.getCurrency_id());
        callableStatement.setDouble(4, account.getBalance());
        callableStatement.setString(5, account.getAccount_number());
        callableStatement.setDate(6, new java.sql.Date(account.getCreate_date().getTime()));
        callableStatement.setString(7, account.getPermission());
        callableStatement.execute();
        callableStatement.close();
        System.out.println("SUCCESS!");
    }

    // UPDATE TRANSACTION
    public void transactionUpdate(int transactionId, int accountId, double amount, String contractorAccountNumber, String description) throws SQLException {
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL update_transaction(?, ?, ?, ?, ?)}");
        callableStatement.setInt(1, transactionId);
        callableStatement.setInt(2, accountId);
        callableStatement.setDouble(3, amount);
        callableStatement.setString(4, contractorAccountNumber);
        callableStatement.setString(5, description);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("Transaction updated successfully!");
    }

    //UPDATE DEPOSIT
    public void depositUpdate(int depositId, String title, double amount, int currencyId, double interestPercentage, String description, Date createDate, Date deadline, double status) throws SQLException {
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL update_deposit(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        callableStatement.setInt(1, depositId);
        callableStatement.setString(2, title);
        callableStatement.setDouble(3, amount);
        callableStatement.setInt(4, currencyId);
        callableStatement.setDouble(5, interestPercentage);
        callableStatement.setString(6, description);
        callableStatement.setDate(7, createDate);
        callableStatement.setDate(8, deadline);
        callableStatement.setDouble(9, status);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("Deposit updated successfully!");
    }

    //UPDATE LOAN
    public void loanUpdate(int loanId, String title, double amount, int currencyId, double interestPercentage, String description, Date createDate, Date deadline, double status) throws SQLException {
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL update_loan(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        callableStatement.setInt(1, loanId);
        callableStatement.setString(2, title);
        callableStatement.setDouble(3, amount);
        callableStatement.setInt(4, currencyId);
        callableStatement.setDouble(5, interestPercentage);
        callableStatement.setString(6, description);
        callableStatement.setDate(7, createDate);
        callableStatement.setDate(8, deadline);
        callableStatement.setDouble(9, status);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("Loan updated successfully!");
    }
}
