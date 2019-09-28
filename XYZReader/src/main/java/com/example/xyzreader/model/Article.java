package com.example.xyzreader.model;

public class Article {
    public String author;
    public String date;
    public String title;
    public String text;
    public String photoUrl;

    @Override
    public String toString() {
        return "Article{" +
                "author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
