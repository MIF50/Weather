package model;

/**
 * {
 * "coord":{
     * "lon":-122.33,
     * "lat":47.6
     * },
 * "weather":[
     * {
         * "id":800,
         * "main":"Clear",
         * "description":"clear sky",
         * "icon":"01n"
     * }
     * ],
 * "base":"stations",
 * "main":{
     * "temp":271.4,
     * "pressure":1031,
     * "humidity":74,
     * "temp_min":269.15,
     * "temp_max":274.15
     * },
 * "visibility":16093,
 * "wind":{
     * "speed":2.11,
     * "deg":38.0029
     * },
 * "clouds":{"all":1},
 * "dt":1514096100,

 * "sys":{
 *    "type":1,
     * "id":2949,
     * "message":0.0192,
     * "country":"US",
     * "sunrise":1514130975,
     * "sunset":1514161344},
     * "id":5809844,
     * "name":"Seattle",
     * "cod":200
     * }
 * }
 */
public class Weather {
    public Place place;
    public String iconData;
    public CurrentCondition currentCondition = new CurrentCondition();
    public Temperature temperature = new Temperature();
    public Wind wind = new Wind();
    public Rain rain = new Rain();
    public Snow snow = new Snow();
    public Clouds clouds = new Clouds();
}
