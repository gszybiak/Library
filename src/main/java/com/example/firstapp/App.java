package com.example.firstapp;

import com.example.firstapp.enums.WindowType;
import com.example.firstapp.helper.WindowHelper;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Główna klasa aplikacji.
 */
public class App extends Application {

    /**
     * Otwiera ekran główny.
     */
    @Override
    public void start(Stage stage) {
        WindowHelper.openWindow(WindowType.LOGIN_WINDOW);
    }

    /**
     * Metoda startowa aplikacji.
     */
    public static void main(String[] args){
        launch();
    }
}
