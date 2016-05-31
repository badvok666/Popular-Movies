package com.example.badvok.popularmovies.DataBase;

import android.util.Log;

import com.example.badvok.popularmovies.AppDelegate;

import io.realm.Realm;
import io.realm.RealmObject;

/**
 * Created by simon on 05-May-16.
 */
public class Film extends RealmObject {

    private String title;
    private String id;
    private String poster_path;
    private String release_date;
    private String overview;
    private double vote_average;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public Film(String title, String id, String poster_path, String release_date, String overview, double vote_average) {
        this.vote_average = vote_average;
        this.title = title;
        this.id = id;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.overview = overview;
    }
    public Film() {

    }

    public static void clearTable(){
        //TODO compare id
        Realm realm = AppDelegate.getRealmInstance();
        try {
            realm.beginTransaction();

            realm.where(Film.class).findAll().clear();

            realm.commitTransaction();
        } catch (Exception e) {
            Log.e("Realm Error", "error" + e);
            realm.cancelTransaction();

        }

    }

    public static void commitNewFilm(Film film){

        Realm realm = AppDelegate.getRealmInstance();

        //TODO realm listener
        try{
            realm.beginTransaction();
            realm.copyToRealm(film);
            realm.commitTransaction();

        } catch (Exception e) {
            Log.e("RealmError", "error" + e);
            realm.cancelTransaction();

        }
    }
}
