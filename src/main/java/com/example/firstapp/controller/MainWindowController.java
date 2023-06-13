package com.example.firstapp.controller;

import com.example.firstapp.Email.SendEmail;
import com.example.firstapp.helper.MsgHelper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

public class MainWindowController {

    public TextField txtISBN;
    public TextField txtAuthor;
    public TextField txtTitle;
    public TextField txtPublicationYear;
    public TextField txtPublishingHouse;
    public TextField txtDescription;
    public Label txtNumberBook;
    public Button btnLeftArrow;
    public Button btnRightArrow;
    public Button btnSave;
    public Button btnCancel;
    public Button btnRent;
    public Button btnReturn;
    public Button btnSendAll;
    public DatePicker calendarRent;
    /** Czy admin */
    public static boolean isAdmin = false;
    /** Czy tryb dodawania */
    private boolean addMode = false;
    /** Czy tryb edycji */
    private boolean editMode = false;
    private ArrayList<Book> bookList = new ArrayList<>();
    /** Aktualny numer wyświetlanej książki */
    private int numberBook;
    /** Ścieżka do pliku z danymi książek */
    private String pathFile = "C:/Users/Gabriel/TaskJava/FirstApp/book.txt";

    /**
     * Metoda "load"
     */
    public void initialize() throws IOException {
        Path path = Paths.get(pathFile);
        if(!Files.exists(path))
            Files.createFile(path);

        long countLines= Files.lines(path).count();
        if(countLines > 0) {
            Scanner scanner = new Scanner(path);


            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] attributes = line.split("#");

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate rentDate = attributes.length > 7 ?  LocalDate.parse(attributes[7], formatter) : null;
                Book book = new Book(attributes.length > 0 ?  attributes[0] : " ", attributes.length > 1 ?  attributes[1] : " ",
                        attributes.length > 2 ?  attributes[2] : " ", attributes.length > 3 ?  attributes[3] : " ",
                        attributes.length > 4 ?  attributes[4] : " ", attributes.length > 5 ?  attributes[5] : " ",
                        rentDate);
                bookList.add(book);
            }
        }
        if(bookList.isEmpty()) {
            numberBook = 0;
            btnRightArrow.setDisable(true);
            btnLeftArrow.setDisable(true);
        }
        else {
            numberBook = 1;
            btnLeftArrow.setDisable(true);
            if (bookList.size() == 1)
                btnRightArrow.setDisable(true);
            else
                btnRightArrow.setDisable(false);
            setAllData(numberBook);
        }
        txtNumberBook.setText("Numer książki: " + numberBook);
        disabledAllData(true);
        btnCancel.setVisible(false);
        btnSave.setVisible(false);

        visabledButtonsRent();
        btnSendAll.setVisible(isAdmin);
    }

    /**
     * Metoda po kliknięciu strzałki w lewo,
     * pobiera dane dla wcześniejszego rekordu,
     * przycisk zablokowany, jeśli
     * nie ma wcześniejszych rekordów
     */
    public void btnLeftClicked(ActionEvent actionEvent){
        if(numberBook > 1){
            numberBook--;
            btnLeftArrow.setDisable(false);
            btnRightArrow.setDisable(false);
            setAllData(numberBook);
            txtNumberBook.setText("Numer książki: " + numberBook);
            if(numberBook == 1)
                btnLeftArrow.setDisable(true);
        }
        else{
            btnLeftArrow.setDisable(true);

        }
        visabledButtonsRent();
    }

    /**
     * Metoda po kliknięciu strzałki w prawo,
     * pobiera dane dla następnego rekordu,
     * przycisk zablokowany, jeśli
     * nie ma następnych rekordów
     */
    public void btnRightClicked(ActionEvent actionEvent){
        if(numberBook == bookList.size())
            btnRightArrow.setDisable(true);
        else{
            numberBook++;
            btnRightArrow.setDisable(false);
            btnLeftArrow.setDisable(false);
            setAllData(numberBook);
            txtNumberBook.setText("Numer książki: " + numberBook);
            visabledButtonsRent();

            if(numberBook == bookList.size())
                btnRightArrow.setDisable(true);
        }
    }
    
    /**
     * Metoda po kliknięciu przycisku "Dodaj"
     * odblokowuje kontrolki i czyści ich zawartość
     * oraz ustawia flagę "czy tryb edycji" na true
     *
     */
    public void btnAddClicked(ActionEvent actionEvent){
        disabledAllData(false);
        cleanAllData();
        btnCancel.setVisible(true);
        btnSave.setVisible(true);
    }

    /**
     * Metoda wywołuje walidację kontrolek,
     * jeśli wszystko w porządku obiekt jest dodawany do listu,
     * ustawiamy flagę "czy w trybie edycji" na true
     */
    public void btnSaveClicked(ActionEvent actionEvent) throws IOException{
        if(!validate())
            return;

        Book book = new Book(txtISBN.getText(), txtAuthor.getText(), txtTitle.getText(),
                txtPublicationYear.getText(), txtPublishingHouse.getText(), txtDescription.getText(), calendarRent.getValue());

        if(!editMode)
        {
            bookList.add(book);
            if(bookList.size() == 1) {
                numberBook = 1;
                txtNumberBook.setText("Numer książki: " + numberBook);
                setAllData(numberBook);
                btnRightArrow.setDisable(true);
                btnLeftArrow.setDisable(true);
            }

            Path pathSaveNew = Paths.get(pathFile);
            Files.write(pathSaveNew, (book.toString() + "\n").getBytes(), StandardOpenOption.APPEND);

            if(numberBook + 1 == bookList.size())
                btnRightArrow.setDisable(false);
        }
        if(editMode)
        {
            bookList.set(numberBook - 1, book);

            Path pathSaveModified = Paths.get(pathFile);
            List<String> lines = Files.readAllLines(pathSaveModified);
            lines.set(numberBook - 1, book.toString());
            Files.write(pathSaveModified, lines);
        }
        editMode = false;
        disabledAllData(true);
        btnCancel.setVisible(false);
        btnSave.setVisible(false);
        visabledButtonsRent();
    }

    /**
     * Metoda wychodzi z trybu edycji lub dodawania rekordu
     */
    public void btnCancelClicked(ActionEvent actionEvent) {
        setAllData(numberBook);
        disabledAllData(true);
        btnCancel.setVisible(false);
        btnSave.setVisible(false);
        visabledButtonsRent();
    }

    /**
     * Metoda edytuje dany rekord
     */
    public void btnModifyClicked(ActionEvent actionEvent) {
        editMode = true;
        disabledAllData(false);
        btnCancel.setVisible(true);
        btnSave.setVisible(true);
        btnRent.setVisible(false);
        btnReturn.setVisible(false);
    }

    /**
     * Metoda usuwa dany rekord
     */
    public void btnDeleteClicked(ActionEvent actionEvent) throws IOException{
        boolean agree = MsgHelper.showYesOrNoAlert("Wybierz tak lub nie","Czy na pewno chcesz usunąć książkę?","Usuwanie książki",null);
        if(!agree)
            return;

        bookList.remove(numberBook - 1);
        Path pathSaveNew = Paths.get(pathFile);
        Files.write(pathSaveNew, new ArrayList<>());
        for(int i=0; i<bookList.size(); i++){
            Files.write(pathSaveNew, (bookList.get(i).toString() + "\n").getBytes(), StandardOpenOption.APPEND);
        }
        setAllData(numberBook);
        if(bookList.size() == 0) {
            numberBook = 0;
            txtNumberBook.setText("Numer książki: " + numberBook);
        }
    }

    /**
     * Metoda wypożyczająca książkę oraz wysyłająca potwierdzenie na maila
     */
    public void btnRentClicked(ActionEvent actionEvent) {
        boolean correct = true;
        if (calendarRent.getValue() == null) {
            MsgHelper.showError("Niepoprawne dane", "Uzupełnij poprawnie datę oddania książki");
            correct = false;
        }
        if (correct) {
            TextInputDialog dialog = new TextInputDialog("Adres email");
            dialog.setTitle("Wysyłanie email");
            dialog.setHeaderText("Podaj email:");
            dialog.setContentText("Email:");
            Optional<String> result = dialog.showAndWait();

            result.ifPresent(name -> {
                String encodedString = Base64.getEncoder().encodeToString(name.getBytes());
                byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
                String decodedString = new String(decodedBytes);
                    SendEmail.sendInfoRend(decodedString, calendarRent.getValue(), bookList.get(numberBook - 1));
            });
            btnRent.setVisible(false);
            btnReturn.setVisible(true);
        }
    }

    /**
     * Metoda po kliknięciu przycisku "Kopiuj do schowka"
     */
    public void btnCopyClicked(ActionEvent actionEvent){
        String dataBook = bookList.get(numberBook - 1).toString();
        StringSelection selection = new StringSelection(dataBook);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, selection);
    }

    /**
     * Metoda po kliknięciu przycisku "Wyślij wszystie"
     * Po kliknięciu wysyła się mail z wszystkimi informacjami danej książki
     */
    public void btnSendAllClicked(ActionEvent actionEvent){
        TextInputDialog dialog = new TextInputDialog("Adres email");
        dialog.setTitle("Wysyłanie email");
        dialog.setHeaderText("Podaj email:");
        dialog.setContentText("Email:");
        Optional<String> result = dialog.showAndWait();

        result.ifPresent(name -> {
            String encodedString = Base64.getEncoder().encodeToString(name.getBytes());
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            String decodedString = new String(decodedBytes);
            SendEmail.sendAllInformation(decodedString);
        });
    }

    /**
     * Metoda zwracająca książkę oraz wysyłająca potwierdzenie na maila
     */
    public void btnReturnClicked(ActionEvent actionEvent) throws IOException {
        TextInputDialog dialog = new TextInputDialog("Adres email");
        dialog.setTitle("Wysyłanie email");
        dialog.setHeaderText("Podaj email:");
        dialog.setContentText("Email:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            String encodedString = Base64.getEncoder().encodeToString(name.getBytes());
            byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
            String decodedString = new String(decodedBytes);
            SendEmail.sendInfoReturn(decodedString, bookList.get(numberBook - 1));
        });
        calendarRent.setValue(null);

        Book book = new Book(txtISBN.getText(), txtAuthor.getText(), txtTitle.getText(),
                txtPublicationYear.getText(), txtPublishingHouse.getText(), txtDescription.getText(), calendarRent.getValue());

        bookList.set(numberBook - 1, book);

        Path pathSaveModified = Paths.get(pathFile);
        List<String> lines = Files.readAllLines(pathSaveModified);
        lines.set(numberBook - 1, book.toString());
        Files.write(pathSaveModified, lines);

        btnReturn.setVisible(false);
    }

    /**
     * Metoda waliduje kontrolki (opis fakultatywny)
     */
    public boolean validate(){
        boolean correct = true;
        if(txtISBN == null || !txtISBN.getText().matches("\\d+"))
        {
            MsgHelper.showError("Niepoprawne dane", "Uzupełnij poprawnie numer ISBN");
            correct = false;
        }
        if(txtAuthor == null)
        {
            MsgHelper.showError("Niepoprawne dane", "Uzupełnij poprawnie dane autora");
            correct = false;
        }
        if(txtTitle == null) {
            MsgHelper.showError("Niepoprawne dane", "Uzupełnij tytuł książki");
            correct = false;
        }
        if(txtPublicationYear == null || !txtPublicationYear.getText().matches("\\d+")) {
            MsgHelper.showError("Niepoprawne dane", "Uzupełnij poprawnie rok wydania");
            correct = false;
        }
        if(txtPublishingHouse == null || !txtPublishingHouse.getText().matches("^[a-zA-Z]+$")) {
            MsgHelper.showError("Niepoprawne dane", "Uzupełnij poprawnie dane wydawnictwa");
            correct = false;
        }
        return correct;
    }

    /**
     * Metoda usuwa wprowadzone dane z kontrolek
     */
    public void cleanAllData(){
        txtISBN.setText("");
        txtAuthor.setText("");
        txtTitle.setText("");
        txtPublicationYear.setText("");
        txtPublishingHouse.setText("");
        txtDescription.setText("");
        calendarRent.setValue(null);
    }

    /**
     * Metoda blokuje/odblokowuje kontrolki
     */
    public  void disabledAllData(boolean isDisabled){
        txtISBN.setDisable(isDisabled);
        txtAuthor.setDisable(isDisabled);
        txtTitle.setDisable(isDisabled);
        txtPublicationYear.setDisable(isDisabled);
        txtPublishingHouse.setDisable(isDisabled);
        txtDescription.setDisable(isDisabled);
        calendarRent.setDisable(isDisabled);
    }

    /**
     * Metoda ustawiająca wszystkie dane dla danej książki
     * param numberBook - numer książki
     */
    public void setAllData(int numberBook){
        if(bookList.isEmpty()) {
            cleanAllData();
            return;
        }
        txtISBN.setText(bookList.get(numberBook - 1).isbn);
        txtAuthor.setText(bookList.get(numberBook - 1).author);
        txtTitle.setText(bookList.get(numberBook - 1).title);
        txtPublicationYear.setText(bookList.get(numberBook - 1).publicationYear);
        txtPublishingHouse.setText(bookList.get(numberBook - 1).publishHouse);
        txtDescription.setText(bookList.get(numberBook - 1).description);
        calendarRent.setValue(bookList.get(numberBook - 1).calendarRent);
    }

    /**
     * Metoda ustawiająca widoczność przycisków do wypożyczenia i zwrócenia
     */
    public void visabledButtonsRent(){
        if(calendarRent.getValue() == null) {
            btnReturn.setVisible(false);
            btnRent.setVisible(false);
        }
        else {
            btnRent.setVisible(true);
            btnReturn.setVisible(false);
        }
    }
}
