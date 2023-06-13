package com.example.firstapp.controller;

import java.time.LocalDate;

public class Book {

    public String isbn;
    public String author;
    public String title;
    public String publicationYear;
    public String publishHouse;
    public String description;
    public LocalDate calendarRent;

    public Book (String isbn, String author,String title, String publicationYear, String publishHouse, String description, LocalDate calendarRent){
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.publicationYear = publicationYear;
        this.publishHouse = publishHouse;
        this.description = description;
        this.calendarRent = calendarRent;
    }

    @Override
    public String toString() {
        return isbn + "#" + author + "#" + title + "#" + publicationYear + "#" + publishHouse + "#" + description + "#" + calendarRent;
    }
}
