package com.example.badvok.popularmovies;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by badvok on 28-Nov-15.
 */
public class DummyData {

    String filmName;
    int rating;
    String description;

    public DummyData(String filmName, int rating, String description) {
        this.filmName = filmName;
        this.rating = rating;
        this.description = description;
    }
    public DummyData(){
        addData();
    }

    private List<DummyData> films;

    private void addData(){
        films = new ArrayList<>();
        films.add(new DummyData("Star wars", 7 , "Movie aboot wars in starts and bad evil vs good side "));
        films.add(new DummyData("Hot Fuz",8,"Simon Peggington and nicolas cage get frosty"));
        films.add(new DummyData("Zombie Beavers", 10, "A zombie beaver goes to collage to study the anatomy of a cup"));
    }

    public List<DummyData> getAll(){
        return films;
    }
}
