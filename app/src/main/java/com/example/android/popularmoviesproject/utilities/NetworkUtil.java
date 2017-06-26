package com.example.android.popularmoviesproject.utilities;

import android.net.Uri;

import com.squareup.picasso.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created on 6/14/2017.
 */

public class NetworkUtil {

    final static String MBD_BASE_URL =
            "https://api.themoviedb.org/3/movie/";
    final static String POPULAR = "popular";

    final static String API_KEY = "api_key";

    public static URL buildURL(String mdbPopularQuery) {
        Uri builtUri = Uri.parse(MBD_BASE_URL).buildUpon()
                .appendPath(POPULAR)
                .appendQueryParameter(API_KEY, "")
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }//buildUrl

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setDoOutput(true);
            InputStream in = urlConnection.getInputStream();
            // int response_code = urlConnection.getResponseCode(); Not sure if need, it appears to be doing the same thing as if statement below.

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                //Tutorial androidcss.com/android/fetch-json-data-android.
                InputStream input = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));

                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine())!= null){
                    result.append(line);
                }

                return (result.toString());
            }//if
            else {
                return null;
            }//Else
        }//Try

        finally {
            urlConnection.disconnect();
        } //Try/Finally

    }//getResponse

}//NetworkUtil


