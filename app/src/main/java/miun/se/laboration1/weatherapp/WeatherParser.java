package miun.se.laboration1.weatherapp;

import org.json.JSONObject;
import org.json.JSONArray;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherParser {

    private static final String API_URL = "https://api.met.no/weatherapi/locationforecast/2.0/?lat=62.391078&lon=17.306233";

    public static WeatherData fetchWeather() throws Exception {
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("User-Agent", "WeatherApp");

        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        JSONObject json = new JSONObject(response.toString());
        JSONObject properties = json.getJSONObject("properties");
        JSONArray timeseries = properties.getJSONArray("timeseries");
        
        JSONObject firstTime = timeseries.getJSONObject(0);
        JSONObject data = firstTime.getJSONObject("data");
        JSONObject instant = data.getJSONObject("instant");
        JSONObject details = instant.getJSONObject("details");

        double temperature = details.getDouble("air_temperature");
        double cloudCover = details.getDouble("cloud_area_fraction");
        double windSpeed = details.getDouble("wind_speed");
        
        double precipitation = 0;
        if (data.has("next_1_hours")) {
            JSONObject next1h = data.getJSONObject("next_1_hours");
            if (next1h.has("details")) {
                JSONObject precDetails = next1h.getJSONObject("details");
                if (precDetails.has("precipitation_amount")) {
                    precipitation = precDetails.getDouble("precipitation_amount");
                }
            }
        }

        String description = getWeatherDescription((int) cloudCover, precipitation);

        return new WeatherData(temperature, precipitation, windSpeed, cloudCover, description);
    }

    private static String getWeatherDescription(int cloudCover, double precipitation) {
        if (precipitation > 0) {
            return "Regn";
        } else if (cloudCover > 70) {
            return "Molnigt";
        } else if (cloudCover > 30) {
            return "Delvis molnigt";
        } else {
            return "Soligt";
        }
    }
}
