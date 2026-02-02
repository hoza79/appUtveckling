package miun.se.laboration1.weatherapp;

public class CityWeather {

    private double temperature, windSpeed, cloudiness, precipitation;
    private String symbol;
    public CityWeather(double temperature, double windSpeed, double cloudiness, double precipitation, String symbol){
        this.temperature = temperature;
        this.windSpeed = windSpeed;
        this.cloudiness = cloudiness;
        this.precipitation = precipitation;
        this.symbol = symbol;
    }

    public double getTemperature(){
        return temperature;
    }
    public double getWindSpeed(){
        return windSpeed;
    }

    public double getCloudiness(){
        return cloudiness;
    }

    public double getPrecipitation(){
        return precipitation;
    }
    public String getSymbol() {return symbol;}



}
