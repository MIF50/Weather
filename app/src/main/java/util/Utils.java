package util;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * this class containt url to get data from website as json object and deal with this data with  class in model
 * url =>http://api.openweathermap.org/data/2.5/weather?q=cairo,eg &appid=170d3b872b0095aed937c3e598471ff5
 */
public class Utils {
    public static final String API_KEY="&appid=170d3b872b0095aed937c3e598471ff5";

    public static final String  BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String ICON_URL = "http://openweathermap.org/img/w/"; // + 10d.png

    //helper methods
    /* this method get json object with name {}*/
    public static JSONObject getObject(String tagName, JSONObject jsonObject) throws JSONException {
        JSONObject jObj = jsonObject.getJSONObject(tagName);
        return jObj;
    }
    /*this method get name of jsonObject*/
    public static  String getString(String tagName, JSONObject jsonObject) throws JSONException {
        return jsonObject.getString(tagName);
    }
    /*this method get value float from jsonOject*/
    public static float getFloat(String tagName, JSONObject jsonObject) throws JSONException{
        return (float) jsonObject.getDouble(tagName);
    }
    /*this method get value Double from jsonObject*/
    public static double getDouble(String tagName, JSONObject jsonObject) throws JSONException{
        return (float) jsonObject.getDouble(tagName);
    }
    /*this method get value int ffrom jsonObject*/
    public static int getInt(String tagName, JSONObject jsonObject) throws JSONException {
        return  jsonObject.getInt(tagName);
    }
}
