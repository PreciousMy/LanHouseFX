module com.example.lanhousefx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lanhousefx to javafx.fxml;
    exports com.example.lanhousefx;
}