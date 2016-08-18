package com.example.badvok.popularmovies.DataBase;

import android.util.Log;

import com.example.badvok.popularmovies.AppDelegate;

import java.util.ArrayList;
import java.util.List;

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
   // private boolean favorite;

    private static List<String>favorites;

  //  public boolean isFavorite() {
  //      return favorite;
  //  }

   // public void setFavorite(boolean favorite) {
  //      this.favorite = favorite;
  //  }

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
       // this.favorite = favorite;
    }
    public Film() {

    }

    public static void clearTable(){
        //TODO compare id
        Realm realm = AppDelegate.getRealmInstance();
      //  List<Film>films = realm.where(Film.class).equalTo("favorite",true).findAll();
        //TODO is this how to check boolean in realm?
      //  favorites = new ArrayList<String>();
     //   favorites.clear();
     //   for (int i = 0; i < films.size(); i++) {
     //       favorites.add(films.get(i).getId());
     //   }


        try {
            realm.beginTransaction();

            realm.where(Film.class).findAll().clear();

            realm.commitTransaction();
        } catch (Exception e) {
            Log.e("Realm Error", "error" + e);
            realm.cancelTransaction();

        }
    }

    public static void addNewFilm(String title, String id, String poster_path, String release_date, String overview, double vote_average){
        Realm realm = AppDelegate.getRealmInstance();

        try{
            //realm.beginTransaction();
            Log.d("edit", "adding: " +title );
            Film film = new Film();
            film.setTitle(title);
            film.setId(id);
            film.setPoster_path(poster_path);
            film.setRelease_date(release_date);
            film.setOverview(overview);
            film.setVote_average(vote_average);
         /*   for (int i = 0; i < favorites.size(); i++) {
                if (favorites.get(i).equals(id)){
                    film.setFavorite(true);
                }else{
                    film.setFavorite(false);
                }
            }
            if (favorites.size()==0){
                film.setFavorite(false);
            }*/

            Film.commitNewFilm(film);
            //realm.commitTransaction();

        }catch (Exception e){
            Log.e("RealmError", "error" + e);
            realm.cancelTransaction();

        }

        /*
        if(realm.where(Film.class).equalTo("id",id).findFirst() != null){
            Log.d("edit", "editing: " + realm.where(Film.class).equalTo("id",id).findFirst().getId() );
            try{
                Film toEdit;
                realm.beginTransaction();
                toEdit = realm.where(Film.class).equalTo("id",id).findFirst();

                toEdit.setTitle(title);
                toEdit.setId(id);
                toEdit.setPoster_path(poster_path);
                toEdit.setRelease_date(release_date);
                toEdit.setOverview(overview);
                toEdit.setVote_average(vote_average);
                realm.commitTransaction();

            }catch (Exception e){
                Log.e("RealmError", "error" + e);
                realm.cancelTransaction();

            }
        }else{
            Log.d("edit", "new: -- " + title );
            try{
                //realm.beginTransaction();

                Film film = new Film();
                film.setTitle(title);
                film.setId(id);
                film.setPoster_path(poster_path);
                film.setRelease_date(release_date);
                film.setOverview(overview);
                film.setVote_average(vote_average);
                film.setFavorite(false);

                Film.commitNewFilm(film);
                //realm.commitTransaction();

            }catch (Exception e){
                Log.e("RealmError", "error" + e);
                realm.cancelTransaction();

            }
        }*/
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

    public static void updateFavorite(String filmId, Boolean isFavorite){
       // Realm realm = AppDelegate.getRealmInstance();

        favorites.add(filmId);

      /*  try{
            Film toEdit;
            realm.beginTransaction();
            toEdit = realm.where(Film.class).equalTo("id",filmId).findFirst();
            toEdit.setFavorite(isFavorite);

            realm.commitTransaction();
        }catch (Exception e){
            Log.e("Realm Error", "error" + e);
            realm.cancelTransaction();
        }*/
    }
}
