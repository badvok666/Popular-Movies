package com.example.badvok.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.badvok.popularmovies.DataBase.Favorites;
import com.example.badvok.popularmovies.DataBase.Film;
import com.example.badvok.popularmovies.DataBase.Review;
import com.example.badvok.popularmovies.DataBase.Trailer;
import com.example.badvok.popularmovies.FetchFilms.FetchReviewTask;
import com.example.badvok.popularmovies.FetchFilms.FetchTrailerTask;
import com.example.badvok.popularmovies.FetchFilms.Interfaces.FetchReviewsListener;
import com.example.badvok.popularmovies.FetchFilms.Interfaces.FetchTrailerListener;
import com.example.badvok.popularmovies.RecyclerView.RecyclerViewInterface;
import com.example.badvok.popularmovies.RecyclerView.FilmDetailRecyclerAdapter;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by badvok on 29-Nov-15.
 */
public class FilmActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing fragment_film
     * a detail view for films
     */
    public static class PlaceholderFragment extends Fragment implements RecyclerViewInterface {

        FilmDetailRecyclerAdapter filmDetailRecyclerAdapter;

        List<Review> mReviews = null;
        RecyclerView recyclerView;

        Boolean fetchedTrailers = false;
        Boolean fetchedReviews = false;


        @Override
        public void toggleRecyclerView(boolean showReviews) {

            filmDetailRecyclerAdapter.notifyDataSetChanged();

        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_film, container, false);
            Intent intent = getActivity().getIntent();

            recyclerView = (RecyclerView) rootView.findViewById(R.id.trailerRecyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(AppDelegate.ctx, 1));


            recyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);

                }
            });


            if (intent != null && intent.hasExtra("com.example.badvok.pupularmovies.Film")) {
                final Realm realm = AppDelegate.getRealmInstance();

                final String filmId = intent.getStringExtra("com.example.badvok.pupularmovies.Film");

                FetchTrailerTask fetchTrailerTask = new FetchTrailerTask();
                fetchTrailerTask.execute(AppDelegate.ORDER_PERAM, filmId);
                fetchTrailerTask.setFetchTrailerListener(new FetchTrailerListener() {
                    @Override
                    public void onComplete() {

                        fetchedTrailers = true;
                        if(fetchedReviews){
                            setUpRecyclerView(filmId);
                        }
                        Log.d("Film", "complete on trailer");
                    }

                    @Override
                    public void onError() {
                        Log.d("Film", "error on trailer");
                    }

                    @Override
                    public void onProgress() {

                    }
                });

                FetchReviewTask fetchReviewTask = new FetchReviewTask();
                fetchReviewTask.execute(AppDelegate.ORDER_PERAM, filmId);
                fetchReviewTask.setFetchReviewsListener(new FetchReviewsListener() {
                    @Override
                    public void onComplete() {
                        fetchedReviews = true;
                        if(fetchedTrailers){
                            setUpRecyclerView(filmId);
                        }

                        Log.d("Film", "complete on review");
                    }

                    @Override
                    public void onError() {
                        Log.d("Film", "error on review");
                    }

                    @Override
                    public void onProgress() {

                    }
                });
            }
            return rootView;
        }

        public void setUpRecyclerView(String filmId){

            final Realm realm = AppDelegate.getRealmInstance();

            List<Review> reviewslist = realm.where(Review.class).findAll();

            RealmResults<Favorites> result = realm.where(Favorites.class).equalTo("filmId",filmId).findAll();

            if(!result.isEmpty())
            {
                boolean isFavorite = result.first().getFavorite();
                             filmDetailRecyclerAdapter = new FilmDetailRecyclerAdapter(realm.where(Trailer.class).equalTo("filmId",filmId).findAll(),
                        realm.where(Review.class).equalTo("filmId",filmId).findAll(),
                        realm.where(Film.class).equalTo("id",filmId).findFirst(),
                        isFavorite,
                        PlaceholderFragment.this);

                recyclerView.setAdapter(filmDetailRecyclerAdapter);
            }
            else
            {
                //TODO handel
            }

           //boolean isFavorite = realm.where(Favorites.class).equalTo("filmId",filmId).isNotNull("filmId").findFirst().getFavorite();



        }

        public void showReviews(){

            for (int i = 0; i < mReviews.size(); i++) {
                Log.d("reviews",mReviews.get(i).getAuthor());
            }
        }

    }

}
