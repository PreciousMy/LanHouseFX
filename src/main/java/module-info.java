module com.example.lanhousefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jdk.compiler;
    requires java.sql.rowset;
    requires transitive mysql.connector.j;
    requires javafx.graphics;
    requires java.desktop;


    opens com.example.lanhousefx to javafx.fxml;
    exports com.example.lanhousefx;
}