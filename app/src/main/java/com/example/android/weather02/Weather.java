package com.example.android.weather02;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by HP-PC on 27/10/2017.
 */

public class Weather {
    /** Description */
    private String mDescription;
    /** Temperature */
    private double  mTemp;
    /** Minimum Temperature */
    private int  mTempMin;
    /** Maximum Temperature */
    private int  mTempMax;
    /** time in milliseconds */
    private long mTimeMilliseconds;
    /** humidity */
    private String mHumidity;


    /**
     * Create a new Weather object.
     *
     * @param description is the weather's description
     * @param temp is the temperature
     * @param timeMilliseconds is the time in milliseconds
     * @param humidity is the humidity
     */
    public Weather(String description, double temp, long timeMilliseconds, String humidity) {
        mDescription = description;
        mTemp = temp;
        mTimeMilliseconds = timeMilliseconds;
        mHumidity = humidity;
    }
    /**
     * Create a new Weather object.
     *
     * @param description is the weather's description
     * @param tempMin is the minimum temperature
     * @param tempMax is the maximum temperature
     * @param timeMilliseconds is the time in milliseconds
     * @param humidity is the humidity
     */
    public Weather(String description, int tempMin, int tempMax, long timeMilliseconds, String humidity) {
        mDescription = description;
        mTempMin = tempMin;
        mTempMax = tempMax;
        mTimeMilliseconds = timeMilliseconds/1000;
        mHumidity = humidity;
    }

    /** Get the FULL day of the weather. */
    public String getFullDay() {
        // Create a new Date object from the time in milliseconds
        Date dateObject = new Date(getTimeInMilliseconds());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
        return dayFormat.format(dateObject);
    }

    /** Get the SHORT day of the weather. */
    public String getShortDay() {
        // Create a new Date object from the time in milliseconds
        Date dateObject = new Date(getTimeInMilliseconds());
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE");
        return dayFormat.format(dateObject);
    }
    /** Get the date of the weather. */
    public String getDate() {
        // Create a new Date object from the time in milliseconds
        Date dateObject = new Date(getTimeInMilliseconds());
        SimpleDateFormat dayFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dayFormat.format(dateObject);
    }
    /** Get the time of the weather in format: "12:00". */
    public String getTime() {
        // Create a new Date object from the time in milliseconds
        Date dateObject = new Date(getTimeInMilliseconds());
        SimpleDateFormat dayFormat = new SimpleDateFormat("HH:mm");
        return dayFormat.format(dateObject);
    }
    /** Get the weather's description. */
    public String getDescription() {
        return mDescription;
    }

    /** Get the temperature . */
    public String getTemp() {
        return getCleanTemp() + " \u2103";
    }

    /** Get the Clean temperature . */
    public String getCleanTemp() {
        DecimalFormat tempFormat = new DecimalFormat("0");
        String tempInCelsius = tempFormat.format(mTemp-272.15);
        return tempInCelsius;
    }
    public double getRealTemp(){
        return mTemp;
    }
    /** Get the time in milliseconds. */
    public long getTimeInMilliseconds() {
        return mTimeMilliseconds*1000;
    }

    /** Get the humidity. */
    public String getHumidity() {
        return mHumidity+"%";
    }
    /** Get the CLEAN humidity. */
    public String getCleanHumidity() {
        return mHumidity;
    }

    /** Get the description's image resource */
    public int getImageResourceId() {
        int imageResourceId;
        switch (mDescription) {
            case "Partly Cloudy":
                imageResourceId = R.drawable.icons8_partlycloudy;
                break;
            case "Cloudy":
            case "Clouds":
            case "Fog":
                imageResourceId = R.drawable.icons8_cloud;
                break;
            case "Clear":
                imageResourceId = R.drawable.icons8_sunny;
                break;
            case "Rain":
            default:
                imageResourceId = R.drawable.icons8_rain;
                break;
        }
        return imageResourceId;
    }

}
