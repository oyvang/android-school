package org.oyvang.library;

/**
 * Created by GeirMorten on 01.10.2014.
 */
public class Book {
    String title;
    String author;

    public Book(String title, String authors) {
        this.title = title;
        this.author = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthors() {
        return author;
    }

    public void setAuthors(String authors) {
        this.author = authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", authors=" + author +
                '}';
    }
}
