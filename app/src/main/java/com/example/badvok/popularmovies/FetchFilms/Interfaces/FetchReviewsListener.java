package com.example.badvok.popularmovies.FetchFilms.Interfaces;

/**
 * Created by simon on 12-May-16.
 */
public interface FetchReviewsListener {

    void onComplete();
    void onError();
    void onProgress();

}
