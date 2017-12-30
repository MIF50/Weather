package data;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * this class prepare the city that put in query in url
 */
public class CityPreference {


    SharedPreferences prefs;

    public CityPreference(Activity activity) {
        prefs = activity.getPreferences(Activity.MODE_PRIVATE);
    }

    //If the user has not chosen a city, return default

    public String getCity() {
        return prefs.getString("city", "Seattle,US");
    }

    public void setCity(String city) {
        prefs.edit().putString("city", city).commit();
    }

}
