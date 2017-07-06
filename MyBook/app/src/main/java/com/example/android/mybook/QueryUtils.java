package com.example.android.mybook;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anil on 2017-06-21.
 */

public class QueryUtils {

    /**LOG_TAG for log massage     */
    private static String LOG_TAG = QueryUtils.class.getSimpleName();


    /**public constructor     */
    public QueryUtils() {
    }

    /**Fetching list of Books     */
    public static List<Book> fetchBookList(String request){
        URL url = createUrl(request);
        String jsonResonse = "";
        try {
            jsonResonse = makeHttprequest(url);
        } catch (IOException e) {
            Log.v(LOG_TAG, "Problem retriving Json response", e);
        }

        List<Book> books = extractFromJsonResponse(jsonResonse);
        return books;
    }

    private static List<Book> extractFromJsonResponse(String jsonResonse) {
        //If jsonString is empty or null;
        if(TextUtils.isEmpty(jsonResonse)){
            return null;
        }
        /* Create new empty list */
        List<Book> books = new ArrayList<>();

        /**
         *try to extract jsonresponse and if exception then catch it
         */
        try{
           /*Convert string into readable form */
            JSONObject root = new JSONObject(jsonResonse);

            //extract array of items
            JSONArray items = root.getJSONArray("items");

            //extract object one by one from item
            for(int i=0; i < items.length(); i++){
                //extract object
                JSONObject object = items.getJSONObject(i);

                //obtain volume info
                JSONObject volume = object.getJSONObject("volumeInfo");

                //get name of book
                String title = volume.getString("title");

                //get Image
                JSONObject imagelinks = volume.getJSONObject("imageLinks");
                String image = imagelinks.getString("thumbnail");

                //get auther
                JSONArray a = volume.getJSONArray("authors");
                String auther = "Auther : " + a.getString(0);
                for(int j = 1; j < a.length(); j++){
                    auther = auther+ " and " + a.getString(j);
                }

                //get info url
                String url = volume.getString("infoLink");

                //get publisher
                String publisher = "Publisher : " + volume.getString( "publisher");

                Book AddBook = new Book(title, image, url, publisher, auther);
                books.add(AddBook);
            }
        } catch (JSONException e) {
            Log.v(LOG_TAG,"can't made a List due to jsonresponse is null");
        }

        return books;
    }

    /** Create response with creating connection*/
    private static String makeHttprequest(URL url) throws IOException {
        String jsonrsponse = "";

        //if url is null
        if(url == null){
            return  null;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000/*millisecond*/);
            urlConnection.setConnectTimeout(15000/*millisecond*/);
            urlConnection.connect();

            inputStream = urlConnection.getInputStream();
            jsonrsponse = readFromStream(inputStream);

        } catch (IOException e) {
            Log.v(LOG_TAG, "Problem retriving response", e);
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonrsponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**Creating URL for rsponse */
    private static URL createUrl(String request) {
        URL url = null;

        try{
            url = new URL(request);
        } catch (MalformedURLException e) {
            Log.v(LOG_TAG, "Error in creating URL", e);
        }

        return url;
    }
}
