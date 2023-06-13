package com.example.firstapp.helper;

import com.example.firstapp.App;
import com.example.firstapp.enums.WindowType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Klasa zawierająca metody związane z obsługą okien.
 */
public class WindowHelper {

    /**
     * Otwiera wybrane okno.
     *
     * @param windowType typ okna do otwarcia
     */
    public static void openWindow(WindowType windowType) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(windowType.getViewPath()));
            Scene scene = new Scene(fxmlLoader.load(), windowType.getWidth(), windowType.getHeight());
            Stage stage = new Stage();
            stage.setTitle(windowType.getTitle());
            stage.setScene(scene);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            MsgHelper.showError(String.format("Błąd otwierania ekranu %s", windowType.getViewPath()), ex.getLocalizedMessage());
        }
    }

    /**
     * Otwiera wybrane okno z ustawieniem wielkości okna
     *
     * @param windowType typ okna do otwarcia
     *        width - szerokość okna
     *        height - wysokość okna
     */
    public static void openWindow(WindowType windowType, int width, int height) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(windowType.getViewPath()));
            Scene scene = new Scene(fxmlLoader.load(), windowType.getWidth(), windowType.getHeight());
            Stage stage = new Stage();
            stage.setTitle(windowType.getTitle());
            stage.setScene(scene);
            stage.setWidth(width);
            stage.setHeight(height);
            stage.show();
        } catch (Exception ex) {
            ex.printStackTrace();
            MsgHelper.showError(String.format("Błąd otwierania ekranu %s", windowType.getViewPath()), ex.getLocalizedMessage());
        }
    }

    /**
     * Metoda ta zamyka okno po przekazaniu jednego z jego komponentów.
     *
     * @param cmp komponent z ekrenu do zamknięcia
     */
    public static void closeWindow(Node cmp) {
        try {
            ((Stage) cmp.getScene().getWindow()).close();
        } catch (Exception ex) {
            ex.printStackTrace();
            MsgHelper.showError(String.format("Błąd zamykania ekranu ",ex.getLocalizedMessage()),"Spróbuj ponownie");
        }
    }
}