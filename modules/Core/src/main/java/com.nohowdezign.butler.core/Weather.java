package com.nohowdezign.butler.core;

import com.nohowdezign.butler.modules.annotations.Initialize;
import com.nohowdezign.butler.modules.annotations.ModuleLogic;
import com.nohowdezign.butler.processing.LanguageProcessor;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

/**
 * @author Noah Howard
 */
@ModuleLogic(subjectWord = "weather temperature")
public class Weather {

    @Initialize
    public void provideWeather(String query) {
        try {
            LanguageProcessor languageProcessor = new LanguageProcessor();
            OpenWeatherMap client = new OpenWeatherMap("a3379e5d05fb282ef41a97a5930139a1");
            CurrentWeather response;
            String loc = languageProcessor.getNamedEntity(query, "LOCATION");
            if(loc.equals("")) {
                response = client.currentWeatherByCityName("Syracuse");
            } else {
                response = client.currentWeatherByCityName(loc.replace(" ", "%20"));
            }

            System.out.println("It is currently " + response.getMainInstance().getTemperature() + " degrees in " + response.getCityName());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private float celciusToFahrenheit(float degCelcius) {
        float degFahrenheit;
        degFahrenheit = degCelcius * 9/5 + 32;
        return degFahrenheit;
    }

    private float kelvinToCelcius(float degKelvin) {
        float degCelcius;
        degCelcius = degKelvin - 273.15f;
        return degCelcius;
    }

}
