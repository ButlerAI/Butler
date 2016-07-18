package com.nohowdezign.butler.processing;

/**
 * @author Noah Howard
 */
public class IntentProcessor {
    private LanguageProcessor languageProcessor = new LanguageProcessor();

    public String getIntentOfSentence(String sentence) {
        String toReturn = "";
        String nerTags = languageProcessor.getNamedEntities(sentence);
        for(String word : nerTags.split("\\s")) {
            if(word.split("_")[1] != "O") { // If the word has a tag
                toReturn += word.split("_")[1] + " : " + word.split("_")[0];
            }
        }
        return toReturn;
    }

}
