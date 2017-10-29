package com.example.android.weather02;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Weather>> {

    /** Tag for log messages */
    private static final String LOG_TAG = MainActivity.class.getName();

    /** URL for weather data from the Open Weather Map. */
    private static final String WEATHER_REQUEST_URL =
            "http://api.openweathermap.org/data/2.5/forecast?id=293396&APPID=fb2b6a8af170b708d8cc36292838bd6c";

    /**
     * Constant value for the weather loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int WEATHER_LOADER_ID = 1;

    /** Adapter for the list of weathers */
    private TodayAdapter mTodayAdapter;

    /** TextView that is displayed when the list is empty */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Find a reference to the {@link ListView} in the layout */
        ListView todayListView = (ListView) findViewById(R.id.today_list);
        //Find a reference to the TextView of the empty_view in the layout
        mEmptyStateTextView = (TextView) findViewById(R.id.today_empty_view);
        todayListView.setEmptyView(mEmptyStateTextView);

        //Create a new adapter that takes an empty list of weathers as input
        mTodayAdapter = new TodayAdapter(this, new ArrayList<Weather>());

        /**
         * Set the adapter on the {@link ListView}
         * so the list can be populated in the user interface
         */
        todayListView.setAdapter(mTodayAdapter);

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
            View loadingIndicator = findViewById(R.id.today_loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            //Update empty state with no connection error message
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        // Setup button to open ForecastActivity
        Button forecastButton = (Button) findViewById(R.id.today_forecast_button);
        // Set a click listener on that View
        forecastButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the forecastButton is clicked on.
            @Override
            public void onClick(View view) {
                /** Create a new intent to open the {@link ForecastActivity}*/
                Intent forecastIntent = new Intent(MainActivity.this, ForecastActivity.class);
                // Start the new activity
                startActivity(forecastIntent);
            }
        });
    }
    @Override
    public Loader<List<Weather>> onCreateLoader(int i, Bundle bundle) {
        //Create a new loader for the given URL
        return new TodayLoader(this, WEATHER_REQUEST_URL);
    }
    @Override
    public void onLoadFinished(Loader<List<Weather>> loader, List<Weather> weathers) {
        //Hide loading indicator because the data has been loaded
        View loadingIndicator = findViewById(R.id.today_loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        //Set empty state text to display "No weathers found."
        mEmptyStateTextView.setText(R.string.no_weathers);
        //Clear the adapter of previous weathers data.
        mTodayAdapter.clear();
        /** If there is a valid list of {@link Weather}s, then add them to the adapter's
         * data set. This will trigger the ListView to update.
         */
        if (weathers != null && !weathers.isEmpty()) {
            mTodayAdapter.addAll(weathers);
        }

        /** Updates the information layout */
        //Find the TextView in the activity_main.xml layout with the ID day_of_the_week_text.
        TextView dayTextView = (TextView) findViewById(R.id.today_day_of_the_week_text);
        //Get the full day of the week from the first weather in the weathers list
        //and set this text on the day of te week TextView.
        dayTextView.setText(weathers.get(0).getFullDay());

        //Find the TextView in the activity_main.xml layout with the ID date_text.
        TextView dateTextView = (TextView) findViewById(R.id.today_date_text);
        //Get the date from the first weather in the weathers list
        //and set this text on the date TextView.
        dateTextView.setText(weathers.get(0).getDate());

        //Find the TextView in the activity_main.xml layout with the ID place_text.
        TextView placeTextView = (TextView) findViewById(R.id.today_place_text);
        //Get the place from the settings
        //and set this text on the place TextView.
        placeTextView.setText(R.string.dummy_place);


    }
    @Override
    public void  onLoaderReset(Loader<List<Weather>> loader) {
        //Loader reset, so we can clear out our existing data.
        mTodayAdapter.clear();
    }
}
