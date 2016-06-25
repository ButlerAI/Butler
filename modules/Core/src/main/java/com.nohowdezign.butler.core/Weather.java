package com.nohowdezign.butler.core;

import com.nohowdezign.butler.modules.annotations.Initialize;
import com.nohowdezign.butler.modules.annotations.ModuleLogic;
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
    public void provideWeather(String query) throws IOException, JSONException {
        OwmClient client = new OwmClient();
        WeatherStatusResponse response = client.currentWeatherAtCity("Searsmont", "ME");
        System.out.println(query);
    }

}
