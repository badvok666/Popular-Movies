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

import com.example.badvok.popularmovies.FetchFilms.FetchFilmsTask;
import com.example.badvok.popularmovies.FetchFilms.FilmsDataListener;
import com.example.badvok.popularmovies.FetchFilms.FilmsItem;
import com.example.badvok.popularmovies.RecyclerView.FilmRecyclerViewAdapter;
import com.example.badvok.popularmovies.RecyclerView.FilmRecyclerViewClickListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    ArrayList<FilmsItem> films;
    String ORDER_PARAMATER = "popularity.desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.filmRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        refreshData(ORDER_PARAMATER);


        onClickListeners();

    }

    public void onClickListeners() {

        mRecyclerView.addOnItemTouchListener(
                new FilmRecyclerViewClickListener(getApplicationContext(), new FilmRecyclerViewClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Log.d("log", "asd" + position);
                        FilmsItem fm = films.get(position);
                        Intent intent = new Intent(MainActivity.this, FilmActivity.class)
                                .putExtra("com.example.badvok.pupularmovies.FilmsItem", fm);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.refresh_list) {
            refreshData(ORDER_PARAMATER);
        } else if (id == R.id.order_by_pop_desc) {
            ORDER_PARAMATER = "popularity.desc";
            refreshData(ORDER_PARAMATER);
        } else if (id == R.id.order_by_highest_rated) {
            ORDER_PARAMATER = "rating.desc";
            refreshData(ORDER_PARAMATER);
        }

        return super.onOptionsItemSelected(item);
    }
    

    public void refreshData(String order_param) {
        FetchFilmsTask fft = new FetchFilmsTask();
        fft.execute(order_param);
        fft.setFilmsDataListener(new FilmsDataListener() {
            @Override
            public void onFilmsPosterPathsPopulated(ArrayList<FilmsItem> data) {
                films = data;
                FilmRecyclerViewAdapter frva = new FilmRecyclerViewAdapter(films);
                mRecyclerView.setAdapter(frva);
            }
        });
    }
}
