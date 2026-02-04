package miun.se.laboration1.weatherapp;

import java.io.IOException;

/**
 * WeatherController - Kopplar ihop API, Parser och AsyncWorker
 */
public class WeatherController {

    private final WeatherApi api;
    private final XmlParser parser;

    public WeatherController() {
        this.api = new WeatherApi();
        this.parser = new XmlParser();
    }

    /**
     * Hämtar väder och returnerar CityWeather-objekt.
     * Använder AsyncWeatherWorker för att köra i bakgrunden.
     *
     * @param callback - får resultatet när det är klart
     */
    public void getWeather(AsyncWeatherWorker.UiCallback<CityWeather> callback) {
        AsyncWeatherWorker<CityWeather> worker = new AsyncWeatherWorker<>();

        worker.start(
                // Bakgrundsjobb
                () -> {
                    String xmlString = api.fetchWeather("XML");
                    return parser.parseStringData(xmlString);
                },
                // Callback till UI
                callback
        );
    }
}