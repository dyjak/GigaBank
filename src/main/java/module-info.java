module gigabank.gigabank {
    requires javafx.controls;
    requires javafx.fxml;


    opens gigabank.gigabank to javafx.fxml;
    exports gigabank.gigabank;
    exports gigabank.gigabank.Controllers;
    opens gigabank.gigabank.Controllers to javafx.fxml;
}