package com.nohowdezign.butler.processing;

/**
 * @author Noah Howard
 */
public class NameDetection {
    private String name = "butler";

    /**
     * Checks whether or not the sentence contains my name (default: Butler)
     * @param sentenceToProcess is the sentence to scan
     */
    public boolean isNameInSentence(String sentenceToProcess) {
        for(String s : sentenceToProcess.split("\\s")) {
            if(s.toLowerCase().contains(name)) {
                return true;
            }
        }
        return false;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
