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

/*
    Weather API Class. Calls Weather API and retrives data in JSON format.
 */
public class WeatherApi {

    // Create OkHttpClient
    private final OkHttpClient client = new OkHttpClient();

    // Create Executor and Handler for Async
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    // Callback interface to deliver results to UI
    public interface WeatherCallback {
        void onResult(String jsonData);
        void onError(Exception e);
    }

    public void getWeather(WeatherCallback callback){

        //Api URL Sundsvall, this URL calls "compact" for compact JSON
        //String url = "https://api.met.no/weatherapi/locationforecast/2.0/compact?lat=62.3908&lon=17.3069";

        //Api URL Sundsvall, this URL calls "compact" for compact JSON
        String url = "https://api.met.no/weatherapi/locationforecast/2.0/classic?lat=62.3908&lon=17.3069";


        executor.execute(() -> {
            Request request = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "WeatherApp/1.0 philip.hellzen@gmail.com")
                    .build();

            try(Response response = client.newCall(request).execute()) {
                if(response.isSuccessful()){
                    String fetchData = response.body().string();
                    handler.post(() -> callback.onResult(fetchData));
                } else {
                    handler.post(() -> callback.onError(new IOException("Empty response")));
                }
            } catch (IOException e){
                handler.post(() -> callback.onError(e));
            }
        });
    }
}
