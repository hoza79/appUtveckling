package miun.se.laboration1.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import miun.se.laboration1.weatherapp.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    private AsyncWeatherWorker<String> worker;
    private BasicWeatherApi weatherApi;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    /**
     * Weather Api Call
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Create new WeatherApi object

        worker = new AsyncWeatherWorker<>();
        weatherApi = new BasicWeatherApi();

        worker.start(() -> { return weatherApi.fetchWeather("XML"); },

                new AsyncWeatherWorker.UiCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        binding.weatherView.setText(result);
                    }

                    public void onError(Throwable error) {
                        String errOut = "Error: " + error.getMessage();
                        binding.weatherView.setText(errOut);
                    }
                }
        );}

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}