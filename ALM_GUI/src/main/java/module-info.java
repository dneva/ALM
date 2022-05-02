module com.example.alm_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.alm_gui to javafx.fxml;
    exports com.example.alm_gui;
    exports com.example.alm_gui.Classes;
    opens com.example.alm_gui.Classes to javafx.fxml;
}