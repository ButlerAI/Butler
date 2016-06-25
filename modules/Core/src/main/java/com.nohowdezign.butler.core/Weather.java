package com.nohowdezign.butler.core;

import com.nohowdezign.butler.modules.annotations.Initialize;
import com.nohowdezign.butler.modules.annotations.ModuleLogic;
import com.nohowdezign.butler.processing.LanguageProcessor;
import org.bitpipeline.lib.owm.OwmClient;
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
            String placesOrDays = languageProcessor.getPartOfSpeechFromSentence(query, "NNP");
            OwmClient client = new OwmClient();
            WeatherStatusResponse response = client.currentWeatherAtCity("Searsmont", "ME");
            System.out.println(query);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
