package com.example.badvok.popularmovies.DataBase;

import com.example.badvok.popularmovies.AppDelegate;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by simon on 05-May-16.
 */
public class Review extends RealmObject{

    private String id;
    private String author;
    private String content;
    private String url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Review(String id, String author, String content, String url) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.url = url;
    }

    public Review(){

    }

    public static Review getReview(String id){
        Realm realm = AppDelegate.getRealmInstance();

        return realm.where(Review.class).equalTo("id",id).findFirst();

    }

    public static List<Review> getReviews(String id){
        Realm realm = AppDelegate.getRealmInstance();

        return realm.where(Review.class).equalTo("id",id).findAll();

    }
}
