package com.nohowdezign.butler.core;

import com.nohowdezign.butler.modules.annotations.Execute;
import com.nohowdezign.butler.modules.annotations.ModuleLogic;
import com.nohowdezign.butler.processing.LanguageProcessor;
import com.nohowdezign.butler.responder.Responder;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

/**
 * @author Noah Howard
 */
@ModuleLogic(subjectWord = "weather temperature")
public class Weather {

    @Execute
    public void provideWeather(String query, Responder responder) {
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

            responder.respondWithMessage("It is currently " + response.getMainInstance().getTemperature() + " degrees in " + response.getCityName());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
