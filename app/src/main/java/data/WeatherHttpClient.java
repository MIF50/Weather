package data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import util.Utils;

/**
 * this class get url and open connection in website Weather to get data
 */
public class WeatherHttpClient {

    /*this method get data from website as String value */
    public String getWeatherData(String place) {
        HttpURLConnection connection = null; // to open url
        InputStream inputStream = null; // to read data


        try {
            connection = (HttpURLConnection) (new URL(Utils.BASE_URL + place+Utils.API_KEY)).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true); //Sets the flag indicating whether this {@code URLConnection} allows input
            connection.setDoOutput(true); // contain request body
            connection.connect(); // connect

            //Read the response
            StringBuffer stringBuffer = new StringBuffer();
            inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;

            while ((line = bufferedReader.readLine()) != null)
                stringBuffer.append(line + "\r\n");

            inputStream.close();
            connection.disconnect();
            return stringBuffer.toString();

        } catch (IOException e) {

            e.printStackTrace();
        }finally {
            try {
                if (inputStream!=null){
                    inputStream.close();
                }

            } catch (IOException e) {

            }

            connection.disconnect();
        }

        return null;
    }
}
