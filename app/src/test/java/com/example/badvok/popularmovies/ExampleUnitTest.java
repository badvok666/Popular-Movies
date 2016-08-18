package com.example.badvok.popularmovies;

import com.example.badvok.popularmovies.DataBase.Film;

import org.junit.Test;

import java.io.File;
import java.util.List;

import io.realm.Realm;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
        Realm realm = AppDelegate.getRealmInstance();

        List<Film> films = realm.where(Film.class).findAll();

        assertEquals(null,films);
    }
}