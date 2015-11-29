package com.example.badvok.popularmovies;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by badvok on 28-Nov-15.
 */
public class FetchFilmsTask extends AsyncTask<Void, Void,Void> {

    String filmsJson = null;

    @Override
    protected Void doInBackground(Void... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try{
            URL url = new URL("APICALLHERE");

            urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if(inputStream == null){
                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine())!= null){
                buffer.append(line+"\n");
            }

            if(buffer.length() == 0){
                return null;
            }

            filmsJson = buffer.toString();

            Log.d("json","Films json string: " + filmsJson);

        }catch (IOException e){
            Log.e("FetchFilmsTask", "Error ", e);
        }finally {
            if(urlConnection != null){
                urlConnection.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                }catch (IOException e){
                    Log.e("FetchFilmsTask", "Error closing stream ", e);
                }
            }
        }

    /*    try{
            return null;
        }catch (JSONException e){
            Log.e("JsonError",e.getMessage(), e);
            e.printStackTrace();
        }*/

        return null;
    }



    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
