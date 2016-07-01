package com.nohowdezign.testmodule;

import com.nohowdezign.butler.modules.annotations.Initialize;
import com.nohowdezign.butler.modules.annotations.ModuleLogic;
import com.nohowdezign.butler.responder.Responder;

/**
 * @author Noah Howard
 */
@ModuleLogic(subjectWord = "foobar")
public class TestModule {

    @Initialize
    public void initTest(String query, Responder responder) {
        responder.respondWithMessage("testefst");
    }

}
