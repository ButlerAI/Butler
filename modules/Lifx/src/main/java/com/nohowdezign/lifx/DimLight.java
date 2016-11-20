package com.nohowdezign.lifx;

import com.github.besherman.lifx.LFXClient;
import com.github.besherman.lifx.LFXLight;
import com.nohowdezign.butler.intent.AbstractIntent;
import com.nohowdezign.butler.intent.annotations.Intent;
import com.nohowdezign.butler.responder.Responder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Noah Howard
 *
 * Dims the lights to a specified value.
 */
public class DimLight {
    private static final Map<String, Float> dimValues;
    static
    {
        dimValues = new HashMap<>();
        dimValues.put("one", 0.01f);
        dimValues.put("five", 0.05f);
        dimValues.put("ten", 0.1f);
        dimValues.put("fifteen", 0.15f);
        dimValues.put("twenty", 0.2f);
        dimValues.put("twenty five", 0.25f);
        dimValues.put("thirty", 0.3f);
        dimValues.put("thirty five", 0.35f);
        dimValues.put("forty", 0.4f);
        dimValues.put("forty five", 0.45f);
        dimValues.put("fifty", 0.5f);
        dimValues.put("fifty five", 0.55f);
        dimValues.put("sixty", 0.6f);
        dimValues.put("sixty five", 0.65f);
        dimValues.put("seventy", 0.7f);
        dimValues.put("seventy five", 0.75f);
        dimValues.put("eighty", 0.8f);
        dimValues.put("eighty five", 0.85f);
        dimValues.put("ninety", 0.9f);
        dimValues.put("ninety five", 0.95f);
        dimValues.put("hundred", 1.0f);
        dimValues.put("one hundred", 1.0f);
    }

    @Intent(keyword = "brightness")
    public void dimLight(AbstractIntent intent, Responder responder) {
        Float dimVal = 0.0f;
        boolean haveDetectedVal = false;
        String previousWord = "";
        for(String word : intent.getOriginalSentence().split(" ")) {
            if(haveDetectedVal) {
                if(dimValues.containsKey(word)) {
                    dimVal = dimValues.get(previousWord + " " + word);
                }
            } else {
                if(dimValues.containsKey(word)) {
                    dimVal = dimValues.get(word);
                    haveDetectedVal = true;
                    previousWord = word;
                }
            }
        }

        if(dimVal != null) {
            System.out.println(dimVal);
            LFXClient client = new LFXClient();
            try {
                client.open(true);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            try {
                if (client.getLights().size() > 1) {
                    responder.respondWithMessage("You have multiple lights. I can only toggle one right now, sorry.");
                } else {
                    for (LFXLight light : client.getLights()) {
                        light.setBrightness(dimVal);
                    }
                    responder.respondWithMessage("I have set your light to " + String.valueOf(Math.round(dimVal * 100.0f)) + " percent.");
                }
            } finally {
                client.close();
            }
        } else {
            responder.respondWithMessage("I could not figure out what value you were saying.");
        }
    }

}
