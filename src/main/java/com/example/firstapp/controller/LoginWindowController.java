package com.example.firstapp.controller;
import com.example.firstapp.enums.WindowType;
import com.example.firstapp.helper.MsgHelper;
import com.example.firstapp.helper.WindowHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.awt.*;

public class LoginWindowController {
    private int incorrectLoginCount = 0;
    public PasswordField txtPassword;
    public TextField txtUser;

    /**
     * Metoda sprawdza czy dane logowania są poprawne, jeśli sa to przechodzi do ekranu głównego, jeśli nie to wyświetla
     * komunikat, jeśli komunikat pokaże się 3 razy okienko logowania się wyłącza
     */
    public void btnLoginClicked(ActionEvent actionEvent) {
        String password = txtPassword.getText();
        String login = txtUser.getText();

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();

        if (incorrectLoginCount >= 3) {
            ((Stage) txtPassword.getScene().getWindow()).close();
        } else if (login.equals("admin") && password.equals("admin")) {
            ((Stage) txtPassword.getScene().getWindow()).close();
            WindowHelper.openWindow(WindowType.MAIN_WINDOW, screenSize.width, screenSize.height );

        } else if(login.equals("boss") && password.equals("boss")){
            ((Stage) txtPassword.getScene().getWindow()).close();
            MainWindowController.isAdmin = true;
            WindowHelper.openWindow(WindowType.MAIN_WINDOW);

        } else {
            MsgHelper.showError("Błąd logowania", "Podaj poprawne dane logowania");
            incorrectLoginCount++;
        }
    }

    /**
     * Metoda pozwala na przejście do logowania  za pomoca klawisza enter
     */
    public void initialize() {
        txtPassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnLoginClicked(null);
            }
        });
    }
}