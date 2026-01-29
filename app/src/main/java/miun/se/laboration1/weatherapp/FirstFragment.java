package miun.se.laboration1.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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
        WeatherApi api = new WeatherApi();
        api.getWeather(new WeatherApi.WeatherCallback() {
            @Override
            public void onResult(String jsonData) {
                binding.weatherView.setText(jsonData);
            }

            @Override
            public void onError(Exception e) {
                binding.weatherView.setText("Error: " + e.getMessage());
            }
        });
    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}