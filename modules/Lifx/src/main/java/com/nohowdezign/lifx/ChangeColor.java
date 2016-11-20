package com.nohowdezign.lifx;

import com.github.besherman.lifx.LFXClient;
import com.github.besherman.lifx.LFXLight;
import com.nohowdezign.butler.intent.AbstractIntent;
import com.nohowdezign.butler.intent.annotations.Intent;
import com.nohowdezign.butler.responder.Responder;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Noah Howard
 */
public class ChangeColor {
    private static final Map<String, Color> colors;
    static
    {
        colors = new HashMap<>();
        colors.put("blue", Color.BLUE);
        colors.put("gray", Color.GRAY);
        colors.put("green", Color.GREEN);
        colors.put("magenta", Color.MAGENTA);
        colors.put("orange", Color.ORANGE);
        colors.put("pink", Color.PINK);
        colors.put("red", Color.RED);
        colors.put("white", Color.WHITE);
        colors.put("yellow", Color.YELLOW);
    }

    @Intent(keyword = "change")
    public void changeLightColor(AbstractIntent intent, Responder responder) {
        Color color = null;
        for(String word : intent.getOriginalSentence().split(" ")) {
            if(colors.containsKey(word)) {
                color = colors.get(word);
            }
        }

        if(color != null) {
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
                        light.setColor(color);
                    }
                    responder.respondWithMessage("I have changed your light color.");
                }
            } finally {
                client.close();
            }
        } else {
            responder.respondWithMessage("I could not figure out what color you were saying.");
        }
    }

}
