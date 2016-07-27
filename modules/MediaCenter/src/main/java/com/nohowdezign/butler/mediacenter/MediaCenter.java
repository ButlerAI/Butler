package com.nohowdezign.butler.mediacenter;

import com.nohowdezign.butler.intent.AbstractIntent;
import com.nohowdezign.butler.intent.annotations.Intent;
import com.nohowdezign.butler.responder.Responder;

/**
 * @author Noah Howard
 */
public class MediaCenter {

    @Intent(keyword = "play")
    public void onPlay(AbstractIntent intent, Responder responder) {
        //
    }

}
