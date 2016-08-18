package com.example.badvok.popularmovies.DataBase;

import android.util.Log;

import com.example.badvok.popularmovies.AppDelegate;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

/**
 * Created by badvok on 17-Aug-16.
 */
public class Favorites extends RealmObject {

    @PrimaryKey
    private String filmId;
    private Boolean favorite;

    public Favorites(){

    }

    public Favorites(String filmId, Boolean isFavorite){
        this.favorite = isFavorite;
        this.filmId = filmId;
    }


    public String getFilmId() {
        return filmId;
    }

    public void setFilmId(String filmId) {
        this.filmId = filmId;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public static void editFavorite(String id, Boolean isFavorite){
        Realm realm = AppDelegate.getRealmInstance();

        try{
            Favorites toEdit;
            realm.beginTransaction();
            toEdit = realm.where(Favorites.class).equalTo("filmId",id).findFirst();

            toEdit.setFavorite(isFavorite);
            realm.commitTransaction();

        }catch (Exception e){
            Log.e("RealmError", "error" + e);
            realm.cancelTransaction();

        }

    }

    public static void addToFavorites(String id){
        Realm realm = AppDelegate.getRealmInstance();

        Favorites oldFavorite = realm.where(Favorites.class).equalTo("filmId",id).findFirst();

        Boolean isFavorite = false;

        if(oldFavorite != null){
            if(oldFavorite.getFavorite()){
                isFavorite = true;
            }else{
                isFavorite = false;
            }


        }

        try{
            realm.beginTransaction();
            Favorites newFavorite = new Favorites();

            newFavorite.setFavorite(isFavorite);
            newFavorite.setFilmId(id);

            realm.copyToRealm(newFavorite);
            realm.commitTransaction();

        }catch (Exception e){
            Log.e("RealmError", "error" + e);
            realm.cancelTransaction();

        }

       // List<Favorites> favorites = realm.where(Favorites.class).findAll();
      //  for (int i = 0; i < favorites.size(); i++) {
      //      Log.d("favorites2","size: " +favorites.size() + " favorites: " + favorites.get(i).getFilmId()+ " :: " + favorites.get(i).getFavorite());
     //   }




    }

}
