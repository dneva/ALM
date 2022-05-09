module com.example.alm_gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.kohsuke.github.api;


    opens com.example.alm_gui to javafx.fxml;
    exports com.example.alm_gui;
    exports com.example.alm_gui.Classes;
    opens com.example.alm_gui.Classes to javafx.fxml;
}