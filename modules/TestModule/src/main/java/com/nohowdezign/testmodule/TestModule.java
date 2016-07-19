package com.nohowdezign.testmodule;

import com.nohowdezign.butler.intent.AbstractIntent;
import com.nohowdezign.butler.intent.annotations.Intent;
import com.nohowdezign.butler.responder.Responder;

/**
 * @author Noah Howard
 */
public class TestModule {

    @Intent(keyword = "test")
    public void initTest(AbstractIntent intent, Responder responder) {
        responder.respondWithMessage(intent.getIntentType());
    }

}
