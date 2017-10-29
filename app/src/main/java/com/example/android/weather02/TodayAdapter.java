package com.example.android.weather02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by HP-PC on 27/10/2017.
 *
 * An {@link TodayAdapter} knows how to create a list item layout for each forecast
 * in the upcoming X hours (a list of {@link Weather} objects).
 *
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */

public class TodayAdapter extends ArrayAdapter<Weather> {

    /**
     * Constructs a new {@link TodayAdapter}.
     *
     * @param context of the app
     * @param weathers is the list of weathers which is the data source of the adapter.
     */
    public TodayAdapter(Context context, List<Weather> weathers) {
        super(context, 0, weathers);
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
                    R.layout.today_list_item, parent, false);
        }
        /** Get the {@link Weather} object located at this position in the list */
        Weather currentWeather = getItem(position);

        //Find the TextView in the today_list_item.xml layout with the ID time_text_view.
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.today_item_time_text_view);
        //Get the time from the currentWeather object and set this text on
        //the time TextView.
        timeTextView.setText(currentWeather.getTime());

        //Find the ImageView in the today_list_item.xml layout with the ID description_image.
        ImageView descriptionImageView = (ImageView) listItemView.findViewById(R.id.today_item_description_image);
        //Get the image's resource from the currentWeather object and set this resource on
        //the description ImageView.
        descriptionImageView.setImageResource(currentWeather.getImageResourceId());

        //Find the TextView in the today_list_item.xml layout with the ID description_text_view.
        TextView descriptionTextView = (TextView) listItemView.findViewById(R.id.today_item_description_text_view);
        //Get the description from the currentWeather object and set this text on
        //the description TextView.
        descriptionTextView.setText(currentWeather.getDescription());

        //Find the TextView in the today_list_item.xml layout with the ID temp_text_view.
        TextView tempTextView = (TextView) listItemView.findViewById(R.id.today_item_temp_text_view);
        //Get the temperature from the currentWeather object and set this text on
        //the temperature TextView.
        tempTextView.setText(currentWeather.getTemp());

        //Find the TextView in the today_list_item.xml layout with the ID humidity_text_view.
        TextView humidityTextView = (TextView) listItemView.findViewById(R.id.today_item_humidity_text_view);
        //Get the humidity from the currentWeather object and set this text on
        //the humidity TextView.
        humidityTextView.setText(currentWeather.getHumidity());

        // Return the whole list item layout so that it can be shown in the ListView.
        return listItemView;

    }
}
