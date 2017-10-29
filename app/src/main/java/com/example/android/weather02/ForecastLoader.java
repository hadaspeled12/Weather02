package com.example.android.weather02;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.parseInt;

/**
 * Created by HP-PC on 27/10/2017.
 *
 * Loads a list of weathers by using an AsyncTask to perform the
 * network request to the given URL.
 */

public class ForecastLoader extends AsyncTaskLoader<List<Weather>> {
    /** Tag for log messages */
    private static final String LOG_TAG = ForecastLoader.class.getName();
    /** Query URL */
    private String mUrl;

    /**
     * Constructs a new {@link ForecastLoader}.
     *
     * @param context of the activity
     * @param url to load data from
     */
    public ForecastLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
    /** This is on a background thread. */
    @Override
    public List<Weather> loadInBackground() {
        if (mUrl == null) {
            return null;
        }
        //Perform the network request, parse the response, and extract a list of weathers.
        List<Weather> weathers = QueryUtils.fetchWeatherData(mUrl);

        return weathers;
    }
}
