package gigabank.gigabank.Entities;

import java.sql.*;
import java.util.ArrayList;


public class DB_ListBuilder {


    //CONNECTION ESTABLISHMENT
    private static Statement DB_EstablishConnection() throws SQLException {
        String jdbcURL = "jdbc:oracle:thin:@//localhost:1521/freepdb1";
        String username = "hr";
        String password = "oracle";
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        Statement statement = connection.createStatement();
        return statement;
    }


    //ACCOUNTS BUILDER
    public static ArrayList<EntityAccount> accountListBuild(String sqlQuery) throws SQLException {

        ResultSet resultSet = DB_EstablishConnection().executeQuery(sqlQuery);

        ArrayList<EntityAccount> accounts_x = new ArrayList<>();
        while (resultSet.next()) {
            int account_id = resultSet.getInt("account_id");
            ;
            int user_id = resultSet.getInt("user_id");
            ;
            int currency_id = resultSet.getInt("currency_id");
            ;
            double balance = resultSet.getDouble("balance");
            ;
            String account_number = resultSet.getString("account_number");
            ;
            java.util.Date create_date = resultSet.getDate("create_date");
            ;
            String permission = resultSet.getString("permission");
            ;

            EntityAccount account = new EntityAccount(account_id, user_id, currency_id, balance, account_number, create_date, permission);
            accounts_x.add(account);
            //System.out.println(account);
        }
        return accounts_x;
    }


    //CURRENCIES BUILDER
    public static ArrayList<EntityCurrency> currencyListBuild(String sqlQuery) throws SQLException {

        ResultSet resultSet = DB_EstablishConnection().executeQuery(sqlQuery);

        ArrayList<EntityCurrency> currencies_x = new ArrayList<>();
        while (resultSet.next()) {
            int currency_id = resultSet.getInt("currency_id");
            ;
            String currency = resultSet.getString("currency");
            ;
            double usd_conversion = resultSet.getDouble("usd_conversion");
            ;

            EntityCurrency currency_ = new EntityCurrency(currency_id, currency, usd_conversion);
            currencies_x.add(currency_);
            //System.out.println(account);
        }
        return currencies_x;
    }


    //DEPOSITS BUILDER
    public static ArrayList<EntityDeposit> depositListBuild(String sqlQuery) throws SQLException {

        ResultSet resultSet = DB_EstablishConnection().executeQuery(sqlQuery);

        ArrayList<EntityDeposit> deposits_x = new ArrayList<>();
        while (resultSet.next()) {
            int deposit_id = resultSet.getInt("deposit_id");
            String title = resultSet.getString("title");
            double amount = resultSet.getDouble("amount");
            int currency_id = resultSet.getInt("currency_id");
            int interest_percentage = resultSet.getInt("interest_percentage");
            String description = resultSet.getString("description");
            int user_id = resultSet.getInt("user_id");
            Date create_date = resultSet.getDate("create_date");
            Date deadline = resultSet.getDate("deadline");
            double status = resultSet.getDouble("status");

            EntityDeposit deposit = new EntityDeposit(deposit_id, title, amount, currency_id, interest_percentage, description, user_id, create_date, deadline, status);
            deposits_x.add(deposit);
            //System.out.println(account);
        }
        return deposits_x;
    }


    //LOANS BUILDER
    public static ArrayList<EntityLoan> loanListBuild(String sqlQuery) throws SQLException {

        ResultSet resultSet = DB_EstablishConnection().executeQuery(sqlQuery);

        ArrayList<EntityLoan> loans_x = new ArrayList<>();
        while (resultSet.next()) {
            int loan_id = resultSet.getInt("loan_id");
            String title = resultSet.getString("title");
            double amount = resultSet.getDouble("amount");
            int currency_id = resultSet.getInt("currency_id");
            int interest_percentage = resultSet.getInt("interest_percentage");
            String description = resultSet.getString("description");
            int user_id = resultSet.getInt("user_id");
            Date create_date = resultSet.getDate("create_date");
            Date deadline = resultSet.getDate("deadline");
            double status = resultSet.getDouble("status");

            EntityLoan loan = new EntityLoan(loan_id, title, amount, currency_id, interest_percentage, description, user_id, create_date, deadline, status);
            loans_x.add(loan);
            //System.out.println(loan);
        }
        return loans_x;
    }


    //TRANSACTIONS BUILDER
    public static ArrayList<EntityTransaction> transactionListBuild(String sqlQuery) throws SQLException {

        ResultSet resultSet = DB_EstablishConnection().executeQuery(sqlQuery);

        ArrayList<EntityTransaction> transactions_x = new ArrayList<>();
        while (resultSet.next()) {
            int transaction_id = resultSet.getInt("transaction_id");
            int account_id = resultSet.getInt("account_id");
            double amount = resultSet.getDouble("amount");
            String contractor_account_number = resultSet.getString("contractor_account_number");
            Timestamp execute_date = resultSet.getTimestamp("execute_date");
            String description = resultSet.getString("description");

            EntityTransaction transaction = new EntityTransaction(transaction_id,account_id,amount,contractor_account_number,execute_date,description);
            transactions_x.add(transaction);
            //System.out.println(transaction);
        }
        return transactions_x;
    }


    //USERS BUILDER
    public static ArrayList<EntityUser> userListBuild(String sqlQuery) throws SQLException {

        ResultSet resultSet = DB_EstablishConnection().executeQuery(sqlQuery);

        ArrayList<EntityUser> users_x = new ArrayList<>();
        while (resultSet.next()) {
            int user_id = resultSet.getInt("user_id");
            String name = resultSet.getString("name");
            String surname = resultSet.getString("surname");
            Date birthdate = resultSet.getDate("birthdate");
            String sex = resultSet.getString("sex");
            String login = resultSet.getString("login");
            String pin = resultSet.getString("pin");

            EntityUser user = new EntityUser(user_id, name, surname, birthdate, sex, login, pin);
            users_x.add(user);
            //System.out.println(eUser);
        }
        return users_x;
    }

}