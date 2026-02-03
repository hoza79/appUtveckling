package miun.se.laboration1.weatherapp;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * WeatherApi
 *  - Fetches raw weather data (JSON or XML)
 */
public class WeatherApi {

    private final OkHttpClient client = new OkHttpClient();

    //Api URL Compact för JSON, coords: Sundsvall
    private static final String urlJSON = "https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=62.3908&lon=17.3069";
    //Api URL Classic för XML, coords: Sundsvall
    private static final String urlXML = "https://api.met.no/weatherapi/locationforecast/2.0/classic?lat=62.3908&lon=17.3069";

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

        // Select URL from format
        if (format.equalsIgnoreCase("JSON")) {
            url = urlJSON;
        } else if (format.equalsIgnoreCase("XML")) {
            url = urlXML;
        } else {
            throw new IllegalArgumentException(
                    "Invalid format: " + format + ". Use JSON or XML."
            );
        }

        //Build API request
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", "WeatherApp/1.0 philip.hellzen@gmail.com")
                .build();

        // Make request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected response: " + response);
            }
            // Return body as string
            return response.body().string();
        }
    }
}
