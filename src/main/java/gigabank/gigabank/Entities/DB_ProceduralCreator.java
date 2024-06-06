package gigabank.gigabank.Entities;

import java.sql.*;

public class DB_ProceduralCreator {

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

    // CREATE USER
    public void userCreate(String name, String surname, String birthDate, String sex, String login, String pin) throws SQLException {
        Statement createStatement = DB_EstablishConnection().createStatement();
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL add_user(?, ?, ?, ?, ?, ?)}");
        callableStatement.setString(1, name);
        callableStatement.setString(2, surname);
        callableStatement.setString(3, birthDate);
        callableStatement.setString(4, sex);
        callableStatement.setString(5, login);
        callableStatement.setString(6, pin);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("SUCCESS!");
    }

    //CREATE ACCOUNT
    public void accountCreate(int p_user_id, int p_currency_id, double p_balance, String p_account_number, String p_permission) throws SQLException {
        Statement createStatement = DB_EstablishConnection().createStatement();
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL add_account(?, ?, ?, ?, ?, ?)}");
        callableStatement.setInt(1, p_user_id);
        callableStatement.setInt(2, p_currency_id);
        callableStatement.setDouble(3, p_balance);
        callableStatement.setString(4, p_account_number);
        callableStatement.setDate(5, new java.sql.Date(System.currentTimeMillis()));
        callableStatement.setString(6, p_permission);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("SUCCESS!");
    }

    // CREATE TRANSACTION
    public void transactionCreate(int accountId, double amount, String contractorAccountNumber, Timestamp executeDate, String description) throws SQLException {
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL add_transaction(?, ?, ?, ?, ?)}");
        callableStatement.setInt(1, accountId);
        callableStatement.setDouble(2, amount);
        callableStatement.setString(3, contractorAccountNumber);
        callableStatement.setTimestamp(4, executeDate);
        callableStatement.setString(5, description);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("SUCCESS!");
    }

    //DEPOSIT CREATE
    public void depositCreate(String title, double amount, int currencyId, double interestPercentage, String description, int userId, Date createDate, Date deadline, double status) throws SQLException {
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL add_deposit(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        callableStatement.setString(1, title);
        callableStatement.setDouble(2, amount);
        callableStatement.setInt(3, currencyId);
        callableStatement.setDouble(4, interestPercentage);
        callableStatement.setString(5, description);
        callableStatement.setInt(6, userId);
        callableStatement.setDate(7, createDate);
        callableStatement.setDate(8, deadline);
        callableStatement.setDouble(9, status);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("Deposit added successfully!");
    }

    //LOAN CREATE
    public void loanCreate(String title, double amount, int currencyId, double interestPercentage, String description, int userId, Date createDate, Date deadline, double status) throws SQLException {
        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL add_loan(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
        callableStatement.setString(1, title);
        callableStatement.setDouble(2, amount);
        callableStatement.setInt(3, currencyId);
        callableStatement.setDouble(4, interestPercentage);
        callableStatement.setString(5, description);
        callableStatement.setInt(6, userId);
        callableStatement.setDate(7, createDate);
        callableStatement.setDate(8, deadline);
        callableStatement.setDouble(9, status);
        callableStatement.execute();
        callableStatement.close();
        System.out.println("Loan added successfully!");
    }
}
