package com.example.gogos.bookapp;

/**
 * Created by gogos on 2017-11-30.
 */

public class BookList {
    private String bookTittle;
    private String BookAuthor;
    private String publishedDate;

    public BookList(String bookTittle, String bookAuthor, String publishedDate) {
        this.bookTittle = bookTittle;
        BookAuthor = bookAuthor;
        this.publishedDate = publishedDate;
    }

    public String getBookTittle() {
        return bookTittle;
    }

    public void setBookTittle(String bookTittle) {
        this.bookTittle = bookTittle;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        BookAuthor = bookAuthor;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
}
