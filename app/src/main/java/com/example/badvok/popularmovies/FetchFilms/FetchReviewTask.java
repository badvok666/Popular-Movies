package com.example.badvok.popularmovies.FetchFilms;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.badvok.popularmovies.AppDelegate;
import com.example.badvok.popularmovies.DataBase.Review;
import com.example.badvok.popularmovies.FetchFilms.Interfaces.FetchReviewsListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.realm.Realm;

/**
 * Created by simon on 08-May-16.
 */
public class FetchReviewTask extends AsyncTask<String, Void, Void>{

    String reviewJsonStr = null;
    FetchReviewsListener listener;
    int age;

    public void setFetchReviewsListener (FetchReviewsListener listener){
        this.listener = listener;
    }

    @Override
    protected Void doInBackground(String... params) {

        Review.clearReviewsForFilm(params[1]);


        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        ApiKey apiKey = new ApiKey();
        String key = apiKey.getKey();

        final String BASE_URL = "http://api.themoviedb.org/3/movie/";
        final String SORT_BY = "sort_by";
        final String REVIEW = "reviews";
        final String API_KEY = "api_key";
        final String MOVIE_ID = "209112";

        try {
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(MOVIE_ID)
                    .appendPath(REVIEW)
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

            reviewJsonStr = buffer.toString();


        }catch (IOException e){
            Log.e("FetchFilmsTask", "Error ", e);
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("FetchReviewTask", "Error closing stream ", e);
                }
            }
        } try {
            createObject(reviewJsonStr,params[1]);
        } catch (JSONException e) {
            Log.e("JsonError", e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    private void createObject(String jsonStr, String filmId)throws JSONException{

        String RESULTS = "results";
        String ID = "id";
        String AUTHOR = "author";
        String CONTENT = "content";
        String URL = "url";

        JSONObject reviewListJson = new JSONObject(jsonStr);
        JSONArray resultsArray = reviewListJson.getJSONArray(RESULTS);

        for (int i = 0; i < resultsArray.length(); i++) {
            JSONObject reviewJSON = resultsArray.getJSONObject(i);


            Review review = new Review(
                    filmId,
                    reviewJSON.getString(ID),
                    reviewJSON.getString(AUTHOR),
                    reviewJSON.getString(CONTENT),
                    reviewJSON.getString(URL)
            );
            Realm realm = AppDelegate.getRealmInstance();
            try{
                realm.beginTransaction();
                realm.copyToRealm(review);
                realm.commitTransaction();

            } catch (Exception e) {
                Log.e("RealmError", "error" + e);
                realm.cancelTransaction();
            }
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (listener != null) {

            listener.onComplete();
        }
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
}
