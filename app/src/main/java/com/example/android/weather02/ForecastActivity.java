package com.example.android.weather02;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Created by HP-PC on 27/10/2017.
 */

public class ForecastActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Weather>> {

    /** Tag for log messages */
    private static final String LOG_TAG = ForecastActivity.class.getName();

    /** URL for weather data from the Open Weather Map. */
    private static final String WEATHER_REQUEST_URL =
            "http://api.openweathermap.org/data/2.5/forecast?id=293396&APPID=fb2b6a8af170b708d8cc36292838bd6c";

    /**
     * Constant value for the weather loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int WEATHER_LOADER_ID = 2;

    /** Adapter for the list of weathers */
    private ForecastAdapter mForecastAdapter;


    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    @Override
    protected void  onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecast_activity);

        /** Find a reference to the {@link ListView} in the layout */
        ListView forecastListView = (ListView) findViewById(R.id.forecast_list);
        //Find a reference to the TextView of the forecast_empty_view in the layout
        mEmptyStateTextView = (TextView) findViewById(R.id.forecast_empty_view);
        forecastListView.setEmptyView(mEmptyStateTextView);

        //Create a new adapter that takes an empty list of weathers as input
        mForecastAdapter = new ForecastAdapter(this, new ArrayList<MasterWeather>());

        /**
         * Set the adapter on the {@link ListView}
         * so the list can be populated in the user interface
         */
        forecastListView.setAdapter(mForecastAdapter);

        //Get details on the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        //Get details oc the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        //If there is a network connection, fetch data.
        if (networkInfo != null && networkInfo.isConnected()) {
            //Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();
            //Initialize the loader. Pass in the int ID constant defined above and pass in null for
            //the bundle. Pass in this activity for the LoaderCallBacks parameter (which is valid
            //because this activity implements the LoaderCallBacks interface).
            loaderManager.initLoader(WEATHER_LOADER_ID, null, this);
        } else {
            //Otherwise, display error
            //First, hide loading indicator so error message will be visible
            View loadingIndicator = findViewById(R.id.forecast_loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            //Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

    }
    @Override
    public Loader<List<Weather>> onCreateLoader(int i, Bundle bundle){
        //Create a new loader for the given URL
        return new ForecastLoader(this, WEATHER_REQUEST_URL);
    }
    @Override
    public void onLoadFinished(Loader<List<Weather>> loader, List<Weather> weathers) {
        //Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.forecast_loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        //Set empty state text to display "No weathers found."
        mEmptyStateTextView.setText(R.string.no_weathers);
        //Clear the adapter of previous weathers data.
        mForecastAdapter.clear();
        /** If there is a valid list of {@link Weather}s, then add them to the adapter's
         * data set. This will trigger the ListView to update.
         */
        if (weathers != null && !weathers.isEmpty()) {
            mForecastAdapter.addAll(filterList(weathers));
        }


    }
    @Override
    public void  onLoaderReset(Loader<List<Weather>> loader) {
        //Loader reset, so we can clear out our existing data.
        mForecastAdapter.clear();
    }

    //filters the weather list to fit the weeks layout
    private List<MasterWeather> filterList(List<Weather> weathers) {
        List<MasterWeather> result = new ArrayList<>();
        List<Weather> resultWeather = new ArrayList<>();
        String thisDay = weathers.get(0).getShortDay();
        int minTemp = 100;
        int maxTemp = 0;
        for (int i = 0; i < weathers.size(); i++) {
            if (weathers.get(i).getShortDay().equals(thisDay)) {
                String tempTemp = weathers.get(i).getCleanTemp();
                if (parseInt(tempTemp) < minTemp) {
                    minTemp = parseInt(tempTemp);
                }
                if (parseInt(tempTemp) > maxTemp) {
                    maxTemp = parseInt(tempTemp);
                }
                resultWeather.add(new Weather(weathers.get(i).getDescription(),weathers.get(i).getRealTemp(), weathers.get(i).getTimeInMilliseconds()/1000, weathers.get(i).getCleanHumidity()));

            } else {
                result.add(new MasterWeather(weathers.get(i - 1).getDescription(), minTemp, maxTemp,
                        weathers.get(i - 1).getTimeInMilliseconds(),
                        weathers.get(i - 1).getCleanHumidity(),
                        resultWeather));
                thisDay = weathers.get(i).getShortDay();
                resultWeather = new ArrayList<>();
                minTemp = 100;
                maxTemp = 0;
                i = i - 1;
            }
        }
        return result;
    }

    // Set on item click to show detailed forecast for that day

    public void displayDetailedForecast(View view) {
        // Find the current MasterWeather that was clicked on
        Log.e(LOG_TAG,"item was clicked!!!");
        ListView listView = (ListView) view.findViewById(R.id.forecast_item_list);
        if (listView.getVisibility() == View.GONE) {
            listView.setVisibility(View.VISIBLE);
        } else {
            listView.setVisibility(View.GONE);
        }
    }
}
