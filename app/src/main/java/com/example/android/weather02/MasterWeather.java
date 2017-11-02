package com.example.android.weather02;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by HP-PC on 28/10/2017.
 */

public class MasterWeather {
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
    /** forecast */
    private List<Weather> mWeathers;



    /**
     * Create a new Weather object.
     *
     * @param description is the weather's description
     * @param tempMin is the minimum temperature
     * @param tempMax is the maximum temperature
     * @param timeMilliseconds is the time in milliseconds
     * @param humidity is the humidity
     * @param weathers is a list of the forecast for each day
     */
    public MasterWeather(String description, int tempMin, int tempMax, long timeMilliseconds,
                         String humidity, List<Weather> weathers) {
        mDescription = description;
        mTempMin = tempMin;
        mTempMax = tempMax;
        mTimeMilliseconds = timeMilliseconds/1000;
        mHumidity = humidity;
        mWeathers=weathers;
    }

    /** Get the list of weathers */
    public List<Weather> getListOfWeathers(){
        return mWeathers;
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
        String mDescription;
        switch (getImageResourceId()) {
            case R.drawable.icons8_rain:
                mDescription = "Rain";
                break;
            case R.drawable.icons8_cloud:
                mDescription = "Clouds";
                break;
            default:
                mDescription = "Clear";
        }
        return mDescription;
    }

    /** Get the Clean temperature . */
    public String getCleanTemp() {
        DecimalFormat tempFormat = new DecimalFormat("0");
        String tempInCelsius = tempFormat.format(mTemp-272.15);
        return tempInCelsius;
    }
    /** Get the minimum temperature . */
    public String getMinTemp() {
        return mTempMin + " - ";
    }
    /** Get the maximum temperature . */
    public String getMaxTemp() {
        return mTempMax + " \u2103";
    }
    /** Get the time in milliseconds. */
    public long getTimeInMilliseconds() {
        return mTimeMilliseconds*1000;
    }

    /** Get the humidity. */
    public String getHumidity() {
        return mHumidity+"%";
    }

    /** Get the description's image resource */
    public int getImageResourceId() {
        int imageResource = R.drawable.icons8_sunny;
        boolean isCloudy = false;
        for (Weather mWeather : getListOfWeathers()){
            if (mWeather.getDescription().equals("Rain")){
                imageResource = R.drawable.icons8_rain;
                return imageResource;
            } else if (mWeather.getDescription().equals("Clouds")){
                isCloudy = true;
            }
        }

        if (isCloudy == true){
            imageResource =R.drawable.icons8_cloud;
        }
        return imageResource;
    }
}
