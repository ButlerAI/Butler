package com.nohowdezign.lifx;

import com.github.besherman.lifx.LFXClient;
import com.github.besherman.lifx.LFXLight;
import com.nohowdezign.butler.intent.AbstractIntent;
import com.nohowdezign.butler.intent.annotations.Intent;
import com.nohowdezign.butler.responder.Responder;

import java.io.IOException;

/**
 * @author Noah Howard
 *
 * Toggle a LiFX light on/off
 */
public class ToggleLight {

    @Intent(keyword = "turn")
    public void toggleLight(AbstractIntent intent, Responder responder) {
        LFXClient client = new LFXClient();
        try {
            client.open(true);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            if(client.getLights().size() > 1) {
                responder.respondWithMessage("You have multiple lights. I can only toggle one right now, sorry.");
            } else {
                for (LFXLight light : client.getLights()) {
                    light.setPower(!light.isPower()); // Toggle the light
                }
                responder.respondWithMessage("I have successfully toggled your light.");
            }
        } finally {
            client.close();
        }
    }

}
