package com.example.xyzreader.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Article implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //Warning, changing this requires changes to the CREATOR
        dest.writeString(author);
        dest.writeString(date);
        dest.writeString(title);
        dest.writeString(text);
        dest.writeString(photoUrl);
    }

    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        public Article createFromParcel(Parcel in) {
            //Warning, any changes here need to be reflected in writeToParcel
            final Article article = new Article();
            article.author = in.readString();
            article.date = in.readString();
            article.title = in.readString();
            article.text = in.readString();
            article.photoUrl = in.readString();
            return article;
        }

        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}
