package com.example.android.weather02;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP-PC on 27/10/2017.
 *
 * An {@link ForecastAdapter} knows how to create a list item layout for each forecast
 * in the upcoming days (a list of {@link Weather} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */

public class ForecastAdapter extends ArrayAdapter<MasterWeather> {
    /** Adapter for the list of weathers */
    private ForecastSuperAdapter mForecastSupreAdapter;

    private Context mContext;

    /**
     * Constructs a new {@link ForecastAdapter}.
     *
     * @param context of the app
     * @param weathers is the list of weathers which is the data source of the adapter.
     */
    public ForecastAdapter(Context context, List<MasterWeather> weathers) {
        super(context, 0, weathers);
        mContext = context;
        Log.e("ForecastAdapter", "Context" + context);
    }

    /**
     * Returns a list item view that displays information about the weather at the given position
     * in the list of weathers.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Check if there is an existing list item view (called convertView) that we can reuse,
        //otherwise, if convertView is null, then inflate a new list item layout.
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.forecast_list_item, parent, false);
        }
        /** Get the {@link Weather} object located at this position in the list */
        MasterWeather currentMasterWeather = getItem(position);

        //Find the TextView in the forecast_list_item.xml layout with the ID day_text_view.
        TextView dayTextView = (TextView) listItemView.findViewById(R.id.forecast_item_day_text_view);
        //Get the short day from the currentWeather object and set this text on
        //the day TextView.
        dayTextView.setText(currentMasterWeather.getShortDay());

        //Find the TextView in the forecast_list_item.xml layout with the ID date_text_view.
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.forecast_item_date_text_view);
        //Get the date from the currentWeather object and set this text on
        //the date TextView.
        dateTextView.setText(currentMasterWeather.getDate());

        //Find the ImageView in the forecast_list_item.xml layout with the ID description_image.
        ImageView descriptionImageView = (ImageView) listItemView.findViewById(R.id.forecast_item_description_image);
        //Get the image's resource from the currentWeather object and set this resource on
        //the description ImageView.
        descriptionImageView.setImageResource(currentMasterWeather.getImageResourceId());

        //Find the TextView in the forecast_list_item.xml layout with the ID description_text_view.
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.forecast_item_description_text_view);
        //Get the description from the currentWeather object and set this text on
        //the description TextView.
        descriptionTextView.setText(currentMasterWeather.getDescription());

        //Find the TextView in the forecast_list_item.xml layout with the ID min_temp_text_view.
        TextView minTempTextView = (TextView) listItemView.findViewById(R.id.forecast_item_min_temp_text_view);
        //Get the minimum temperature from the currentWeather object and set this text on
        //the minimum temperature TextView.
        minTempTextView.setText(currentMasterWeather.getMinTemp());

        //Find the TextView in the forecast_list_item.xml layout with the ID max_temp_text_view.
        TextView maxTempTextView = (TextView) listItemView.findViewById(R.id.forecast_item_max_temp_text_view);
        //Get the maximum temperature from the currentWeather object and set this text on
        //the maximum temperature TextView.
        maxTempTextView.setText(currentMasterWeather.getMaxTemp());

        //Find the TextView in the forecast_list_item.xml layout with the ID humidity_text_view.
        TextView humidityTextView = (TextView) listItemView.findViewById(R.id.forecast_item_humidity_text_view);
        //Get the humidity from the currentWeather object and set this text on
        //the humidity TextView.
        humidityTextView.setText(currentMasterWeather.getHumidity());

        //Find the ListView in the forecast_list_item.xml layout with the ID list.
        ListView listView = (ListView) listItemView.findViewById(R.id.forecast_item_list);
        mForecastSupreAdapter = new ForecastSuperAdapter(mContext, currentMasterWeather.getListOfWeathers());
        listView.setAdapter(mForecastSupreAdapter);
        listView.setVisibility(View.GONE);

        Log.e("ForecastAdapter","forecast size:" + currentMasterWeather.getListOfWeathers().size());

        // Return the whole list item layout so that it can be shown in the ListView.
        return listItemView;
    }

}
