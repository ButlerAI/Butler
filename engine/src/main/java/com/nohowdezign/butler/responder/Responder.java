package com.nohowdezign.butler.responder;

/**
 * @author Noah Howard
 */
public abstract class Responder {
    protected String responseType = "";
    public abstract void respondWithMessage(String message);
}
