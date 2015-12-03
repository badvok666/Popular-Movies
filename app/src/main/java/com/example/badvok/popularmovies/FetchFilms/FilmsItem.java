package com.example.badvok.popularmovies.FetchFilms;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by badvok on 29-Nov-15.
 */
public class FilmsItem implements Parcelable {

    String title;
    String id;
    String poster_path;
    String release_date;
    String overview;
    double vote_average;

    public FilmsItem(String title, String id, String poster_path, String release_date, String overview, double vote_average) {
        this.title = title;
        this.id = id;
        this.poster_path = poster_path;
        this.release_date = release_date;
        this.overview = overview;
        this.vote_average = vote_average;
    }

    public FilmsItem(Parcel in) {
        readFromParcel(in);
    }

    public String getId() {
        return id;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(id);
        dest.writeString(poster_path);
        dest.writeString(release_date);
        dest.writeString(overview);
        dest.writeDouble(vote_average);

    }

    private void readFromParcel(Parcel in){

        title = in.readString();
        id = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        overview = in.readString();
        vote_average = in.readDouble();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator CREATOR =
            new Parcelable.Creator() {
                public FilmsItem createFromParcel(Parcel in) {
                    return new FilmsItem(in);
                }

                public FilmsItem[] newArray(int size) {
                    return new FilmsItem[size];
                }
            };
}
