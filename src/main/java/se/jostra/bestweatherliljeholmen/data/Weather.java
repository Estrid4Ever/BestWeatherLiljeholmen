package se.jostra.bestweatherliljeholmen.data;

import java.time.LocalDateTime;

public class Weather {
    private LocalDateTime timeSeries;
    private double celsius;
    private double airPressureAtSeaLevel;
    private int cloudChance;
    private int relativeHumidity;
    private double rainAmount;
    private int windFromDirection;
    private double windSpeed;
    private String weatherSource;

    public Weather(LocalDateTime localDateTime, double celsius, double airPressureAtSeaLevel,
                   int cloudChance, int relativeHumidity, double rainAmount,
                   int windFromDirection, double windSpeed, String weatherSource) {
        this.timeSeries = localDateTime;
        this.celsius = celsius;
        this.airPressureAtSeaLevel = airPressureAtSeaLevel;
        this.cloudChance = cloudChance;
        this.relativeHumidity = relativeHumidity;
        this.rainAmount = rainAmount;
        this.windFromDirection = windFromDirection;
        this.windSpeed = windSpeed;
        this.weatherSource = weatherSource;
    }

    public double getCelsius() {
        return celsius;
    }

    public void setCelsius(double celsius) {
        this.celsius = celsius;
    }

    public LocalDateTime getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(LocalDateTime timeSeries) {
        this.timeSeries = timeSeries;
    }

    public double getAirPressureAtSeaLevel() {
        return airPressureAtSeaLevel;
    }

    public void setAirPressureAtSeaLevel(double airPressureAtSeaLevel) {
        this.airPressureAtSeaLevel = airPressureAtSeaLevel;
    }

    public int getCloudChance() {
        return cloudChance;
    }

    public void setCloudChance(int cloudChance) {
        this.cloudChance = cloudChance;
    }

    public int getRelativeHumidity() {
        return relativeHumidity;
    }

    public void setRelativeHumidity(int relativeHumidity) {
        this.relativeHumidity = relativeHumidity;
    }

    public double getRainAmount() {
        return rainAmount;
    }

    public void setRainAmount(double rainAmount) {
        this.rainAmount = rainAmount;
    }

    public int getWindFromDirection() {
        return windFromDirection;
    }

    public void setWindFromDirection(int windFromDirection) {
        this.windFromDirection = windFromDirection;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getWeatherSource() {
        return weatherSource;
    }

    public void setWeatherSource(String weatherSource) {
        this.weatherSource = weatherSource;
    }

    @Override
    public String toString() {
        return "Weather{" +
                "localDateTime=" + timeSeries +
                ", celsius=" + celsius +
                ", airPressureAtSeaLevel=" + airPressureAtSeaLevel +
                ", cloudChance=" + cloudChance +
                ", relativeHumidity=" + relativeHumidity +
                ", rainAmount=" + rainAmount +
                ", windFromDirection=" + windFromDirection +
                ", windSpeed=" + windSpeed +
                ", weatherSource='" + weatherSource + '\'' +
                '}';
    }
}
