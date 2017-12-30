package model;

/**
 * main":{
 * "temp":271.4,
 * "pressure":1031,
 * "humidity":74,
 * "temp_min":269.15,
 * "temp_max":274.15
 * },
 */
public class Temperature {
    private double temp;
    private float minTemp;
    private float maxTemp;

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public float getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(float minTemp) {
        this.minTemp = minTemp;
    }

    public float getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(float maxTemp) {
        this.maxTemp = maxTemp;
    }
}
