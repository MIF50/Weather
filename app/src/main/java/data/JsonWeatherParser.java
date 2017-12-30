package data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import model.Place;
import model.Weather;
import util.Utils;

/**
 * this class used to return data as model (Weather)
 */
public class JsonWeatherParser {
    /*this method to return data as Weather Model fron String data*/
    public static Weather getWeather(String data) throws JSONException {
        Weather weather = new Weather(); // class that return data as it (Weather)


        //create jsonDataWeather from data
        JSONObject jsonDataWeather = new JSONObject(data);


        //extract the info
        Place place = new Place();

        JSONObject coordObj = Utils.getObject("coord", jsonDataWeather); // get JsonObject that called (coord) from  JsonObject data  jsonDataWeather
        place.setLat(Utils.getFloat("lat", coordObj)); // get value of lat and set it in  setter place
        place.setLon(Utils.getFloat("lon", coordObj)); // get value of lng and set it in  setter place


        //Get the sys obj
        JSONObject sysObj = Utils.getObject("sys", jsonDataWeather);
        place.setCountry(Utils.getString("country", sysObj));
        place.setLastupdate(Utils.getInt("dt", jsonDataWeather));
        place.setSunrise(Utils.getInt("sunrise", sysObj));
        place.setSunset(Utils.getInt("sunset", sysObj));
        place.setCity(Utils.getString("name", jsonDataWeather));
        weather.place = place; // set palce in Weather object

        //This is an array for weather info
        JSONArray jsonArray = jsonDataWeather.getJSONArray("weather");// get jsonArray that called weather from jsonDataWeather

        //we could loop through the array, but we are just interested in getting the first index (0) --- this only one index is 0
        JSONObject jsonWeather = jsonArray.getJSONObject(0);
        weather.currentCondition.setWeatherId(Utils.getInt("id", jsonWeather));
        weather.currentCondition.setDescription(Utils.getString("description", jsonWeather));
        weather.currentCondition.setCondition(Utils.getString("main", jsonWeather));
        weather.currentCondition.setIcon(Utils.getString("icon", jsonWeather));

        //Let's get the main object
        JSONObject mainObj = Utils.getObject("main", jsonDataWeather);
        weather.currentCondition.setHumidity(Utils.getInt("humidity", mainObj));
        weather.currentCondition.setPressure(Utils.getInt("pressure", mainObj));
        weather.currentCondition.setMinTemperature(Utils.getFloat("temp_min", mainObj));
        weather.currentCondition.setMinTemperature(Utils.getFloat("temp_max", mainObj));
        weather.currentCondition.setTemperature(Utils.getDouble("temp", mainObj));


        //Let's setup wind
        JSONObject windObj = Utils.getObject("wind", jsonDataWeather);
        weather.wind.setSpeed(Utils.getFloat("speed", windObj));
        weather.wind.setDeg(Utils.getFloat("deg", windObj));


        //Setup clouds
        JSONObject cloudObj = Utils.getObject("clouds", jsonDataWeather);
        weather.clouds.setPercipitation(Utils.getInt("all", cloudObj));


        return weather;


    }
}
