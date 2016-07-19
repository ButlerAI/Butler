package com.nohowdezign.butler.intent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Noah Howard
 */
public class AbstractIntent {
    private String intentType = "";
    private Map<String, String> optionalArguments = new HashMap<String, String>();

    public void setIntentType(String intentType) {
        this.intentType = intentType;
    }

    public void setOptionalArguments(Map<String, String> optionalArguments) {
        this.optionalArguments = optionalArguments;
    }

    public String getIntentType() {
        return intentType;
    }

    public Map<String, String> getOptionalArguments() {
        return optionalArguments;
    }
}
