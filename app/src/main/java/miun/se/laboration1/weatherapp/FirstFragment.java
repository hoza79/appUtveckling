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
        WeatherApi api = new WeatherApi();
        // Call getWeather method in object. Format = "json" or "xml"
        api.getWeather("xml", new WeatherApi.WeatherCallback() {
            @Override
            public void onResult(String fetchData) {
                binding.weatherView.setText(fetchData);
            }

            @Override
            public void onError(Exception e) {
                String errorOut = "Error: " + e.getMessage();
                binding.weatherView.setText(errorOut);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}