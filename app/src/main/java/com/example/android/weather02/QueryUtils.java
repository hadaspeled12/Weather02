package com.example.android.weather02;

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
 * Created by HP-PC on 27/10/2017.
 *
 * Helper methods related to requesting and receiving weather data.
 */

public class QueryUtils {
    /** Tag for the log messages */
    private static final String LOG_TAG = QueryUtils.class.getSimpleName();
    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils(){
    }
    /**
     * Query the forecast data set and return a list of {@link Weather} objects.
     */
    public static List<Weather> fetchWeatherData(String requestUrl) {
        //Create URL object
        URL url = createUrl(requestUrl);
        //Perform HTTPs request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTPs request.", e);
        }
        /** Extract relevant fields from the JSON response and create a list of {@link Weather}s */
        List<Weather> weathers = extractFeatureFromJson(jsonResponse);
        /** Return the list of {@link Weather}s */
        return weathers;
    }
    /** Returns new URL object from the given string URL. */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL", e);
        }
        return url;
    }
    /** Make an HTTPs request to the given URL and return a String as the response. */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        //If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000 /*milliseconds */);
            urlConnection.setConnectTimeout(1500/*milliseconds*/);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //If the request was successful (response code 200),
            //then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the weather JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                //Closing the input stream could throw an IOException, which is why
                //the makeHttpRequest(Url url) method signature specifies than an IOException
                //could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
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
    /**
     * Return a list of {@link Weather} objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Weather> extractFeatureFromJson(String weatherJSON) {
        //If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(weatherJSON)) {
            return null;
        }
        //Create an empty ArryList that we can start adding weathers to
        List<Weather> weathers = new ArrayList<>();
        //Try to parse the JSON response string. If there's a problem with the way the JSON
        //is formatted a JSONException exception object will be thrown.
        try {
            //Convert SAMPLE_JSON_RESPONSE String into a JSONObject
            JSONObject sampleJsonResponse = new JSONObject(weatherJSON);
            //Extract "list" JSONArray
            JSONArray list = sampleJsonResponse.getJSONArray("list");
            //Loop through each feature in the array
            for (int i = 0; i < list.length(); i++) {
                //Get weather JSONObject at position i
                JSONObject c = list.getJSONObject(i);
                //Extract "dt" for time in milliseconds
                long timeInMilliseconds = c.getLong("dt");
                //Get "main" JSONObject
                JSONObject main = c.getJSONObject("main");
                //Extract "temp" for the temperarure
                double temp = main.getDouble("temp");
                //Extract "humidity" for the humidity
                String humidity = main.getString("humidity");
                //Get "weather JSONArray
                JSONArray weather = c.getJSONArray("weather");
                //Extract "main" for description
                String description = weather.getJSONObject(0).getString("main");
                //Create Weather java object from timeInMilliseconds, temp, humidity, description
                //and add it to a list of weathers
                weathers.add(new Weather(description, temp, timeInMilliseconds, humidity));
            }
        } catch (JSONException e) {
            //If an error is thrown when executing any of the above statments in the "try" block,
            // catch the exception here, so app doesn't crash. Print a log message
            //with the message from the exception.
            Log.e(LOG_TAG, "Problem parsing the weather JSON results", e);
        }
        //Return the list of weathers
        return weathers;
    }
}
