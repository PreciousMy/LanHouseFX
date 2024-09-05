module com.example.lanhousefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.lanhousefx to javafx.fxml;
    exports com.example.lanhousefx;
}