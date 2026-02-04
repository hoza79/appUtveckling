package miun.se.laboration1.weatherapp;

public class WeatherData {
    private double temperature;
    private double precipitation;
    private double windSpeed;
    private double cloudCoverage;
    private String description;

    public WeatherData(double temperature, double precipitation, double windSpeed, double cloudCoverage, String description) {
        this.temperature = temperature;
        this.precipitation = precipitation;
        this.windSpeed = windSpeed;
        this.cloudCoverage = cloudCoverage;
        this.description = description;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getPrecipitation() {
        return precipitation;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getCloudCoverage() {
        return cloudCoverage;
    }

    public String getDescription() {
        return description;
    }

    public int getWeatherIcon() {
        if (precipitation > 0) {
            return R.drawable.rain_icon;
        } else if (cloudCoverage > 70) {
            return R.drawable.cloudy_icon;
        } else if (cloudCoverage > 30) {
            return R.drawable.partly_cloudy_icon;
        } else {
            return R.drawable.sunny_icon;
        }
    }
}
