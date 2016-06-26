package com.nohowdezign.butler.core;

import com.nohowdezign.butler.modules.annotations.Initialize;
import com.nohowdezign.butler.modules.annotations.ModuleLogic;
import com.nohowdezign.butler.processing.LanguageProcessor;
import org.bitpipeline.lib.owm.OwmClient;
import org.bitpipeline.lib.owm.WeatherData;
import org.bitpipeline.lib.owm.WeatherStatusResponse;
import org.json.JSONException;

import java.io.IOException;

/**
 * @author Noah Howard
 */
@ModuleLogic(subjectWord = "weather")
public class Weather {

    @Initialize
    public void provideWeather(String query) {
        try {
            LanguageProcessor languageProcessor = new LanguageProcessor();
            OwmClient client = new OwmClient();
            WeatherStatusResponse response;
            String loc = languageProcessor.getNamedEntity(query, "LOCATION");
            System.out.println(loc);
            if(loc.equals("")) {
                response = client.currentWeatherAtCity("Syracuse");
            } else {
                response = client.currentWeatherAtCity(loc);
            }

            WeatherData weather = response.getWeatherStatus().get(0);
            System.out.println("It is currently " + weather.getTemp() + " degrees and " + weather.getWeatherConditions() + " in " + loc);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
