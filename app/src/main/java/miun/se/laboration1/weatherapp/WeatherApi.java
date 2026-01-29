package miun.se.laboration1.weatherapp;

import android.os.Looper;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.os.Handler;
import android.os.Looper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Weather API Class. Calls Weather API and retrieves data in selected format.
 */
public class WeatherApi {

    // Create OkHttpClient to make HTTP requests to weather API
    private final OkHttpClient client = new OkHttpClient();

    /**
     * Create Executor and Handler for Async
     * - Executor runs network calls on a background thread
     * - Handler posts results back to the main/UI thread
     */
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    // Callback interface to deliver weather data or errors back to the UI asynchronously
    public interface WeatherCallback {
        void onResult(String fetchData);
        void onError(Exception e);
    }

    /**
     * getWeather function
     *  - fetches weather data from the API and returns it via provided callback
     * @param format 'JSON' or 'XML' decides which Api URL
     * @param callback Callback interface for receiving results/errors on UI thread
     */
    public void getWeather(String format, WeatherCallback callback){

        String url;

        // Api URL (coords: Sundsvall), this URL calls "compact" for compact JSON
        if(format.equalsIgnoreCase("JSON")) {
            url = "https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=62.3908&lon=17.3069";
        }
        // Api URL (coords: Sundsvall), this URL calls "classic" for XML
        else if(format.equalsIgnoreCase("XML")) {
            url = "https://api.met.no/weatherapi/locationforecast/2.0/classic?lat=62.3908&lon=17.3069";
        }
        // Handle wrong format input, send error to callback and return to exit method
        else {
            handler.post(() -> callback.onError(
                    new IllegalArgumentException("Invalid format: " + format + ". Use 'JSON' or 'XML'.")
            ));
            return;
        }

        // Run network request in background thread
        executor.execute(() -> {
            // Build HTTP GET request with URL and required User-Agent handler
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "WeatherApp/1.0 philip.hellzen@gmail.com")
                    .build();

            // Execute request and obtain HTTP response
            try(Response response = client.newCall(request).execute()) {
                // Success, send data to callback
                if(response.isSuccessful()){
                    String fetchData = response.body().string();
                    handler.post(() -> callback.onResult(fetchData));
                // Fail, send error to callback.
                } else {
                    handler.post(() -> callback.onError(new IOException("Empty response")));
                }
            // Handle network errors and send exception to callback
            } catch (IOException e){
                handler.post(() -> callback.onError(e));
            }
        });
    }
}
