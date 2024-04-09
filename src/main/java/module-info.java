module gigabank.gigabank {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens gigabank.gigabank to javafx.fxml;
    exports gigabank.gigabank;
    exports gigabank.gigabank.Controllers;
    opens gigabank.gigabank.Controllers to javafx.fxml;
    exports gigabank.gigabank.Entities;
    opens gigabank.gigabank.Entities to javafx.fxml;
}