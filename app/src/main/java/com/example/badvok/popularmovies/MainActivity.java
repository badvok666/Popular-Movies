package com.example.badvok.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.badvok.popularmovies.DataBase.Film;
import com.example.badvok.popularmovies.DataBase.Review;
import com.example.badvok.popularmovies.FetchFilms.FetchFilmsTask;
import com.example.badvok.popularmovies.FetchFilms.FetchFilmsTaskTwo;
import com.example.badvok.popularmovies.FetchFilms.FetchReviewTask;
import com.example.badvok.popularmovies.FetchFilms.FilmsDataListener;
import com.example.badvok.popularmovies.FetchFilms.FilmsItem;
import com.example.badvok.popularmovies.FetchFilms.Interfaces.FetchFilmsListener;
import com.example.badvok.popularmovies.RecyclerView.FilmRecyclerViewAdapter;
import com.example.badvok.popularmovies.RecyclerView.FilmRecyclerViewClickListener;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    List<Film>films;
  //  String ORDER_PARAMATER = "popularity.desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.filmRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        refreshData(AppDelegate.ORDER_PERAM);
        onClickListeners();
    }

    public void onClickListeners() {
        mRecyclerView.addOnItemTouchListener(
                new FilmRecyclerViewClickListener(getApplicationContext(), new FilmRecyclerViewClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        String filmId = films.get(position).getId();
                        Intent intent = new Intent(MainActivity.this, FilmActivity.class).putExtra("com.example.badvok.pupularmovies.Film", filmId);
                        startActivity(intent);

                    }
                })
        );

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Order Paramater and refresh are located here
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.refresh_list) {
            refreshData(AppDelegate.ORDER_PERAM);
        } else if (id == R.id.order_by_pop_desc) {
            AppDelegate.showOnlyFavorites = false;
            AppDelegate.ORDER_PERAM = AppDelegate.POPULARITY_DECENDING;
            refreshData( AppDelegate.ORDER_PERAM);
        } else if (id == R.id.order_by_highest_rated) {
            AppDelegate.showOnlyFavorites = false;
            AppDelegate.ORDER_PERAM = AppDelegate.HIGHEST_RATED;
            refreshData( AppDelegate.ORDER_PERAM);
        } else if (id == R.id.order_by_favorites){
            AppDelegate.showOnlyFavorites = true;
            refreshData(AppDelegate.ORDER_PERAM);
        }

        return super.onOptionsItemSelected(item);
    }

    public void getOrderParam(){

    }

    /**
     * Gets the data on AyncTask based on the provided order paramater
     * @param order_param
     */
    public void refreshData(String order_param) {

        FetchFilmsTaskTwo fetchFilmsTaskTwo = new FetchFilmsTaskTwo();
        fetchFilmsTaskTwo.execute(order_param);
        fetchFilmsTaskTwo.setFetchFilmsListener(new FetchFilmsListener() {
            @Override
            public void onComplete() {

                Realm realm = AppDelegate.getRealmInstance();

                if(AppDelegate.showOnlyFavorites){
                    films = realm.where(Film.class).equalTo("favorite",true).findAll();
                }else{
                    films = realm.where(Film.class).findAll();
                }


                FilmRecyclerViewAdapter frva = new FilmRecyclerViewAdapter(films);

                for (int i = 0; i < films.size(); i++) {
                    Log.d("filmitem", "Films: " + films.get(i).getTitle());
                }
                mRecyclerView.setAdapter(frva);
        }

            @Override
            public void onError() {

            }

            @Override
            public void onProgress() {

            }
        });

    }
}
