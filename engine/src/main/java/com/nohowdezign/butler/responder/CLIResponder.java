package com.nohowdezign.butler.responder;

/**
 * @author Noah Howard
 */
public class CLIResponder extends Responder {

    public CLIResponder() {
        this.responseType = "cli";
    }

    @Override
    public void respondWithMessage(String message) {
        System.out.println(message);
    }
}
