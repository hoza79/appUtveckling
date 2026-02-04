package miun.se.laboration1.weatherapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button fetchButton = view.findViewById(R.id.fetchWeatherButton);
        fetchButton.setOnClickListener(v -> fetchWeather());
    }

    private void fetchWeather() {
        new Thread(() -> {
            try {
                WeatherData weather = WeatherParser.fetchWeather();
                getActivity().runOnUiThread(() -> updateUI(weather));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void updateUI(WeatherData weather) {
        View view = getView();
        if (view == null) return;

        TextView tempText = view.findViewById(R.id.temperatureValue);
        TextView rainText = view.findViewById(R.id.precipitationValue);
        TextView windText = view.findViewById(R.id.windValue);
        TextView cloudText = view.findViewById(R.id.cloudValue);
        ImageView icon = view.findViewById(R.id.weatherIcon);

        tempText.setText("Temp: " + (int)weather.getTemperature() + "°C");
        rainText.setText("Regn: " + weather.getPrecipitation() + " mm");
        windText.setText("Vind: " + (int)weather.getWindSpeed() + " m/s");
        cloudText.setText("Molnighet: " + (int)weather.getCloudCoverage() + "%");

        int iconResId = getWeatherIconResource(weather);
        icon.setImageResource(iconResId);
    }

    private int getWeatherIconResource(WeatherData weather) {
        if (weather.getPrecipitation() > 0) {
            return R.drawable.rain_icon;
        } else if (weather.getCloudCoverage() > 70) {
            return R.drawable.cloudy_icon;
        } else if (weather.getCloudCoverage() > 30) {
            return R.drawable.partly_cloudy_icon;
        } else {
            return R.drawable.sunny_icon;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
