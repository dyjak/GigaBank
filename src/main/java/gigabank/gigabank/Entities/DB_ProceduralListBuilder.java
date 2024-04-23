package gigabank.gigabank.Entities;

import java.sql.*;
import java.util.ArrayList;

public class DB_ProceduralListBuilder {

    //CONNECTION ESTABLISHMENT
    private static Connection DB_EstablishConnection() throws SQLException {
        String jdbcURL = "jdbc:oracle:thin:@//localhost:1521/freepdb1";
        String username = "hr";
        String password = "oracle";
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        return connection;
    }



    //USERS BUILDER
    public ArrayList<EntityUser> userListBuild(String argument) throws SQLException {
        Statement createStatement = DB_EstablishConnection().createStatement();

        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL search_users(?)}");
        callableStatement.setString(1, argument);
        callableStatement.execute();

        String sqlQuery="SELECT * FROM temp_users";
        ResultSet resultSet = DB_EstablishConnection().createStatement().executeQuery(sqlQuery);

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
        }
        return users_x;
    }


    //CURRENCY BUILDER
    public ArrayList<EntityCurrency> currencyListBuild(String argument) throws SQLException {
        Statement createStatement = DB_EstablishConnection().createStatement();

        CallableStatement callableStatement = DB_EstablishConnection().prepareCall("{CALL search_currencies(?)}");
        callableStatement.setString(1, argument);
        callableStatement.execute();

        String sqlQuery="SELECT * FROM temp_currencies";
        ResultSet resultSet = DB_EstablishConnection().createStatement().executeQuery(sqlQuery);

        ArrayList<EntityCurrency> currencies_x = new ArrayList<>();
        while (resultSet.next()) {
            int currency_id = resultSet.getInt("currency_id");
            String currencyName = resultSet.getString("currency");
            double usd_conversion = resultSet.getDouble("usd_conversion");
            EntityCurrency currency = new EntityCurrency(currency_id, currencyName, usd_conversion);
            currencies_x.add(currency);
        }
        return currencies_x;
    }
}
