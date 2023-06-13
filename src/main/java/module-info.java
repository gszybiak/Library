module com.example.firstapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.desktop;
    requires activation;


    opens com.example.firstapp to javafx.fxml;
    exports com.example.firstapp;
    exports com.example.firstapp.helper;
    opens com.example.firstapp.helper to javafx.fxml;
    exports com.example.firstapp.enums;
    opens com.example.firstapp.enums to javafx.fxml;
    exports com.example.firstapp.controller;
    opens com.example.firstapp.controller to javafx.fxml;
}