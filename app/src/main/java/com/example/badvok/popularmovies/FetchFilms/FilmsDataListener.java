package com.example.badvok.popularmovies.FetchFilms;

import java.util.ArrayList;

/**
 * Created by badvok on 29-Nov-15.
 */
public interface FilmsDataListener {

    void onFilmsPosterPathsPopulated(ArrayList<FilmsItem> data);

}
