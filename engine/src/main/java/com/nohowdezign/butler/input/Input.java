package com.nohowdezign.butler.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Noah Howard
 */
public abstract class Input {
    protected static Logger logger = LoggerFactory.getLogger(Input.class);
    protected String input = "";
    protected String inputMethod = "";

    /**
     * Starts listening for input and processing it
     */
    public void listenForInput() {
        logger.info("Listening for user input: " + inputMethod);
    }

    public String getProcessedUserInput() throws IOException {
        return processUserInput(input);
    }

    public String getRawUserInput() {
        return input;
    }

    protected String processUserInput(String inputToProcess) throws IOException {
        return inputToProcess;
    }

    public abstract String getNextInput();

}
