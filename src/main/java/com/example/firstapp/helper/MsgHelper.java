package com.example.firstapp.helper;

import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Klasa zawierająca przydatne metody do wyświetlania komunikatów.
 */
public class MsgHelper {
    /**
     * Wyświetla komunikat błędu.
     *
     * @param header nagłówek komunikatu do wyświetlenia
     * @param msg    komunikat do wyświetlenia
     */
    public static void showError(String header, String msg) {
        show(header, msg, Alert.AlertType.ERROR, "Wystąpił błąd");
    }

    /**
     * Wyświetla alert na ekranie.
     *
     * @param header nagłówek komunikatu do wyświetlenia
     * @param msg    komunikat do wyświetlenia
     * @param type   typ alertu
     * @param title  tytuł okna zawierającego alert
     */
    private static void show(String header, String msg, Alert.AlertType type, String title) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    /**
     * Wyświetla alert z wyborem tak lub nie na ekranie
     *
     * @param header nagłówek komunikatu do wyświetlenia
     * @param msg    komunikat do wyświetlenia
     * @param title  tytuł okna zawierającego alert
     */
    public static boolean showYesOrNoAlert(String header, String msg, String title,Node node) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(msg);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
            return true;
        else
            return false;

    }
}