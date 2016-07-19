package com.nohowdezign.butler.core;

import com.nohowdezign.butler.database.UserProfile;
import com.nohowdezign.butler.intent.AbstractIntent;
import com.nohowdezign.butler.intent.annotations.Intent;
import com.nohowdezign.butler.responder.Responder;
import net.aksingh.owmjapis.CurrentWeather;
import net.aksingh.owmjapis.OpenWeatherMap;

/**
 * @author Noah Howard
 */
public class Weather {
    private UserProfile profile = new UserProfile();

    @Intent(keyword = "weather")
    public void provideWeather(AbstractIntent intent, Responder responder) {
        try {
            OpenWeatherMap client = new OpenWeatherMap("a3379e5d05fb282ef41a97a5930139a1");
            CurrentWeather response;
            String loc = intent.getOptionalArguments().get("LOCATION");
            if(loc == null) {
                response = client.currentWeatherByCityName(profile.getUserLocation(UserProfile.DEFAULT_USER));
            } else {
                response = client.currentWeatherByCityName(loc);
            }

            responder.respondWithMessage("It is currently " + response.getMainInstance().getTemperature() + " degrees in " + response.getCityName());
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

}
