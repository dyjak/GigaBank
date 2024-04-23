module gigabank.gigabank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires atlantafx.base;
    requires com.oracle.database.jdbc;


    opens gigabank.gigabank to javafx.fxml;
    exports gigabank.gigabank;
    exports gigabank.gigabank.Entities;
    opens gigabank.gigabank.Entities to javafx.fxml;
}