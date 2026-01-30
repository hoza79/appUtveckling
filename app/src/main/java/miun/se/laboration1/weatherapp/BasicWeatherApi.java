package miun.se.laboration1.weatherapp;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * WeatherApi
 *  - Blocking API client
 *  - Fetches raw weather data (JSON or XML)
 *  - No threading, no callbacks, no Android dependencies
 */
public class BasicWeatherApi {

    private final OkHttpClient client = new OkHttpClient();

    /**
     * fetchWeather method.
     * - Fetch the weather data from API in raw string format
     * @param format "JSON" or "XML"
     * @return response body as string
     * @throws IOException if network fails
     * @throws IllegalArgumentException if format is invalid
     */
    public String fetchWeather(String format) throws IOException {
        String url;

        if (format.equalsIgnoreCase("JSON")) {
            url = "https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=62.3908&lon=17.3069";
        } else if (format.equalsIgnoreCase("XML")) {
            url = "https://api.met.no/weatherapi/locationforecast/2.0/classic?lat=62.3908&lon=17.3069";
        } else {
            throw new IllegalArgumentException(
                    "Invalid format: " + format + ". Use JSON or XML."
            );
        }

        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "WeatherApp/1.0 philip.hellzen@gmail.com")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response: " + response);
            }
            return response.body().string();
        }
    }
}
