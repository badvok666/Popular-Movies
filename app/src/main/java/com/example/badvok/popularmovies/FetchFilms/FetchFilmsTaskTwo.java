package com.example.badvok.popularmovies.FetchFilms;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.badvok.popularmovies.AppDelegate;
import com.example.badvok.popularmovies.DataBase.Favorites;
import com.example.badvok.popularmovies.DataBase.Film;
import com.example.badvok.popularmovies.FetchFilms.Interfaces.FetchFilmsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.realm.Realm;

/**
 * Created by badvok on 09-May-16.
 */
public class FetchFilmsTaskTwo extends AsyncTask<String, Void, Void> {


    FetchFilmsListener listener;

    public void setFetchFilmsListener(FetchFilmsListener listener){
        this.listener = listener;

    }

    @Override
    protected Void doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        ApiKey ApiKey = new ApiKey();
        String key = ApiKey.getKey();

        final String BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
        final String SORT_BY = "sort_by";
        final String API_KEY = "api_key";



        try {

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(SORT_BY, params[0])
                    .appendQueryParameter(API_KEY, key)
                    .build();


            URL url = new URL(builtUri.toString());
            Log.d("url", url + "");

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                return null;
            }


            try {
                createObject(buffer.toString());
            } catch (JSONException e) {
                Log.e("JsonError", e.getMessage(), e);
                e.printStackTrace();
            }

        } catch (IOException e) {
            Log.e("FetchFilmsTask", "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("FetchFilmsTask", "Error closing stream ", e);
                }
            }
        }



        return null;
    }

    private void createObject(String jsonStr) throws JSONException{

       // Film.clearTable();

        final String RESULTS = "results";
        final String TITLE = "title";
        final String ID = "id";
        final String POSTER_PATH = "poster_path";
        final String RELEASE_DATE = "release_date";
        final String OVERVIEW = "overview";
        final String VOTE_AVERAGE = "vote_average";

        JSONObject filmsListJSON = new JSONObject(jsonStr);
        JSONArray resultsArray = filmsListJSON.getJSONArray(RESULTS);

       // String[] posterPaths = new String[resultsArray.length()];
        Film.clearTable();
        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject filmJSON = resultsArray.getJSONObject(i);
          //  String posterPath = filmJSON.getString(POSTER_PATH);
//TODO look at singleton
            Film.addNewFilm(
                    filmJSON.getString(TITLE),
                    filmJSON.getString(ID),
                    filmJSON.getString(POSTER_PATH),
                    filmJSON.getString(RELEASE_DATE),
                    filmJSON.getString(OVERVIEW),
                    filmJSON.getDouble(VOTE_AVERAGE)
                  );

            Favorites.addToFavorites(filmJSON.getString(ID));


        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if(listener != null){
            listener.onComplete();
        }
        super.onPostExecute(aVoid);
    }
}
