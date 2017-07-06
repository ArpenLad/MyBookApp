package com.example.android.mybook;

/**
 * Created by Anil on 2017-06-21.
 */

public class Book {

    //Book title
    private String Book_Title;

    //Book Image
    private String Book_image;

    //InfoUrl
    private String Url;

    //Publisher
    private String Publisher;

    //Auther
    private String Auther;

    /**
     * Public Constructor
     * @param book_Title
     */
    public Book(String book_Title, String book_image, String url, String publisher, String auther) {
        Book_Title = book_Title;
        Book_image = book_image;
        Url = url;
        Publisher = publisher;
        Auther = auther;
    }

    /**Get Book Title     */
    public String getBook_Title() {
        return Book_Title;
    }

    /**Get Book Image    */
    public String getBook_image() {
        return Book_image;
    }

    public String getUrl() {
        return Url;
    }

    public String getPublisher() {
        return Publisher;
    }

    public String getAuther() {
        return Auther;
    }
}
