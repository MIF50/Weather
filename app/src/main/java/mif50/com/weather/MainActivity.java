package mif50.com.weather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

import data.CityPreference;
import data.JsonWeatherParser;
import data.WeatherHttpClient;
import model.Weather;
import util.Utils;

public class MainActivity extends AppCompatActivity {
    private TextView cityName;
    private ImageView iconView;
    private TextView temp;
    private TextView description;
    private TextView humidity;
    private TextView pressure;
    private TextView wind;
    private TextView sunrise;
    private TextView sunset;
    private TextView updated;
   // private String city="San Francisco,US";


    Weather weather = new Weather();// that hold data as Model Weather

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // find View in xml
        cityName = findViewById(R.id.cityText);
        iconView = findViewById(R.id.icon);
        temp = findViewById(R.id.tepm);
        description = findViewById(R.id.cloudText);
        humidity = findViewById(R.id.humidityText);
        pressure = findViewById(R.id.pressureText);
        wind = findViewById(R.id.windText);
        sunrise = findViewById(R.id.sunriseText);
        sunset = findViewById(R.id.sunsetText);
        updated = findViewById(R.id.update);
        // use this to get default city from class CityPreference
        CityPreference cityPreference = new CityPreference(MainActivity.this);
        renderWeatherData(cityPreference.getCity()); // get data and set it in view
    }
    /*this method when change city to change data depend on city */
    public void renderWeatherData( String city) {
        WeatherTask weatherTask = new WeatherTask();
        weatherTask.execute(new String[]{city + "&units=metric" });

    }
    /*this class take String url and retur image of bitmap*/
    private class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        /**
         * Got this better solution from: http://javatechig.com/android/download-image-using-asynctask-in-android
         *
         */
        @Override
        protected Bitmap doInBackground(String... params) {
            return downloadImage(params[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // super.onPostExecute(bitmap);
            iconView.setImageBitmap(bitmap); // set image view
            iconView.setScaleType(ImageView.ScaleType.FIT_XY);
        }
        /* take code that get from Weather obj Model and open url and reurn image as bitmap*/
        private Bitmap downloadImage(String code) {
            // initilize the default HTTP client object
            final DefaultHttpClient client = new DefaultHttpClient();


            //forming a HttoGet request
            // url used if http://openweathermap.org/img/w/ + 01n   +.png
            final HttpGet getRequest = new HttpGet(Utils.ICON_URL + code + ".png");
            try {

                HttpResponse response = client.execute(getRequest);

                //check 200 OK for success
                final int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode != HttpStatus.SC_OK) {
                    Log.w("ImageDownloader", "Error " + statusCode +
                            " while retrieving bitmap from " + Utils.ICON_URL + code + ".png");
                    return null;

                }

                final HttpEntity entity = response.getEntity();
                if (entity != null) {
                    InputStream inputStream = null;
                    try {
                        // getting contents from the stream
                        inputStream = entity.getContent();

                        // decoding stream data back into image Bitmap that android understands
                        final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                        return bitmap;
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        entity.consumeContent();
                    }
                }
            } catch (Exception e) {
                // You Could provide a more explicit error message for IOException
                getRequest.abort();
                Log.e("ImageDownloader", "Something went wrong while" +
                        " retrieving bitmap from " + Utils.ICON_URL + e.toString());
            }

            return null;
        }
    }
    /*this method execute AsyncTask that take code of icon and return image as bitmap*/
    public void image(String data){
        new DownloadImageAsyncTask().execute(data);
    }


    /*this class AsyncTask take data as String value and return it as Weather Model
    * and set data in View of layout.xml
    * */
    private class WeatherTask extends AsyncTask<String, Void, Weather> {
        @Override
        protected Weather doInBackground(String... params) {


            String data = ( (new WeatherHttpClient()).getWeatherData(params[0])); // send url to get data as String

            try {
                weather = JsonWeatherParser.getWeather(data); // sent data String to get it as obj Weather Model

                //Retrive the icon as name of image
                weather.iconData = weather.currentCondition.getIcon();//( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));
                // weather.iconData = ( (new WeatherHttpClient()).getImage(weather.currentCondition.getIcon()));
                Log.v("ICON DATA VALUE IS: ", String.valueOf(weather.currentCondition.getIcon()));

                //We call our ImageDownload task after the weather.iconData is set! (name if image )
                image(weather.iconData);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return weather;
        }






        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            DateFormat df = DateFormat.getTimeInstance();

            String sunriseDate = df.format(new Date(weather.place.getSunrise())); // format sunrise data
            String sunsetDate = df.format(new Date(weather.place.getSunset())); // format sunset data
            DecimalFormat decimalFormat = new DecimalFormat("#.#");

            String updateD = df.format(new Date(weather.place.getLastupdate())); // format data

            String tempFormat = decimalFormat.format(weather.currentCondition.getTemperature()); // format as decimalFormat

            cityName.setText(weather.place.getCity() + "," + weather.place.getCountry()); // set name of city from weather object
            temp.setText("" + tempFormat + "°C"); // set temp
            humidity.setText("Humidity: " + weather.currentCondition.getHumidity() + "%");
            pressure.setText("Pressure: " + weather.currentCondition.getPressure() + "hPa");
            wind.setText("Wind: " + weather.wind.getSpeed() + "mps");
            sunrise.setText("Sunrise : " + sunriseDate);
            sunset.setText("Sunset: " + sunsetDate  );
            updated.setText("Last Updated: " + updateD );
            description.setText("Condition: " + weather.currentCondition.getCondition() + "(" + weather.currentCondition.getDescription() + ")");


            Log.v("TEMPERATURE:", String.valueOf(weather.currentCondition.getTemperature()));
            Log.v("TEST: " , sunriseDate);
        }
    }
    /*this dialog to change city this city used to in query in url to get data */
    private void showInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Change City");

        final EditText cityInput = new EditText(MainActivity.this);
        cityInput.setInputType(InputType.TYPE_CLASS_TEXT);
        cityInput.setHint("Portland,US");
        builder.setView(cityInput);
        builder.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CityPreference cityPreference = new CityPreference(MainActivity.this);
                cityPreference.setCity(cityInput.getText().toString());

                String newCity = cityPreference.getCity();

                //re-render everything again
                renderWeatherData(newCity);
            }
        });
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.changeCity){
            showInputDialog();// used to change city
        }
        return super.onOptionsItemSelected(item);
    }
}
